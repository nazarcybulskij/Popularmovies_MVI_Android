package nazarko.inveritasoft.com.popularmovies.network.model;

/**
 * Created by nazarko on 17.01.18.
 */

public final class Video {

    private final String name;
    private final String key;
    private final String site;
    private final int size;
    private final String type;



    public final String getName() {
        return this.name;
    }

    public final String getKey() {
        return this.key;
    }


    public final String getSite() {
        return this.site;
    }

    public final int getSize() {
        return this.size;
    }


    public final String getType() {
        return this.type;
    }

    public Video(String name,  String key,  String site, int size,  String type) {
        this.name = name;
        this.key = key;
        this.site = site;
        this.size = size;
        this.type = type;
    }
}