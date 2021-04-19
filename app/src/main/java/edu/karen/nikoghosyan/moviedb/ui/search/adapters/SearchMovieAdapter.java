package edu.karen.nikoghosyan.moviedb.ui.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.information.InformationMovieFragment;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {
    private List<Movie> movieList;
    private Context mContext;
    private Movie movie;

    public SearchMovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.search_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivSearch, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbSearch.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.ivSearch.setImageResource(R.drawable.placeholder_image);
                        holder.pbSearch.setVisibility(View.GONE);
                    }
                });

        holder.searchCardView.setOnClickListener(v -> {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSearch;
        CardView searchCardView;
        ProgressBar pbSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchCardView = itemView.findViewById(R.id.searchCardView);
            ivSearch = itemView.findViewById(R.id.ivSearch);
            pbSearch = itemView.findViewById(R.id.pbSearch);
        }
    }

    public void updateData(List<Movie> movies) {
        movies.clear();
        notifyDataSetChanged();
    }
}
