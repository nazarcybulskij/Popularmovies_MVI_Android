package nazarko.inveritasoft.com.popularmovies.grid;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import nazarko.inveritasoft.com.popularmovies.GridViewModelFactory;
import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.SharedPrefs;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviView;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;
import nazarko.inveritasoft.com.popularmovies.grid.viewmodel.GridFragmentViewModel;
import nazarko.inveritasoft.com.popularmovies.grid.viewmodel.GridViewModel;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;

/**
 * Created by nazarko on 16.01.18.
 */

public class GridFragment extends BaseFragment<BaseFragment.ActivityListener> implements MviView<GridMoviesIntent,GridMoviesViewState> {


    private SwipeRefreshLayout srlGrid;

    MovieRepository movieStorage;



    private GridFragmentViewModel mViewModel;
    private CompositeDisposable mDisposables;

    private void initData() {
       movieStorage = MovieRepository.getInstance(getActivity().getApplicationContext());
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, GridViewModelFactory.getInstance(getContext().getApplicationContext(),movieStorage)).get(GridFragmentViewModel.class);
        mDisposables = new CompositeDisposable();
        bind();
    }

    private void bind() {
        mDisposables.add(mViewModel.states().subscribe(action->{
            Log.d("TAG_1","RENDER "+ action.toString()) ;
            render(action);
        },throwable -> {
            Log.d("TAG_1",throwable.getMessage());
        },()->{
            Log.d("TAG_1","COMPLIDE");
        }));
        mViewModel.processIntents(intents());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        initData();
        initViews(root);
        initViewModel();
        return root;
    }

    private void initViews(View root) {
        srlGrid = (SwipeRefreshLayout)root.findViewById(R.id.srlGrid);
    }


    @Override
    public Observable<GridMoviesIntent> intents() {
        return Observable.merge(initIntent(),refreshIntent());
    }



    @Override
    public void render(GridMoviesViewState state) {
        if (state instanceof  GridMoviesViewState.LoadingViewState ){
            GridMoviesViewState.LoadingViewState viewState = (GridMoviesViewState.LoadingViewState) state;
            if (viewState.status == MviStatus.IN_FLIGHT){
                srlGrid.setRefreshing(true);
            }
            if (viewState.status == MviStatus.SUCCESS){
                srlGrid.setRefreshing(false);
            }

        }
    }

    //  init
    public  Observable<GridMoviesIntent.InitGridMoviesIntent> initIntent(){
        return Observable.just(new GridMoviesIntent.InitGridMoviesIntent());
    }

    //   refresh
    public Observable<GridMoviesIntent.RefreshGridMoviesIntent> refreshIntent() {
       return  RxSwipeRefreshLayout.refreshes(srlGrid).map(o -> {
           return  new GridMoviesIntent.RefreshGridMoviesIntent(SortOption.SORT_POPULARITY);
       });
    }

    @Override
    public void onDestroy() {
        mDisposables.dispose();
        super.onDestroy();
    }
}
