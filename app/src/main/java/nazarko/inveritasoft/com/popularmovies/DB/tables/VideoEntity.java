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
        tableName = "video",
        foreignKeys = {@ForeignKey(
                entity = MovieEntity.class,
                childColumns = {"movie_id"},
                onUpdate = 5,
                onDelete = 5,
                parentColumns = {"id"}
        )},
        indices = {@Index({"movie_id"})}
)
public final class VideoEntity {
    @ColumnInfo(
            name = "movie_id"
    )
    private final int movieId;
    private final String name;
    private final String key;
    private final String site;
    private final int size;
    private final String type;
    @PrimaryKey(
            autoGenerate = true
    )
    private final long id;

    public final int getMovieId() {
        return this.movieId;
    }

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

    public final long getId() {
        return this.id;
    }

    public VideoEntity(int movieId,  String name,  String key,  String site, int size,  String type, long id) {
        this.movieId = movieId;
        this.name = name;
        this.key = key;
        this.site = site;
        this.size = size;
        this.type = type;
        this.id = id;
    }

}