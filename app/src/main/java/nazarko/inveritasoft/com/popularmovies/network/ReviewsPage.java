package nazarko.inveritasoft.com.popularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nazarko on 17.01.18.
 */

public final class ReviewsPage {

    private final int page;
    @SerializedName("results")
    private final List reviews;
    @SerializedName("total_pages")
    private final int totalPages;
    @SerializedName("total_results")
    private final int totalResults;

    public final int getPage() {
        return this.page;
    }


    public final List getReviews() {
        return this.reviews;
    }

    public final int getTotalPages() {
        return this.totalPages;
    }

    public final int getTotalResults() {
        return this.totalResults;
    }

    public ReviewsPage(int page,  List reviews, int totalPages, int totalResults) {
        this.page = page;
        this.reviews = reviews;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
}