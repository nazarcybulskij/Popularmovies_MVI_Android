package nazarko.inveritasoft.com.popularmovies.DB.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import nazarko.inveritasoft.com.popularmovies.DB.tables.VideoEntity;

/**
 * Created by nazarko on 17.01.18.
 */

@Dao
public interface VideoDao {

    @Query("SELECT id, movie_id, name, key, site, size, type FROM video " + "WHERE movie_id = :movieId")
    Flowable<List<VideoEntity>> getByMovieId(int movieId);

    @Insert
    void insertAll( List<VideoEntity> videos);

    @Query("DELETE FROM video WHERE movie_id IN (SELECT id FROM movie WHERE id = :movieId)")
    int deleteByMovieId(int movieId);

}
