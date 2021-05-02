package edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.GenreObject;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.details.DetailsMovieFragment;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{
    public static List<Movie> movieList;
    private Movie movie;

    private DocumentReference documentReference;
    private FirebaseFirestore fStore;
    private String userID;
    private StringBuilder genresNames;

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

        holder.ibBookmarkSwitch.setOnClickListener(v -> {
            movie = movieList.get(position);

            movieList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());

            fStore = FirebaseFirestore.getInstance();

            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            documentReference = fStore.collection("users").document(userID);

            Map<String, Object> user = new HashMap<>();
            user.put("movieIDs", FieldValue.arrayRemove(movie.getMovieID()));
            user.put("" + movie.getTitle(), FieldValue.delete());

            documentReference.update(user).addOnSuccessListener(aVoid -> Log.d("TAG", "Bookmark was removed for user" + userID));
        });

        Picasso
                .get()
                .load(movie.getImageURL())
                .fit()
                .transform(new RoundedCornersTransformation(10, 10))
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

        holder.tvBookmarkTitle.setText(movie.getTitle());
        holder.tvBookmarkReleaseDate.setText(movie.getReleaseDate());

        GenreObject[] movieIDs = movie.getGenres();
        genresNames = new StringBuilder();

        for (GenreObject movieID : movieIDs) {
            genresNames.append(movieID.getName()).append(",");
        }
        if (genresNames.length() > 0) {
            holder.tvBookmarkGenre.setText(genresNames.substring(0, genresNames.length() - 1));

        } else {
            holder.tvBookmarkGenre.setText("No genres were found");
        }

        holder.tvBookmarkLanguage.setText(movie.getLanguage());
        holder.tvBookmarkRating.setText(String.valueOf(movie.getRating()));

        holder.itemView.setOnClickListener(v -> {
            movie = movieList.get(position);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new DetailsMovieFragment();

            getBundle(fragment, movie);

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
    //TODO: Add this condition to more places
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivBookmarkPoster;
        private ProgressBar pbBookmarkPoster;
        private ImageButton ibBookmarkSwitch;
        private TextView tvBookmarkTitle;
        private TextView tvBookmarkReleaseDate;
        private TextView tvBookmarkGenre;
        private TextView tvBookmarkLanguage;
        private TextView tvBookmarkRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookmarkPoster = itemView.findViewById(R.id.ivBookmarkPoster);
            pbBookmarkPoster = itemView.findViewById(R.id.pbBookmarkPoster);
            ibBookmarkSwitch = itemView.findViewById(R.id.ibBookmarkSwitch);
            tvBookmarkTitle = itemView.findViewById(R.id.tvBookmarkTitle);
            tvBookmarkReleaseDate = itemView.findViewById(R.id.tvBookmarkReleaseDate);
            tvBookmarkGenre = itemView.findViewById(R.id.tvBookmarkGenre);
            tvBookmarkLanguage = itemView.findViewById(R.id.tvBookmarkLanguage);
            tvBookmarkRating = itemView.findViewById(R.id.tvBookmarkRating);
        }
    }

    public void getBundle(Fragment fragment, Movie movie){
        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE, movie.getTitle());
        args.putDouble(Constants.MOVIE_RATING, movie.getRating());
        args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
        args.putString(Constants.MOVIE_GENRE, String.valueOf(genresNames));
        args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
        Constants.MOVIE_ID = movie.getMovieID();
        args.putString(Constants.MOVIE_IMAGE_URL, movie.getImageURL());
        args.putString(Constants.MOVIE_Language, movie.getLanguage());
        fragment.setArguments(args);
    }
}
