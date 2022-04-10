package ch6.stream;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.japi.function.Function;
import akka.stream.*;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;

@AllArgsConstructor
@Getter
class AccessLog {
    private String ip;
    private String time;
    private String method;
    private String resource;
    private String state;
}

public class StreamDemo {

    @SneakyThrows
    public static void main(String[] args) {

        Function<Throwable, Supervision.Directive> decider = err -> {
            if (err instanceof ArrayIndexOutOfBoundsException) {
                return (Supervision.Directive) Supervision.resume();
            } else {
                return (Supervision.Directive) Supervision.stop();
            }
        };

        ActorSystem system = ActorSystem.create("sys");
        Materializer materializer = ActorMaterializer.create(ActorMaterializerSettings.create(system).withSupervisionStrategy(decider), system);

        Flow<ByteString, String, NotUsed> flowToString = Framing.delimiter(ByteString.fromString("\r\n"), 100).map(x -> x.utf8String());

        Flow<String, AccessLog, NotUsed> flowToAccess = Flow.of(String.class).map(x -> {
            String[] datas = x.split(" ");
            String ip = datas[0];
            String time = datas[1];
            String method = datas[2];
            String resource = datas[3];
            String state = datas[4];
            AccessLog access = new AccessLog(ip, time, method, resource, state);
            return access;
        });

        Flow<AccessLog, ByteString, NotUsed> flowToByte = Flow.of(AccessLog.class).map(x -> ByteString.fromString(x.toString() + "\r\n"));

        Flow<AccessLog, AccessLog, NotUsed> filter404 = Flow.of(AccessLog.class).filter(x -> x.getState().equals("404"));

        URL resource = StreamDemo.class.getClassLoader().getResource("access_log.txt");

        Source<ByteString, CompletionStage<IOResult>> source = FileIO.fromPath(Paths.get(resource.toURI()));
        Sink<ByteString, CompletionStage<IOResult>> sink = FileIO.toPath(Paths.get("demo_out.txt"));
        RunnableGraph<CompletionStage<IOResult>> graph = source.via(flowToString).via(flowToAccess).via(filter404).via(flowToByte).to(sink);
        CompletionStage<IOResult> coms = graph.run(materializer);
        coms.thenAccept(System.out::println);
    }
}
