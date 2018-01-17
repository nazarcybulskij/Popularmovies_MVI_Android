package nazarko.inveritasoft.com.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import nazarko.inveritasoft.com.popularmovies.grid.GridViewModel;
import nazarko.inveritasoft.com.popularmovies.grid.Sort;
import nazarko.inveritasoft.com.popularmovies.repo.MovieStorage;

/**
 * Created by nazarko on 17.01.18.
 */

public class GridViewModelFactory implements ViewModelProvider.Factory {


    private static GridViewModelFactory INSTANCE;

    private final Context applicationContext;

    private GridViewModelFactory(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static GridViewModelFactory getInstance(Context context,
                                                   SharedPrefs sharedPrefs,
                                                   MovieStorage movieStorage,
                                                   List<Sort> sortOptions ) {
        if (INSTANCE == null) {
            INSTANCE = new GridViewModelFactory(context.getApplicationContext());
        }
        return INSTANCE;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == GridViewModel.class) {
            return (T)new GridViewModel();
        }else{
            return null;
        }
    }
}
