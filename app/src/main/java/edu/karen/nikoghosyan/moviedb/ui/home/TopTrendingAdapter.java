package edu.karen.nikoghosyan.moviedb.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.information.InformationMovieFragment;

public class TopTrendingAdapter extends RecyclerView.Adapter<TopTrendingAdapter.ViewHolder> {
    private List<Movie> moviesList;
    Context mContext;
    Movie movie;

    public TopTrendingAdapter(List<Movie> movies) {
        this.moviesList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.home_toptrending_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = moviesList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivTopTrending);

        holder.ivTopTrending.setOnClickListener(v -> {
            movie = moviesList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new InformationMovieFragment();

            Bundle args = new Bundle();
            args.putString(Constants.MOVIE_TITLE, movie.getTitle());
            args.putDouble(Constants.MOVIE_RATING, movie.getRating());
            args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
            args.putIntArray(Constants.MOVIE_GENRE_IDS, movie.getGenres());
            args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
            args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
            fragment.setArguments(args);

            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivTopTrending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTopTrending = itemView.findViewById(R.id.ivTopTrendingHome);
        }
    }
}
