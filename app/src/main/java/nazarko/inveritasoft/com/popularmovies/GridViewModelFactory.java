package nazarko.inveritasoft.com.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import nazarko.inveritasoft.com.popularmovies.grid.viewmodel.GridMoviesViewModel;
import nazarko.inveritasoft.com.popularmovies.repo.MovieRepository;

/**
 * Created by nazarko on 17.01.18.
 */

public class GridViewModelFactory implements ViewModelProvider.Factory {


    private static GridViewModelFactory INSTANCE;

    private final Context applicationContext;
    private final MovieRepository movieStorage;

    private GridViewModelFactory(Context applicationContext, MovieRepository movieStorage) {
        this.applicationContext = applicationContext;
        this.movieStorage = movieStorage;

    }

    public static GridViewModelFactory getInstance(Context context,
                                                   MovieRepository movieStorage) {
        if (INSTANCE == null) {
            INSTANCE = new GridViewModelFactory(context.getApplicationContext(),movieStorage);
        }
        return INSTANCE;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == GridMoviesViewModel.class) {
            return (T)new GridMoviesViewModel(movieStorage);
        }
        return null;

    }
}
