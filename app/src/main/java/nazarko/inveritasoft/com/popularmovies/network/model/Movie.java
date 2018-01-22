package nazarko.inveritasoft.com.popularmovies.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import nazarko.inveritasoft.com.popularmovies.utils.ListUtils;

import static nazarko.inveritasoft.com.popularmovies.Constants.MOVIE_DB_BASE_URL;

/**
 * Created by nazarko on 17.01.18.
 */

public final class Movie implements Parcelable{

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3";

    @Expose
    long id;

    @Expose @SerializedName("genre_ids")
    List<Integer> genreIds = new ArrayList<>();

    @Expose
    String overview;

    @Expose @SerializedName("release_date")
    String releaseDate;

    @Expose @SerializedName("poster_path")
    String posterPath;

    @Expose @SerializedName("backdrop_path")
    String backdropPath;

    @Expose
    double popularity;

    @Expose
    String title;

    @Expose @SerializedName("vote_average")
    double voteAverage;

    @Expose @SerializedName("vote_count")
    long voteCount;

    boolean favored = false;

    List<Genre> genres;


    @Override
    public String toString() {
        return "Movie{" + " title='" + title + '}';
    }


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeList(this.genreIds);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeByte(favored ? (byte) 1 : (byte) 0);
        dest.writeTypedList(genres);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, List.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.favored = in.readByte() != 0;
        this.genres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {return new Movie(source);}

        public Movie[] newArray(int size) {return new Movie[size];}
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return   backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath =  backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavored() {
        return favored;
    }

    public void setFavored(boolean favored) {
        this.favored = favored;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
