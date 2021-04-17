package edu.karen.nikoghosyan.moviedb.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.information.InformationMovieFragment;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>{
    private List<Movie> movieList;
    private List<Genre> genreList;
    private Context mContext;
    private Movie movie;

    public SearchMovieAdapter(List<Movie> movieList, List<Genre> genresList) {
        this.movieList = movieList;
        this.genreList = genresList;
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
                .into(holder.ivSearchPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbSearchPoster.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.ivSearchPoster.setImageResource(R.drawable.placeholder_image);
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

        holder.tvSearchTitle.setText(movie.getTitle());
        getGenres(holder);
        holder.tvSearchReleaseDate.setText(movie.getReleaseDate());
    }

    private void getGenres(@NonNull ViewHolder holder) {
        int[] movieIDs = movie.getGenres();
        StringBuilder genresNames = new StringBuilder();

        ((Runnable) () -> {
            int limit = movieIDs.length;
            if (movieIDs.length > 3) limit = 3;

            for (int i = 0; i < limit; i++) {
                for (int j = 0; j < genreList.size(); j++) {
                    if (movieIDs[i] == genreList.get(j).getGenreId()){
                        genresNames.append(genreList.get(j).getGenreName()).append(",");
                    }
                }
            }
        }).run();

        if (genresNames.length() > 0) {
            holder.tvSearchGenre.setText(genresNames.substring(0, genresNames.length() - 1));
        }
        else {
            holder.tvSearchGenre.setText("No genres were found");
        }
        holder.tvSearchLanguage.setText(movie.getLanguage());
        holder.tvSearchRating.setText(String.valueOf(movie.getRating()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivSearchPoster;
        TextView tvSearchReleaseDate;
        TextView tvSearchTitle;
        TextView tvSearchGenre;
        TextView tvSearchRating;
        TextView tvSearchLanguage;
        ProgressBar pbSearchPoster;
        CardView searchCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSearchPoster = itemView.findViewById(R.id.ivSearchPoster);
            tvSearchReleaseDate = itemView.findViewById(R.id.tvSearchReleaseDate);
            tvSearchTitle = itemView.findViewById(R.id.tvSearchTitle);
            tvSearchGenre = itemView.findViewById(R.id.tvSearchGenre);
            tvSearchRating = itemView.findViewById(R.id.tvSearchRating);
            tvSearchLanguage = itemView.findViewById(R.id.tvSearchLanguage);
            pbSearchPoster = itemView.findViewById(R.id.pbSearchPoster);
            searchCardView = itemView.findViewById(R.id.searchCardView);
        }
    }
}
