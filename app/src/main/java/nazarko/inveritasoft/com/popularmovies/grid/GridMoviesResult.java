package nazarko.inveritasoft.com.popularmovies.grid;

import nazarko.inveritasoft.com.popularmovies.base.mvi.MviResult;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;

/**
 * Created by nazarko on 16.01.18.
 */

public interface GridMoviesResult  extends MviResult {

    public class LoadingGridMoviesResult  implements GridMoviesResult{

        Throwable throwable;
        MviStatus status;

        public LoadingGridMoviesResult(){
            this.status = MviStatus.SUCCESS;
        }

        public LoadingGridMoviesResult(boolean inFligth){
            this.status = MviStatus.IN_FLIGHT;
        }


        public LoadingGridMoviesResult(Throwable throwable) {
            this.status = MviStatus.FAILURE;
            this.throwable = throwable;
        }

    }
}
