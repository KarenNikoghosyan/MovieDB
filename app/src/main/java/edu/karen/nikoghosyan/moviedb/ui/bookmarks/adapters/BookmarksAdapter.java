package edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{
    private final List<Movie> movieList;
    private Movie movie;

    public BookmarksAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.bookmarks_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movie = movieList.get(position);

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .into(holder.ivBookmarkPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbBookmarkPoster.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.pbBookmarkPoster.setVisibility(View.GONE);
                        holder.ivBookmarkPoster.setImageResource(R.drawable.placeholder_image);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBookmarkPoster;
        ProgressBar pbBookmarkPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookmarkPoster = itemView.findViewById(R.id.ivBookmarkPoster);
            pbBookmarkPoster = itemView.findViewById(R.id.pbBookmarkPoster);
        }
    }
}
