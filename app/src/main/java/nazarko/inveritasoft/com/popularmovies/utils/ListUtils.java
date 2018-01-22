package nazarko.inveritasoft.com.popularmovies.utils;

import java.util.Collection;

/**
 * Created by nazarko on 22.01.18.
 */

public class ListUtils{


        public static <E> boolean isEmpty(Collection<E> list) {
            return (list == null || list.size() == 0);
        }

}
