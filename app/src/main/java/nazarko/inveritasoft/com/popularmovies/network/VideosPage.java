package nazarko.inveritasoft.com.popularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nazarko on 17.01.18.
 */

public final class VideosPage {
    @SerializedName("results")
    private final List<Video> videos;

    public final List getVideos() {
        return this.videos;
    }

    public VideosPage( List videos) {
        this.videos = videos;
    }
}
