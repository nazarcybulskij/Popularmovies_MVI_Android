package nazarko.inveritasoft.com.popularmovies;

import android.content.SharedPreferences;

import io.reactivex.Observable;
import nazarko.inveritasoft.com.popularmovies.grid.SortOption;

/**
 * Created by nazarko on 17.01.18.
 */

public final class SharedPrefs {

    private static final String  KEY_SORT_POS = "KEY_SORT_POS";

    private final SharedPreferences sharedPreferences;

    public SharedPrefs( SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public final Observable<SortOption> getSort() {
        return   Observable.fromCallable(() -> {
            String sort  = sharedPreferences.getString(KEY_SORT_POS,SortOption.SORT_POPULARITY.getValue());
            return  SortOption.valueOf(sort);
        });
    }

    public Observable<SortOption> writeSortPos(SortOption sort){
        return Observable.fromCallable(() -> {
             sharedPreferences.edit().putString(KEY_SORT_POS, sort.toString()).apply();
             return sort;
        });
    }
}
