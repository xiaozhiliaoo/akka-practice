package org.lili.akka.learningakka.ch3;


import akka.actor.AbstractActor;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class ParsingActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ParseHtmlArticle.class, msg -> {
                    String body = ArticleExtractor.INSTANCE.getText(msg.htmlString);
                    sender().tell(new ArticleBody(msg.uri, body), self());
                }).build();
    }

}
