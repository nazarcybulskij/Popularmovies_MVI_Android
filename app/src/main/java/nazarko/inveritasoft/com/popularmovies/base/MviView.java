package nazarko.inveritasoft.com.popularmovies.base;

import io.reactivex.Observable;

/**
 * Created by nazarko on 12.01.18.
 */

public interface MviView<I extends MviIntent, S extends MviViewState> {

    Observable<I> intents();

    void render(S state);

}
