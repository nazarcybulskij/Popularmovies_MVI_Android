package nazarko.inveritasoft.com.popularmovies.DB.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import nazarko.inveritasoft.com.popularmovies.DB.tables.MovieEntity;

/**
 * Created by nazarko on 17.01.18.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    Flowable<List<MovieEntity>> getAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    Flowable<MovieEntity> getById(int id);

    @Query("SELECT EXISTS(SELECT id FROM movie WHERE id = :id)")
    Flowable<Integer> existsById(int id);

    @Insert(
            onConflict = 1
    )
    long insert( MovieEntity movieEntity);

    @Update
    void update(MovieEntity movieEntity);

    @Query("DELETE FROM movie WHERE id = :id")
    int deleteById(int id);
}
