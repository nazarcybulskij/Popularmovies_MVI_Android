package nazarko.inveritasoft.com.popularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by nazarko on 17.01.18.
 */

public final class MovieInfo {
    private final int id;
    private final String title;
    private final String overview;
    @SerializedName("backdrop_path")
    private final String backdrop;
    @SerializedName("release_date")
    private final Date releaseDate;
    @SerializedName("poster_path")
    private final String poster;
    @SerializedName("vote_average")
    private final double voteAverage;
    @SerializedName("reviews")
    private final ReviewsPage reviewsPage;
    @SerializedName("videos")
    private final VideosPage videosPage;

    public final int getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }


    public final String getOverview() {
        return this.overview;
    }


    public final String getBackdrop() {
        return this.backdrop;
    }


    public final Date getReleaseDate() {
        return this.releaseDate;
    }


    public final String getPoster() {
        return this.poster;
    }

    public final double getVoteAverage() {
        return this.voteAverage;
    }


    public final ReviewsPage getReviewsPage() {
        return this.reviewsPage;
    }


    public final VideosPage getVideosPage() {
        return this.videosPage;
    }

    public MovieInfo(int id,  String title,  String overview,  String backdrop,  Date releaseDate,  String poster, double voteAverage,  ReviewsPage reviewsPage,  VideosPage videosPage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.backdrop = backdrop;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.voteAverage = voteAverage;
        this.reviewsPage = reviewsPage;
        this.videosPage = videosPage;
    }
}
