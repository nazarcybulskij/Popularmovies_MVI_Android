package nazarko.inveritasoft.com.popularmovies.grid.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviIntent;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviViewModel;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesAction;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesIntent;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesResult;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesViewState;
import nazarko.inveritasoft.com.popularmovies.grid.SortOption;
import nazarko.inveritasoft.com.popularmovies.network.model.Movie;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;

/**
 * Created by nazarko on 19.01.18.
 */

public class GridMoviesViewModel extends ViewModel implements MviViewModel<GridMoviesIntent,GridMoviesViewState> {

    private PublishSubject<GridMoviesIntent> mIntentsSubject;
    private Observable<GridMoviesViewState> mStatesObservable;
    private SortPage  sortPage ;

    private MovieRepository movieStorage = null;


    public GridMoviesViewModel(MovieRepository movieStorage) {
        this.mIntentsSubject = PublishSubject.create();
        this.mStatesObservable = compose();
        this.movieStorage = movieStorage;
    }

    private Observable<GridMoviesViewState> compose() {
        return  mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(actionToResultTransformer)
                .scan(new GridMoviesViewState.GridMoviewViewState(MviStatus.IDLE,new ArrayList<Movie>(),false,false,true), reducer)
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
        if (intent instanceof GridMoviesIntent.LoadMoreGridMoviesIntent){
            result = new GridMoviesAction.LoadMoreGridMoviesAction();
        }
        if (intent instanceof GridMoviesIntent.RefreshGridMoviesIntent) {
            GridMoviesIntent.RefreshGridMoviesIntent loadingGridMoviesIntent = (GridMoviesIntent.RefreshGridMoviesIntent) intent;
            result = new GridMoviesAction.RefreshGridMoviesAction();
        }
        if (intent instanceof GridMoviesIntent.ChangedFilterMoviesIntent) {
            GridMoviesIntent.ChangedFilterMoviesIntent loadingGridMoviesIntent = (GridMoviesIntent.ChangedFilterMoviesIntent) intent;
            result = new GridMoviesAction.ChangedFilterGridMoviesAction(loadingGridMoviesIntent.option);
        }

        if(result !=null){
            return result;
        }else{
            throw new IllegalArgumentException("do not know how to treat this intent " + intent);
        }
    }

    // Emits loading, success and failure events.
    private ObservableTransformer<GridMoviesAction, GridMoviesResult>
            changedfilterMoviewPageTransformer =  upstream -> upstream.flatMap(
            loadFirstPageAction ->  movieStorage.getOnlMovies(1,((GridMoviesAction.ChangedFilterGridMoviesAction)loadFirstPageAction).option,true)
                    .map(moviesPage -> moviesPage.getMovies())
                    .map(list ->  new GridMoviesResult.LoadingGridMoviesResult(list))
                    .onErrorReturn(throwable ->  new GridMoviesResult.LoadingGridMoviesResult(throwable))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(new GridMoviesResult.LoadingGridMoviesResult(true)));


    // Emits loading, success and failure events.
    private ObservableTransformer<GridMoviesAction, GridMoviesResult>
            refreshMoviewPageTransformer =  upstream -> upstream.flatMap(
            loadFirstPageAction ->  movieStorage.getOnlMovies(1,true)
                    .map(moviesPage -> moviesPage.getMovies())
                    .map(list ->  new GridMoviesResult.RefreshGridMoviesResult(list))
                    .onErrorReturn(throwable ->  new GridMoviesResult.RefreshGridMoviesResult(throwable))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(new GridMoviesResult.RefreshGridMoviesResult(true)));


    // Emits loading, success and failure events.
    private ObservableTransformer<GridMoviesAction, GridMoviesResult>
            loadMoreMoviewPageTransformer =  upstream -> upstream.flatMap(
            loadFirstPageAction ->  movieStorage.getOnlMovies(sortPage.page+1,true)
                    .map(moviesPage -> moviesPage.getMovies())
                    .map(list ->  new GridMoviesResult.LoadMoreGridMoviesResult(list))
                    .onErrorReturn(throwable ->  new GridMoviesResult.LoadMoreGridMoviesResult(throwable))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(new GridMoviesResult.LoadMoreGridMoviesResult(true)));

    private ObservableTransformer<GridMoviesAction, GridMoviesResult> actionToResultTransformer =
            actions -> actions.publish(shared -> Observable.merge(
                    shared.ofType(GridMoviesAction.ChangedFilterGridMoviesAction.class).compose(changedfilterMoviewPageTransformer),
                    shared.ofType(GridMoviesAction.RefreshGridMoviesAction.class).compose(refreshMoviewPageTransformer),
                    shared.ofType(GridMoviesAction.LoadMoreGridMoviesAction.class).compose(loadMoreMoviewPageTransformer))
            );



