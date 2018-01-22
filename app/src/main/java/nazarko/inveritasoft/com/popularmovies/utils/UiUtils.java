package nazarko.inveritasoft.com.popularmovies.utils;

import android.support.annotation.NonNull;

import java.util.List;

import nazarko.inveritasoft.com.popularmovies.network.model.Genre;

/**
 * Created by nazarko on 22.01.18.
 */

public class UiUtils {


    public static String joinGenres(List<Genre> genres, String delimiter, @NonNull StringBuilder builder) {
        builder.setLength(0);
        if (!ListUtils.isEmpty(genres))
            for (Genre genre : genres) {
                if (builder.length() > 0) builder.append(delimiter);
                builder.append(genre.getName());
            }
        return builder.toString();
    }
}
