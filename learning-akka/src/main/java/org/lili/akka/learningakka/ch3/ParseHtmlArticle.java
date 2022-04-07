package org.lili.akka.learningakka.ch3;

public class ParseHtmlArticle {
    public final String uri, htmlString;

    public ParseHtmlArticle(String uri, String htmlString) {
        this.uri = uri;
        this.htmlString = htmlString;
    }
}
