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

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder>{
    private final List<Movie> movieList;
    private Context mContext;
    private Movie movie;

    public UpcomingAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.home_upcoming_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivUpcomingHome, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbHomeUpcoming.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.ivUpcomingHome.setImageResource(R.drawable.placeholder_image);
                    }
                });

        holder.ivUpcomingHome.setOnClickListener(v -> {
            movie = movieList.get(position);

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
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivUpcomingHome;
        private ProgressBar pbHomeUpcoming;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUpcomingHome = itemView.findViewById(R.id.ivHorrorHome);
            pbHomeUpcoming = itemView.findViewById(R.id.pbHomeHorror);
        }
    }
}
