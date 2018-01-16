package nazarko.inveritasoft.com.popularmovies.grid;

/**
 * Created by nazarko on 16.01.18.
 */

enum SortOption {


    SORT_POPULARITY("popularity.desc"),
    SORT_RATING( "vote_average.desc"),
    SORT_RELEASE_DATE("release_date.desc"),
    SORT_FAVORITE("favorite");

    SortOption(String value ) {

    }


}
