package nazarko.inveritasoft.com.popularmovies.grid;

import android.os.Bundle;

import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseActivity;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;

public class GridActivity extends BaseActivity implements BaseFragment.ActivityListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        if (savedInstanceState == null) {
            addFragment();
        }
    }

    private void addFragment() {
        GridFragment fragment = new GridFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_main, fragment, fragment.getClass().getCanonicalName())
                .commit();

    }


}
