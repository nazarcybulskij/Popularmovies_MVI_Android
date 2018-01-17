package nazarko.inveritasoft.com.popularmovies;

import android.content.SharedPreferences;

import io.reactivex.Observable;

/**
 * Created by nazarko on 17.01.18.
 */

public final class SharedPrefs {

    private static final String  KEY_SORT_POS = "KEY_SORT_POS";

    private final SharedPreferences sharedPreferences;

    public SharedPrefs( SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public final Observable<Integer> getSortPos() {
        return   Observable.fromCallable(() -> {
            return sharedPreferences.getInt(KEY_SORT_POS, 0);
        });
    }

    public Observable<Integer> writeSortPos(Integer sortPos){
        return Observable.fromCallable(() -> {
             sharedPreferences.edit().putInt(KEY_SORT_POS, sortPos).apply();
             return sortPos;
        });
    }
}
