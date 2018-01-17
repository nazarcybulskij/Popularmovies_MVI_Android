package nazarko.inveritasoft.com.popularmovies.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import nazarko.inveritasoft.com.popularmovies.DB.dao.MovieDao;
import nazarko.inveritasoft.com.popularmovies.DB.dao.ReviewDao;
import nazarko.inveritasoft.com.popularmovies.DB.dao.VideoDao;
import nazarko.inveritasoft.com.popularmovies.DB.tables.MovieEntity;
import nazarko.inveritasoft.com.popularmovies.DB.tables.ReviewEntity;
import nazarko.inveritasoft.com.popularmovies.DB.tables.VideoEntity;

/**
 * Created by nazarko on 17.01.18.
 */

@Database(
        entities = {MovieEntity.class, VideoEntity.class, ReviewEntity.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class MovieDb extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract ReviewDao reviewDao();

    public abstract VideoDao videoDao();
}