package nazarko.inveritasoft.com.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;


/**
 * Created by nazarko on 18.01.18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
