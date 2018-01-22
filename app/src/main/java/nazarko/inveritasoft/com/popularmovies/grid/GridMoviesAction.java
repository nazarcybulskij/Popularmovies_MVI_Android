package nazarko.inveritasoft.com.popularmovies.grid;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviAction;

/**
 * Created by nazarko on 16.01.18.
 */

public interface  GridMoviesAction extends MviAction {

    public class ChangedFilterGridMoviesAction implements GridMoviesAction {

        public  SortOption option;

        public ChangedFilterGridMoviesAction(SortOption option) {
            this.option = option;
        }
    }

    public class RefreshGridMoviesAction implements GridMoviesAction {

    }

    public class InitGridMoviesAction implements GridMoviesAction {

    }


}
