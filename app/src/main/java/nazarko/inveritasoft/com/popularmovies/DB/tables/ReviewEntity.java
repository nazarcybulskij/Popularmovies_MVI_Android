package nazarko.inveritasoft.com.popularmovies.DB.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nazarko on 17.01.18.
 */

@Entity(
        tableName = "review",
        foreignKeys = {@ForeignKey(
                entity = MovieEntity.class,
                childColumns = {"movie_id"},
                onUpdate = 5,
                onDelete = 5,
                parentColumns = {"id"}
        )},
        indices = {@Index({"movie_id"})}
)
public final class ReviewEntity {
    @ColumnInfo(
            name = "movie_id"
    )
    private final int movieId;
    private final String author;
    private final String content;
    private final String url;
    @PrimaryKey(
            autoGenerate = true
    )
    private final long id;

    public final int getMovieId() {
        return this.movieId;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getUrl() {
        return this.url;
    }

    public final long getId() {
        return this.id;
    }

    public ReviewEntity(int movieId, String author,  String content,  String url, long id) {
        this.movieId = movieId;
        this.author = author;
        this.content = content;
        this.url = url;
        this.id = id;
    }
}
