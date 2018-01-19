package nazarko.inveritasoft.com.popularmovies.grid.viewmodel;

import android.arch.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviIntent;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewModel;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesAction;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesIntent;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesResult;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesViewState;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;

/**
 * Created by nazarko on 19.01.18.
 */

public class GridFragmentViewModel extends ViewModel implements MviViewModel<GridMoviesIntent,GridMoviesViewState> {

    private PublishSubject<GridMoviesIntent> mIntentsSubject;
    private Observable<GridMoviesViewState> mStatesObservable;

    private MovieRepository movieStorage = null;


    public GridFragmentViewModel(MovieRepository movieStorage) {
        this.mIntentsSubject = PublishSubject.create();
        this.mStatesObservable = compose();
        this.movieStorage = movieStorage;
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
                            shared.ofType(GridMoviesIntent.InitGridMoviesIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof GridMoviesIntent.InitGridMoviesIntent))
                    )
            );

    private GridMoviesAction actionFromIntent(MviIntent intent) {

        GridMoviesAction result = null;

        if (intent instanceof GridMoviesIntent.InitGridMoviesIntent){
            result = new GridMoviesAction.InitGridMoviesAction();
        }

        if (intent instanceof GridMoviesIntent.RefreshGridMoviesIntent) {
            GridMoviesIntent.RefreshGridMoviesIntent loadingGridMoviesIntent = (GridMoviesIntent.RefreshGridMoviesIntent) intent;
            result = new GridMoviesAction.LoadingGridMoviesAction(loadingGridMoviesIntent.option);
        }
        if(result !=null){
            return result;
        }else{
            throw new IllegalArgumentException("do not know how to treat this intent " + intent);
        }
    }

    // Emits loading, success and failure events.
    private ObservableTransformer<GridMoviesAction, GridMoviesResult>
            loadMoviewPageTransformer =  upstream -> upstream.flatMap(
            loadFirstPageAction ->  movieStorage.getOnlMovies(1,((GridMoviesAction.LoadingGridMoviesAction)loadFirstPageAction).option,true)
                    .map(moviesPage -> moviesPage.getMovies())
                    .map(list ->  new GridMoviesResult.LoadingGridMoviesResult())
                    .onErrorReturn(throwable ->  new GridMoviesResult.LoadingGridMoviesResult(throwable))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(new GridMoviesResult.LoadingGridMoviesResult(true)));


    private ObservableTransformer<GridMoviesAction, GridMoviesResult> actionToResultTransformer =
            actions -> actions.publish(shared ->  shared.ofType(GridMoviesAction.LoadingGridMoviesAction.class).compose(loadMoviewPageTransformer));





    private static BiFunction<GridMoviesViewState, GridMoviesResult, GridMoviesViewState> reducer = (tasksViewState, tasksResult) -> {
        if(tasksResult instanceof  GridMoviesResult.LoadingGridMoviesResult){
            GridMoviesResult.LoadingGridMoviesResult  loadingGridMoviesResult = (GridMoviesResult.LoadingGridMoviesResult) tasksResult;
            return  new GridMoviesViewState.LoadingViewState(loadingGridMoviesResult.status);
        }
        return  new GridMoviesViewState.IdleViewState();



    };

}

