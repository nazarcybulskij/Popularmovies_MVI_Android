package nazarko.inveritasoft.com.popularmovies.grid;

import android.arch.lifecycle.ViewModel;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviIntent;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewModel;

/**
 * Created by nazarko on 16.01.18.
 */

public class GridViewModel extends ViewModel implements MviViewModel<GridMoviesIntent,GridMoviesViewState> {

    private PublishSubject<GridMoviesIntent> mIntentsSubject;
    private Observable<GridMoviesViewState> mStatesObservable;

    public GridViewModel() {
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }

    private Observable<GridMoviesViewState> compose() {
        return  mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(actionToResultTransformer)
                .scan(new GridMoviesViewState.IdleViewState(), reducer)
                .replay(1)
                .autoConnect(0);
    }



    @Override
    public void processIntents(Observable<GridMoviesIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }

    @Override
    public Observable<GridMoviesViewState> states() {
        return mStatesObservable;
    }

    private ObservableTransformer<GridMoviesIntent, GridMoviesIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(GridMoviesIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof GridMoviesIntent))
                    )
            );

    private GridMoviesAction actionFromIntent(MviIntent intent) {
        try{
            return  new GridMoviesAction.LoadingGridMoviesAction();
        } catch (Exception ex){
            throw new IllegalArgumentException("do not know how to treat this intent " + intent);
        }
    }

    // Emits loading, success and failure events.
    private ObservableTransformer<GridMoviesAction, GridMoviesResult>
            onePageTransformer =  actions -> actions.flatMap(
            loadFirstPageAction -> Observable.just(new GridMoviesResult.LoadingGridMoviesResult())
                    .delay(4, TimeUnit.SECONDS)
                    .onErrorReturn(throwable ->  new GridMoviesResult.LoadingGridMoviesResult(throwable))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(new GridMoviesResult.LoadingGridMoviesResult(true)));



    private ObservableTransformer<GridMoviesAction, GridMoviesResult> actionToResultTransformer =
            actions -> actions.publish(shared -> shared.ofType(GridMoviesAction.class).compose(onePageTransformer));



    private static BiFunction<GridMoviesViewState, GridMoviesResult, GridMoviesViewState> reducer = (tasksViewState, tasksResult) -> {
        return  new GridMoviesViewState.IdleViewState();
    };

}
