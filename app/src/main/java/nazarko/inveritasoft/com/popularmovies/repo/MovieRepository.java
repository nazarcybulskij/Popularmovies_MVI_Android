package nazarko.inveritasoft.com.popularmovies.repo;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nazarko.inveritasoft.com.popularmovies.BuildConfig;
import nazarko.inveritasoft.com.popularmovies.Constants;
import nazarko.inveritasoft.com.popularmovies.DB.MovieDb;
import nazarko.inveritasoft.com.popularmovies.grid.GridMoviesResult;
import nazarko.inveritasoft.com.popularmovies.grid.SortOption;
import nazarko.inveritasoft.com.popularmovies.network.MovieDbService;
import nazarko.inveritasoft.com.popularmovies.network.model.MoviesPage;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nazarko on 17.01.18.
 */

public class MovieRepository {

    private MovieDbService movieDbService;
    private MovieDb movieDb;


    public MovieRepository(Context   context) {

        Gson gson = new GsonBuilder().create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        this.movieDbService =new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_DB_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieDbService.class);

        this.movieDb = MovieDb.getAppDatabase(context);
    }



    public Observable<MoviesPage> getOnlMovies(int page, SortOption sortOption, boolean fetchAllPages){
        return movieDbService.loadMovies(page, sortOption.getValue(),BuildConfig.MOVIE_DB_API_KEY).toObservable();
    }


//    fun getOnlMovies(page: Int, sortOption: SortOption, fetchAllPages: Boolean): Observable<GetMoviesResult> =
//            if (fetchAllPages) {
//        Observable.range(1, page)
//                .concatMap {
//            theMovieDbService.loadMovies(it, sortOption.value)
//                    .flattenAsObservable { it.movies }
//        }
//                        .toList()
//
//    } else {
//        theMovieDbService.loadMovies(page, sortOption.value)
//                .map { it.movies }
//    }

}