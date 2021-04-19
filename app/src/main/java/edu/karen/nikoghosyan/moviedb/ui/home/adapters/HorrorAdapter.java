package edu.karen.nikoghosyan.moviedb.ui.home.adapters;

import android.content.Context;
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

public class HorrorAdapter extends RecyclerView.Adapter<HorrorAdapter.ViewHolder>{
    private final List<Movie> moviesList;
    private Context mContext;
    private Movie movie;

    public HorrorAdapter(List<Movie> movieList) {
        this.moviesList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.home_horror_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorrorAdapter.ViewHolder holder, int position) {
        movie = moviesList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivHorrorHome, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbHomeHorror.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.ivHorrorHome.setImageResource(R.drawable.placeholder_image);
                    }
                });

        holder.ivHorrorHome.setOnClickListener(v -> {
            movie = moviesList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new InformationMovieFragment();

            Constants.getBundle(fragment, movie);

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
    public int getItemCount() {
        return moviesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHorrorHome;
        ProgressBar pbHomeHorror;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivHorrorHome = itemView.findViewById(R.id.ivHorrorHome);
            pbHomeHorror = itemView.findViewById(R.id.pbHomeHorror);
        }
    }
}
