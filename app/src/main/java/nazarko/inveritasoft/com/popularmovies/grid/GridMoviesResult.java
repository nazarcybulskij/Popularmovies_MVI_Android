package nazarko.inveritasoft.com.popularmovies.grid;

import java.util.List;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviResult;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.network.model.Movie;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesResult  extends MviResult {

    public class LoadingGridMoviesResult  implements GridMoviesResult{

        public List<Movie> movies;
        public Throwable throwable;
        public MviStatus status;

        public LoadingGridMoviesResult(List<Movie> movies) {
            this.movies = movies;
            this.status = MviStatus.SUCCESS;
        }

        public LoadingGridMoviesResult(boolean b) {
            this.status = MviStatus.IN_FLIGHT;
        }

        @Override
        public String toString() {
            return status.toString();
        }


        public LoadingGridMoviesResult(Throwable throwable) {
            this.status = MviStatus.FAILURE;
            this.throwable = throwable;
        }
    }

    public class RefreshGridMoviesResult  implements GridMoviesResult{

        public List<Movie> movies;
        public Throwable throwable;
        public MviStatus status;

        public RefreshGridMoviesResult(boolean b) {
            this.status = MviStatus.IN_FLIGHT;
        }

        public RefreshGridMoviesResult(List<Movie> movies) {
            this.movies = movies;
            this.status = MviStatus.SUCCESS;
        }

        @Override
        public String toString() {
            return status.toString();
        }


        public RefreshGridMoviesResult(Throwable throwable) {
            this.status = MviStatus.FAILURE;
            this.throwable = throwable;
        }
    }


    public class LoadMoreGridMoviesResult implements GridMoviesResult{

        public List<Movie> movies;
        public Throwable throwable;
        public MviStatus status;

        public LoadMoreGridMoviesResult(boolean b) {
            this.status = MviStatus.IN_FLIGHT;
        }

        public LoadMoreGridMoviesResult(List<Movie> movies) {
            this.movies = movies;
            this.status = MviStatus.SUCCESS;
        }

        public LoadMoreGridMoviesResult(Throwable throwable) {
            this.status = MviStatus.FAILURE;
            this.throwable = throwable;
        }

        @Override
        public String toString() {
            return status.toString();
        }



    }
}
