package nazarko.inveritasoft.com.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import nazarko.inveritasoft.com.popularmovies.grid.GridViewModel;

/**
 * Created by nazarko on 16.01.18.
 */

public class MoviesViewModelFactory  implements ViewModelProvider.Factory {

    private static MoviesViewModelFactory INSTANCE;

    private final Context applicationContext;

    private MoviesViewModelFactory(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static MoviesViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesViewModelFactory(context.getApplicationContext());
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == GridViewModel.class) {
            return (T)new GridViewModel();
        }else{
            return null;
        }
    }
}
