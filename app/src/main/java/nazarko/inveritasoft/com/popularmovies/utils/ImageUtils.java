package nazarko.inveritasoft.com.popularmovies.utils;

/**
 * Created by nazarko on 22.01.18.
 */

public class ImageUtils {

    public static final String BASE_URL = "http://image.tmdb.org/t/p";


    public static String buildPosterUrl(String imagePath, int width) {
        String widthPath;

        if (width <= 92)
            widthPath = "/w92";
        else if (width <= 154)
            widthPath = "/w154";
        else if (width <= 185)
            widthPath = "/w185";
        else if (width <= 342)
            widthPath = "/w342";
        else if (width <= 500)
            widthPath = "/w500";
        else
            widthPath = "/w780";

        //Timber.v("buildPosterUrl: widthPath=" + widthPath);
        return BASE_URL + widthPath + imagePath;
    }
}
