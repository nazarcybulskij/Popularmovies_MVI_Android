package nazarko.inveritasoft.com.popularmovies.DB.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by nazarko on 17.01.18.
 */

@Entity(tableName = "movie")
public final class MovieEntity {
    @PrimaryKey
    private final int id;
    private final String title;
    @ColumnInfo(name = "release_date")
    private final Date releaseDate;
    @ColumnInfo(name = "vote_average")
    private final double voteAverage;

    private final String overview;

    private final String poster;

    private final String backdrop;

    public final int getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }


    public final Date getReleaseDate() {
        return this.releaseDate;
    }

    public final double getVoteAverage() {
        return this.voteAverage;
    }


    public final String getOverview() {
        return this.overview;
    }


    public final String getPoster() {
        return this.poster;
    }


    public final String getBackdrop() {
        return this.backdrop;
    }

    public MovieEntity(int id, String title,  Date releaseDate, double voteAverage,  String overview,  String poster, String backdrop) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.poster = poster;
        this.backdrop = backdrop;
    }
}
