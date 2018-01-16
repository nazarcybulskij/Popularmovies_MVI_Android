package nazarko.inveritasoft.com.popularmovies.base.mvi;

import io.reactivex.Observable;

/**
 * Created by nazarko on 12.01.18.
 */

public interface MviViewModel<I extends MviIntent, S extends MviViewState> {

    void processIntents(Observable<I> intents);

    Observable<S> states();
}
