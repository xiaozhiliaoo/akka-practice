package ch6.extension;

import akka.actor.Extension;

public class RPCExtension implements Extension {

    public RPCExtension(String server, int port) {
        this.server = server;
        this.port = port;
    }

    private String server;
    private int port;

    public void rpcCall(String cmd) {
        System.out.println("call " + cmd + "-->" + server + ":" + port);
    }
}
