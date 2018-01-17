package nazarko.inveritasoft.com.popularmovies.network.model;

/**
 * Created by nazarko on 17.01.18.
 */

public final class Review {

    private final String author;
    private final String content;
    private final String url;


    public final String getAuthor() {
        return this.author;
    }


    public final String getContent() {
        return this.content;
    }


    public final String getUrl() {
        return this.url;
    }

    public Review(String author,  String content,  String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }
}