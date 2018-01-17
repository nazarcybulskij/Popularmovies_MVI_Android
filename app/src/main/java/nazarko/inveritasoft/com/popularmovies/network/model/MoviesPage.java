package nazarko.inveritasoft.com.popularmovies.network.model;




import com.google.gson.annotations.SerializedName;

import java.util.List;

import nazarko.inveritasoft.com.popularmovies.network.model.Movie;

/**
 * Created by nazarko on 17.01.18.
 */

public final class MoviesPage {

    private final int page;
    @SerializedName("results")
    private final List<Movie> movies;
    @SerializedName("total_pages")
    private final int totalPages;
    @SerializedName("total_movies")
    private final int totalMovies;

    public final int getPage() {
        return this.page;
    }

    public final List getMovies() {
        return this.movies;
    }

    public final int getTotalPages() {
        return this.totalPages;
    }

    public final int getTotalMovies() {
        return this.totalMovies;
    }

    public MoviesPage(int page,  List movies, int totalPages, int totalMovies) {
        this.page = page;
        this.movies = movies;
        this.totalPages = totalPages;
        this.totalMovies = totalMovies;
    }

}