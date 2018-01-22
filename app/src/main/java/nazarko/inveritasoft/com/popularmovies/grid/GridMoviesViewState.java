package nazarko.inveritasoft.com.popularmovies.grid;

import java.util.List;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewState;
import nazarko.inveritasoft.com.popularmovies.network.model.Movie;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesViewState extends MviViewState {

    public class RefreshViewState implements GridMoviesViewState{

        public List<Movie> movies ;
        public MviStatus status;

        public RefreshViewState( MviStatus status,List<Movie> movies){
            this.status = status;
            this.movies = movies;
        }

        @Override
        public String toString() {
            return status.toString();
        }
    }

    public class LoadingViewState implements GridMoviesViewState{

        public MviStatus status;
        public List<Movie> movies ;

        public LoadingViewState( MviStatus status,List<Movie> movies){
            this.status = status;
            this.movies = movies;
        }

        @Override
        public String toString() {
            return status.toString();
        }
    }



    public class IdleViewState implements GridMoviesViewState {

        @Override
        public String toString() {
            return "IdleViewState";
        }
    }



}
