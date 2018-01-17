package nazarko.inveritasoft.com.popularmovies.grid;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import nazarko.inveritasoft.com.popularmovies.GridViewModelFactory;
import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.SharedPrefs;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviView;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseActivity;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;
import nazarko.inveritasoft.com.popularmovies.repo.MovieStorage;

public class GridActivity extends BaseActivity implements BaseFragment.ActivityListener, MviView<GridMoviesIntent,GridMoviesViewState> {

    public Spinner spGridSort;
    SharedPrefs sharedPrefs;
    MovieStorage movieStorage;
    List<Sort> sortOptions;

    private GridViewModel mViewModel;
    // Used to manage the data flow lifecycle and avoid memory leak.
    private CompositeDisposable mDisposables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        spGridSort = (Spinner)findViewById(R.id.spGridSort);
        spGridSort.setSelection(0);
        setupSortSpinner();

        initViewModel();

        if (savedInstanceState == null) {
            addFragment();
        }
    }

    private void initData() {
        sharedPrefs = new SharedPrefs( PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        movieStorage = new MovieStorage();
        sortOptions = makeSortOptions();
    }

    @Override
    protected void onDestroy() {
        mDisposables.dispose();
        super.onDestroy();
    }


    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, GridViewModelFactory.getInstance(this,sharedPrefs,movieStorage,sortOptions)).get(GridViewModel.class);
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

    private void setupSortSpinner() {
        ArrayAdapter<Sort> adapter = new ArrayAdapter<Sort>(this,R.layout.spinner_item_toolbar,sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGridSort.setAdapter(adapter);
    }

    private List<Sort> makeSortOptions() {
        List<Sort> result = new ArrayList<>();
        result.add(new Sort(SortOption.SORT_POPULARITY, getString(R.string.sort_popularity)));
        result.add(new Sort(SortOption.SORT_RATING, getString(R.string.sort_rating)));
        result.add(new Sort(SortOption.SORT_RELEASE_DATE, getString(R.string.sort_release_date)));
        result.add(new Sort(SortOption.SORT_FAVORITE, getString(R.string.sort_favorite)));
        return result;
    }

    private void addFragment() {
        GridFragment fragment = new GridFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_main, fragment, fragment.getClass().getCanonicalName())
                .commit();

    }

    @Override
    public Observable<GridMoviesIntent> intents() {
        return Observable.merge(initIntent(),changedFilter());
    }

    @Override
    public void render(GridMoviesViewState state) {
        //spGridSort.setSelection(0);
    }

    //  init
    public  Observable<GridMoviesIntent.InitGridMoviesIntent> initIntent(){
        return Observable.just(new GridMoviesIntent.InitGridMoviesIntent());
    }

    //   Spinner
    public Observable<GridMoviesIntent.LoadingGridMoviesIntent> changedFilter() {
        return RxAdapterView.itemSelections(spGridSort).map(integer -> {
            return  new GridMoviesIntent.LoadingGridMoviesIntent();
        }).skip(1);

    }

}
