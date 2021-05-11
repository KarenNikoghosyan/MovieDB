package edu.karen.nikoghosyan.moviedb.ui.genre.adapters;

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

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private final List<Movie> moviesList;
    private Movie movie;

    public GenreAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.genre_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = moviesList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivGenre, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbGenre.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.ivGenre.setImageResource(R.drawable.placeholder_image);
                    }
                });

        holder.itemView.setOnClickListener(v -> {
            movie = moviesList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new DetailsMovieFragment();

            getBundle(fragment, movie);

            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivGenre;
        private final ProgressBar pbGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGenre = itemView.findViewById(R.id.ivGenre);
            pbGenre = itemView.findViewById(R.id.pbGenre);
        }
    }

    private void getBundle(Fragment fragment, Movie movie) {
        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE, movie.getTitle());
        args.putDouble(Constants.MOVIE_RATING, movie.getRating());
        args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
        args.putIntArray(Constants.MOVIE_GENRE_IDS, movie.getGenresIDs());
        args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
        Constants.MOVIE_ID = movie.getMovieID();
        args.putString(Constants.MOVIE_IMAGE_URL, movie.getImageURL());
        args.putString(Constants.MOVIE_LANGUAGE, movie.getLanguage());
        fragment.setArguments(args);
    }
}
