package nazarko.inveritasoft.com.popularmovies.grid;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import nazarko.inveritasoft.com.popularmovies.GridViewModelFactory;
import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviStatus;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviView;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;
import nazarko.inveritasoft.com.popularmovies.grid.viewmodel.GridMoviesViewModel;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;
import nazarko.inveritasoft.com.popularmovies.utils.ListUtils;

/**
 * Created by nazarko on 16.01.18.
 */

public class GridFragment extends BaseFragment<BaseFragment.ActivityListener> implements MviView<GridMoviesIntent,GridMoviesViewState> {


    private SwipeRefreshLayout srlGrid;
    private RecyclerView rvGrid;
    private View emptyView;
    private View pbGrid;

    private  GridRecyclerAdapter gridRecyclerAdapter;

    MovieRepository movieStorage;

    private GridMoviesViewModel mViewModel;
    private CompositeDisposable mDisposables;

    private PublishSubject<GridMoviesIntent.LoadMoreGridMoviesIntent> mLoadNextPageIntentPublisher = PublishSubject.create();

    private void initData() {
       movieStorage = MovieRepository.getInstance(getActivity().getApplicationContext());
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity(), GridViewModelFactory.getInstance(getContext().getApplicationContext(),movieStorage)).get(GridMoviesViewModel.class);
        mDisposables = new CompositeDisposable();
        bind();
    }

    private void bind() {
        mDisposables.add(mViewModel.states().subscribe(action->{
            Log.d("TAG","RENDER "+ action.toString()+action.hashCode()) ;
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




    @Override
    public Observable<GridMoviesIntent> intents() {
        return Observable.merge(initIntent(),refreshIntent(),mLoadNextPageIntentPublisher);
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


    @Override
    public void onDestroy() {
        mDisposables.dispose();
        super.onDestroy();
    }
}
