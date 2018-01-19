package nazarko.inveritasoft.com.popularmovies.grid;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviIntent;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesIntent  extends MviIntent{

    public class LoadingGridMoviesIntent implements GridMoviesIntent{

        public SortOption option;

        public LoadingGridMoviesIntent(SortOption option) {
            this.option = option;
        }


    }

    public class RefreshGridMoviesIntent implements GridMoviesIntent{

        public SortOption option;

        public RefreshGridMoviesIntent(SortOption option) {
            this.option = option;
        }


    }

    public class InitGridMoviesIntent  implements GridMoviesIntent {

    }
}
