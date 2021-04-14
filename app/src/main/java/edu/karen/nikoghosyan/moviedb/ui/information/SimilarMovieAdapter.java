package edu.karen.nikoghosyan.moviedb.ui.information;

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

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>{
    private final List<Movie> movieList;
    private Movie movie;

    public SimilarMovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.information_similar_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivSimilarMovie, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbInfoSimilarMovies.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.getMessage();
                    }
                });

        holder.ivSimilarMovie.setOnClickListener(v -> {
            movie = movieList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new InformationMovieFragment();

            Constants.getBundle(fragment, movie);

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
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivSimilarMovie;
        ProgressBar pbInfoSimilarMovies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSimilarMovie = itemView.findViewById(R.id.ivSimilarMovie);
            pbInfoSimilarMovies = itemView.findViewById(R.id.pbInfoSimilarMovies);
        }
    }
}
