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

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.Constants;
import edu.karen.nikoghosyan.moviedb.models.movies.Movie;
import edu.karen.nikoghosyan.moviedb.ui.information.InformationMovieFragment;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {
    private List<Movie> moviesList;
    Context mContext;
    Movie movie;

    public TopRatedAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.home_top_rated_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = moviesList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivTopRatedHome);

        holder.ivTopRatedHome.setOnClickListener(v -> {
            movie = moviesList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new InformationMovieFragment();

            Bundle args = new Bundle();
            args.putString(Constants.MOVIE_TITLE, movie.getTitle());
            args.putDouble(Constants.MOVIE_RATING, movie.getRating());
            args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
            fragment.setArguments(args);

            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTopRatedHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTopRatedHome = itemView.findViewById(R.id.ivTopRatedHome);
        }
    }
}
