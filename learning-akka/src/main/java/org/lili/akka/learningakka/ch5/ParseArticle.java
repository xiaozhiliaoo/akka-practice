package org.lili.akka.learningakka.ch5;

public class ParseArticle {
    public final String htmlBody;
    public ParseArticle(String url) {
        this.htmlBody = url;
    }
}
