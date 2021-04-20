package edu.karen.nikoghosyan.moviedb.ui.information.adapters;

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

public class InformationMovieAdapter extends RecyclerView.Adapter<InformationMovieAdapter.ViewHolder>{
    private final List<Movie> movieList;
    private Movie movie;

    public InformationMovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.information_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivInformation, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbInformation.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.pbInformation.setVisibility(View.GONE);
                        holder.ivInformation.setImageResource(R.drawable.placeholder_image);
                    }
                });

        holder.ivInformation.setOnClickListener(v -> {
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
        private ImageView ivInformation;
        private ProgressBar pbInformation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivInformation = itemView.findViewById(R.id.ivInformation);
            pbInformation = itemView.findViewById(R.id.pbInformation);
        }
    }
}
