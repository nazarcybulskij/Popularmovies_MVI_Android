package nazarko.inveritasoft.com.popularmovies.grid;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewState;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesViewState extends MviViewState {

    public class LoadingViewState implements GridMoviesViewState{

        MviStatus status;

        public LoadingViewState( MviStatus status){
            this.status = status;
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
