package edu.karen.nikoghosyan.moviedb.ui.home;

import android.content.Context;
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
import edu.karen.nikoghosyan.moviedb.ui.information.InformationMovieFragment;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {
    private List<Movie> moviesList;
    private Context mContext;
    private Movie movie;

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
                .into(holder.ivTopRatedHome, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbHomeTopRated.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.getMessage();
                    }
                });

        holder.ivTopRatedHome.setOnClickListener(v -> {
            movie = moviesList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new InformationMovieFragment();

            getBundle(fragment);

            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void getBundle(Fragment fragment) {
        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE, movie.getTitle());
        args.putDouble(Constants.MOVIE_RATING, movie.getRating());
        args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
        args.putIntArray(Constants.MOVIE_GENRE_IDS, movie.getGenres());
        args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
        Constants.MOVIE_ID = movie.getMovieID();
        args.putString(Constants.MOVIE_IMAGE_URL, movie.getImageURL());
        args.putString(Constants.MOVIE_Language, movie.getLanguage());
        fragment.setArguments(args);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTopRatedHome;
        ProgressBar pbHomeTopRated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTopRatedHome = itemView.findViewById(R.id.ivTopRatedHome);
            pbHomeTopRated = itemView.findViewById(R.id.pbHomeTopRated);
        }
    }
}
