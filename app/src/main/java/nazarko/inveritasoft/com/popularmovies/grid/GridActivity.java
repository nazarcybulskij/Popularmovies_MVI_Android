package nazarko.inveritasoft.com.popularmovies.grid;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.base.mvi.MviView;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseActivity;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;

public class GridActivity extends BaseActivity implements BaseFragment.ActivityListener, MviView<GridMoviesIntent,GridMoviesViewState> {

    private GridViewModel mViewModel;

    public Spinner spGridSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initViewModel();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        spGridSort = (Spinner)findViewById(R.id.spGridSort);
        setupSortSpinner();

        if (savedInstanceState == null) {
            addFragment();
        }
    }

    private void initViewModel() {

    }

    private void setupSortSpinner() {
        ArrayAdapter<Sort> adapter = new ArrayAdapter<Sort>(this,R.layout.spinner_item_toolbar,makeSortOptions());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGridSort.setAdapter(adapter);
        spGridSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // emit
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

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
        return Observable.just(new GridMoviesIntent.LoadingGridMoviesIntent());
    }

    @Override
    public void render(GridMoviesViewState state) {
        Toast.makeText(this,"render",Toast.LENGTH_SHORT).show();
    }
}
