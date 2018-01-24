package nazarko.inveritasoft.com.popularmovies.grid;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import nazarko.inveritasoft.com.popularmovies.GridViewModelFactory;
import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviView;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;
import nazarko.inveritasoft.com.popularmovies.grid.viewmodel.GridFragmentViewModel;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;
import nazarko.inveritasoft.com.popularmovies.utils.ListUtils;

/**
 * Created by nazarko on 16.01.18.
 */

public class GridFragment extends BaseFragment<BaseFragment.ActivityListener> implements MviView<GridMoviesIntent,GridMoviesViewState> {


    private SwipeRefreshLayout srlGrid;
    private Spinner spGridSort;
    private RecyclerView rvGrid;
    private  View emptyView;
    private  View pbGrid;

    private  GridRecyclerAdapter gridRecyclerAdapter;

    MovieRepository movieStorage;

    private GridFragmentViewModel mViewModel;
    private CompositeDisposable mDisposables;
    private List<Sort> sortOptions;

    private PublishSubject<GridMoviesIntent.LoadMoreGridMoviesIntent> mLoadNextPageIntentPublisher = PublishSubject.create();

    private void initData() {
       movieStorage = MovieRepository.getInstance(getActivity().getApplicationContext());
       sortOptions = makeSortOptions();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, GridViewModelFactory.getInstance(getContext().getApplicationContext(),movieStorage)).get(GridFragmentViewModel.class);
        mDisposables = new CompositeDisposable();
        bind();
    }

    private void bind() {
        mDisposables.add(mViewModel.states().subscribe(action->{
            Log.d("TAG","RENDER "+ action.toString()) ;
            render(action);
        },throwable -> {
            Log.d("TAG",throwable.getMessage());
        },()->{
            Log.d("TAG","COMPLIDE");
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
        rvGrid = (RecyclerView)root.findViewById(R.id.rvGrid);
        emptyView = root.findViewById(R.id.empty_view);
        pbGrid = root.findViewById(R.id.pb_grid);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        spGridSort = (Spinner)root.findViewById(R.id.spGridSort);
        setupSortSpinner();

        int spanCount = 2;
        gridRecyclerAdapter = new GridRecyclerAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (gridRecyclerAdapter.getItemViewType(position) == R.layout.item_progress)
                    return spanCount;
                else
                    return 1;
            }
        });
        rvGrid.setLayoutManager(layoutManager);
        rvGrid.setHasFixedSize(true);
        rvGrid.setAdapter(gridRecyclerAdapter);

        Mugen.with(rvGrid, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                mLoadNextPageIntentPublisher.onNext(new GridMoviesIntent.LoadMoreGridMoviesIntent());
            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();
    }

    private void setupSortSpinner() {
        ArrayAdapter<Sort> adapter = new ArrayAdapter<Sort>(getActivity(),R.layout.spinner_item_toolbar,sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGridSort.setAdapter(adapter);
    }

    private List<Sort> makeSortOptions() {
        List<Sort> result = new ArrayList<>();
        result.add(new Sort(SortOption.SORT_POPULARITY, getString(R.string.sort_popularity)));
        result.add(new Sort(SortOption.SORT_RATING, getString(R.string.sort_rating)));
        result.add(new Sort(SortOption.SORT_RELEASE_DATE, getString(R.string.sort_release_date)));
        return result;
    }


    @Override
    public Observable<GridMoviesIntent> intents() {
        return Observable.merge(initIntent(),refreshIntent(),changedFilter(),mLoadNextPageIntentPublisher);
    }



    @Override
    public void render(GridMoviesViewState state) {
        if (state instanceof  GridMoviesViewState.GridMoviewViewState ){
            GridMoviesViewState.GridMoviewViewState viewState = (GridMoviesViewState.GridMoviewViewState) state;


            pbGrid.setVisibility(viewState.load  ? View.VISIBLE : View.INVISIBLE);
            srlGrid.setRefreshing(viewState.refresh);
            rvGrid.setVisibility(viewState.load  ? View.INVISIBLE : View.VISIBLE);


            if (viewState.status == MviStatus.SUCCESS){
                gridRecyclerAdapter.swapData(viewState.movies);
                emptyView.setVisibility(ListUtils.isEmpty(viewState.movies) ? View.VISIBLE : View.INVISIBLE );
            }
            if (viewState.status == MviStatus.FAILURE){
                pbGrid.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
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
           return  new GridMoviesIntent.RefreshGridMoviesIntent();
       });
    }

    //  Spinner
    public Observable<GridMoviesIntent.ChangedFilterMoviesIntent> changedFilter() {
        return RxAdapterView.itemSelections(spGridSort).map(integer -> {
            return  new GridMoviesIntent.ChangedFilterMoviesIntent(sortOptions.get(integer).option);
        }).skip(1);
    }

    @Override
    public void onDestroy() {
        mDisposables.dispose();
        super.onDestroy();
    }
}
