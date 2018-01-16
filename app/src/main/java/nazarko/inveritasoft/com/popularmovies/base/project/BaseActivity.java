package nazarko.inveritasoft.com.popularmovies.base.project;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nazarko on 16.01.18.
 */

public class BaseActivity extends AppCompatActivity implements LifecycleOwner {

    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
