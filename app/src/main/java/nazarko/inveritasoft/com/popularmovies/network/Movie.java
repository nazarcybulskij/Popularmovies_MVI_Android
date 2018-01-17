package nazarko.inveritasoft.com.popularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by nazarko on 17.01.18.
 */

public final class Movie {

    private final int id;
    @SerializedName("backdrop_path")
    private final String backdrop;
    private final String overview;
    @SerializedName("release_date")
    private final Date releaseDate;
    @SerializedName("poster_path")
    private final String poster;
    private final String title;
    @SerializedName("vote_average")
    private final double voteAverage;

    public final int getId() {
        return this.id;
    }


    public final String getBackdrop() {
        return this.backdrop;
    }


    public final String getOverview() {
        return this.overview;
    }


    public final Date getReleaseDate() {
        return this.releaseDate;
    }


    public final String getPoster() {
        return this.poster;
    }


    public final String getTitle() {
        return this.title;
    }

    public final double getVoteAverage() {
        return this.voteAverage;
    }

    public Movie(int id, String backdrop, String overview, Date releaseDate, String poster, String title, double voteAverage) {
        this.id = id;
        this.backdrop = backdrop;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.title = title;
        this.voteAverage = voteAverage;
    }
}
