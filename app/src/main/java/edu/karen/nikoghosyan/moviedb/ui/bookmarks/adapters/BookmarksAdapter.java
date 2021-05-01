package edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.details.DetailsMovieFragment;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{
    private List<Movie> movieList;
    private Movie movie;

    public BookmarksAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public BookmarksAdapter() {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.bookmarks_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivBookmarkPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbBookmarkPoster.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.pbBookmarkPoster.setVisibility(View.GONE);
                        holder.ivBookmarkPoster.setImageResource(R.drawable.placeholder_image);
                    }
                });

        holder.itemView.setOnClickListener(v -> {
            movie = movieList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new DetailsMovieFragment();

            getBundle(fragment, movie);

            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    //TODO: Add this condition to more places
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBookmarkPoster;
        ProgressBar pbBookmarkPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookmarkPoster = itemView.findViewById(R.id.ivBookmarkPoster);
            pbBookmarkPoster = itemView.findViewById(R.id.pbBookmarkPoster);
        }
    }

    public void getBundle(Fragment fragment, Movie movie){
        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE, movie.getTitle());
        args.putDouble(Constants.MOVIE_RATING, movie.getRating());
        args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
        //args.putIntArray(Constants.MOVIE_GENRE_IDS, movie.getGenresIDs());
        args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
        Constants.MOVIE_ID = movie.getMovieID();
        args.putString(Constants.MOVIE_IMAGE_URL, movie.getImageURL());
        args.putString(Constants.MOVIE_Language, movie.getLanguage());
        fragment.setArguments(args);
    }
}
