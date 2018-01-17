package nazarko.inveritasoft.com.popularmovies.grid;

/**
 * Created by nazarko on 16.01.18.
 */

public class Sort {

   SortOption option;
   String title;

   public Sort(SortOption option, String title) {
      this.option = option;
      this.title = title;
   }

   @Override
   public String toString() {
      return title;
   }
}
