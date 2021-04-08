package edu.karen.nikoghosyan.moviedb.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.Movie;

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

            Toast.makeText(mContext, movie.getTitle(), Toast.LENGTH_SHORT).show();
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