    private  BiFunction<GridMoviesViewState, GridMoviesResult, GridMoviesViewState> reducer = (tasksViewState, tasksResult) -> {

        GridMoviesViewState.GridMoviewViewState gridMoviesViewState = (GridMoviesViewState.GridMoviewViewState)tasksViewState;

        if(tasksResult instanceof  GridMoviesResult.RefreshGridMoviesResult){
            GridMoviesResult.RefreshGridMoviesResult  refreshGridMoviesResult = (GridMoviesResult.RefreshGridMoviesResult) tasksResult;

            if (refreshGridMoviesResult.status == MviStatus.SUCCESS) {
                sortPage = new SortPage(1);
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.SUCCESS,refreshGridMoviesResult.movies,
                        false,
                        gridMoviesViewState.loadmore,
                        gridMoviesViewState.load);
            }
            if(refreshGridMoviesResult.status == MviStatus.FAILURE){
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.FAILURE,new ArrayList<Movie>(),
                        true,
                        gridMoviesViewState.loadmore,
                        gridMoviesViewState.load);
            }else{
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.IN_FLIGHT,new ArrayList<Movie>(),
                        true,
                        gridMoviesViewState.loadmore,
                        gridMoviesViewState.load);
            }
        }

        if(tasksResult instanceof  GridMoviesResult.LoadingGridMoviesResult){
            GridMoviesResult.LoadingGridMoviesResult  loadingGridMoviesResult = (GridMoviesResult.LoadingGridMoviesResult) tasksResult;
            if (loadingGridMoviesResult.status == MviStatus.SUCCESS) {
                sortPage = new SortPage(1);
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.SUCCESS,loadingGridMoviesResult.movies,
                         gridMoviesViewState.refresh,
                         gridMoviesViewState.loadmore,
                        false);
            }
            if(loadingGridMoviesResult.status == MviStatus.FAILURE){
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.FAILURE,new ArrayList<Movie>(),
                         gridMoviesViewState.refresh,
                         gridMoviesViewState.loadmore,
                        true);
            }else{
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.IN_FLIGHT,new ArrayList<Movie>(),
                        gridMoviesViewState.refresh,
                        gridMoviesViewState.loadmore,
                        true);
            }
        }

        if(tasksResult instanceof  GridMoviesResult.LoadMoreGridMoviesResult){
            GridMoviesResult.LoadMoreGridMoviesResult  loadMoreGridMoviesResult = (GridMoviesResult.LoadMoreGridMoviesResult) tasksResult;
            if (loadMoreGridMoviesResult.status== MviStatus.SUCCESS) {
                sortPage.updatePage();
                List<Movie> movies = ((GridMoviesViewState.GridMoviewViewState)tasksViewState).movies ;
                movies.addAll(loadMoreGridMoviesResult.movies);
                return  new GridMoviesViewState.GridMoviewViewState(loadMoreGridMoviesResult.status,movies,
                        gridMoviesViewState.refresh,
                        true,
                        gridMoviesViewState.load);
            }
            if(loadMoreGridMoviesResult.status == MviStatus.FAILURE){
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.FAILURE,((GridMoviesViewState.GridMoviewViewState)tasksViewState).movies,
                        gridMoviesViewState.refresh,
                        true,
                        gridMoviesViewState.load);
            }else{
                return  new GridMoviesViewState.GridMoviewViewState(MviStatus.IN_FLIGHT,((GridMoviesViewState.GridMoviewViewState)tasksViewState).movies,
                        gridMoviesViewState.refresh,
                        true,
                        gridMoviesViewState.load);
            }

        }

        return  new GridMoviesViewState.GridMoviewViewState(MviStatus.IDLE,new ArrayList<>(),false,false,true);

    };


     class SortPage{

        Integer page;
        SortOption sort;

        public SortPage(Integer page) {
            this.page = page;
            //this.sort = sort;
        }

        public SortPage updatePage() {
            this.page++;
            return this;
        }

        public SortPage update(SortOption sort) {
            if (sort.equals(this.sort)){
                this.page++;
            }else{
                this.sort = sort;
                this.page =1;
            }
            return this;
        }

        public SortPage update(Integer page, SortOption sort) {
            if (sort.equals(this.sort)){
                this.page++;
            }else{
                this.sort = sort;
                this.page =1;
            }

            return this;
        }
    }

}

