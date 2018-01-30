package nazarko.inveritasoft.com.popularmovies.grid;

import java.util.ArrayList;
import java.util.List;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewState;
import nazarko.inveritasoft.com.popularmovies.network.model.Movie;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesViewState extends MviViewState {

    public class GridMoviewViewState implements GridMoviesViewState{

        public List<Movie> movies ;
        public MviStatus status;

        public boolean  refresh;
        public boolean  loadmore;
        public boolean  load;

        public GridMoviewViewState( MviStatus status,List<Movie> movies, boolean refresh, boolean loadmore, boolean load) {
            this.movies = movies;
            this.status = status;
            this.refresh = refresh;
            this.loadmore = loadmore;
            this.load = load;
        }


        @Override
        public String toString() {
            return status.toString();
        }

        public static GridMoviewViewState idle(){
            return new GridMoviesViewState.GridMoviewViewState(MviStatus.IDLE,new ArrayList<Movie>(),false,false,true);
        }
    }


}
