package nazarko.inveritasoft.com.popularmovies.grid;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.ArrayList;
import java.util.List;

import nazarko.inveritasoft.com.popularmovies.R;
import nazarko.inveritasoft.com.popularmovies.network.model.Movie;
import nazarko.inveritasoft.com.popularmovies.utils.ImageUtils;
import nazarko.inveritasoft.com.popularmovies.utils.UiUtils;

/**
 * Created by nazarko on 22.01.18.
 */

public class GridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public final int TYPE_MOVIE = R.layout.item_movie;
    public final int TYPE_PROGRESS = R.layout.item_progress;

    List<Movie> movies = new ArrayList<Movie>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_MOVIE==viewType){
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }else{
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_MOVIE){
            ((ViewHolder)holder).bind(movies.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position<movies.size()){
            return TYPE_MOVIE;
        }else{
            return TYPE_PROGRESS;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size()+1;
    }

    public  void swapData(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mContentContainer;
        ImageView mImageView;
        TextView mTitleView;
        TextView mGenresView;
        View mFooterView;
        ImageButton mFavoriteButton;

        int mColorBackground;
        int mColorTitle;
        int mColorSubtitle;

        private final StringBuilder mBuilder = new StringBuilder(30);
        private long mMovieId;


        public ViewHolder(View v) {
            super(v);

            mContentContainer = v.findViewById(R.id.movie_item_container);
            mImageView = v.findViewById(R.id.movie_item_image);
            mTitleView = v.findViewById(R.id.movie_item_title);
            mGenresView = v.findViewById(R.id.movie_item_genres);
            mFooterView = v.findViewById(R.id.movie_item_footer);
            mFavoriteButton = v.findViewById(R.id.movie_item_btn_favorite);
            mColorBackground = ContextCompat.getColor(v.getContext(),R.color.theme_primary);
            mColorTitle = ContextCompat.getColor(v.getContext(),R.color.body_text_white);
            mColorSubtitle = ContextCompat.getColor(v.getContext(),R.color.body_text_1_inverse);
        }

        public void bind(final Movie movie) {
            mTitleView.setText(movie.getTitle());
            mGenresView.setText(UiUtils.joinGenres(movie.getGenres(), ", ", mBuilder));

            // prevents unnecessary color blinking
            if (mMovieId != movie.getId()) {
                resetColors();
                mMovieId = movie.getId();
            }

            String url = ImageUtils.buildPosterUrl(movie.getPosterPath(), 100);

            Glide.with(mTitleView.getContext())
                    .load(url)
                    .crossFade()
                    .placeholder(R.color.movie_poster_placeholder)
                    .listener(GlidePalette.with(url)
                            .intoCallBack(palette -> applyColors(palette.getVibrantSwatch())))
                    .into(mImageView);
        }

        private void resetColors() {
            mFooterView.setBackgroundColor(mColorBackground);
            mTitleView.setTextColor(mColorTitle);
            mGenresView.setTextColor(mColorSubtitle);
            mFavoriteButton.setColorFilter(mColorTitle, PorterDuff.Mode.MULTIPLY);
        }

        private void applyColors(Palette.Swatch swatch) {
            if (swatch != null) {
                mFooterView.setBackgroundColor(swatch.getRgb());
                mTitleView.setTextColor(swatch.getBodyTextColor());
                mGenresView.setTextColor(swatch.getTitleTextColor());
                mFavoriteButton.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.MULTIPLY);
            }
        }

    }

}
