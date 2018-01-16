package nazarko.inveritasoft.com.popularmovies.grid;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.base.project.BaseFragment;

/**
 * Created by nazarko on 16.01.18.
 */

public class GridFragment extends BaseFragment<BaseFragment.ActivityListener> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        return root;
    }
}
