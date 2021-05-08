package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;
import edu.karen.nikoghosyan.moviedb.ui.details.DetailsMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.details.SharedViewModel;

public class BookmarksMovieFragment extends Fragment {

    private SharedViewModel bookmarksMovieViewModel;
    public static RecyclerView rvBookmarks;
    private BookmarksAdapter adapter;
    private Movie movie;

    private String userID;
    private DocumentReference documentReference;
    private FirebaseFirestore fStore;

    @Override
    public void onResume() {
        super.onResume();
        if (DetailsMovieFragment.isClickedBookmark){
            bookmarksMovieViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
            bookmarksMovieViewModel.updateBookmarks();

            DetailsMovieFragment.isClickedBookmark = false;
        }
    }

    private void getCurrentScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvBookmarks.setLayoutManager(new GridLayoutManager(getContext(), 2));        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmarks_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookmarks = view.findViewById(R.id.rvBookmarks);

        getCurrentScreenOrientation();

        getObservers();
    }

    private void getObservers() {
        bookmarksMovieViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        bookmarksMovieViewModel.getBookmarksException().observe(getViewLifecycleOwner(), throwable -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Fatal Error")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_error_outline_24)
                    .setMessage("Couldn't load data. Please check your internet connection.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        requireActivity().finishAffinity();
                        requireActivity().finish();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
        });

        bookmarksMovieViewModel.getBookmarkedMovies().observe(getViewLifecycleOwner(), movies -> {

            adapter = new BookmarksAdapter(movies);
            rvBookmarks.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            recyclerViewItemSwipe();
        });
    }

    private void recyclerViewItemSwipe() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                movie = BookmarksAdapter.movieList.get(viewHolder.getAdapterPosition());
                showSnackBar(getView());

                BookmarksAdapter.movieList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                fStore = FirebaseFirestore.getInstance();
                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                documentReference = fStore.collection("users").document(userID);

                Map<String, Object> user = new HashMap<>();
                user.put("movieIDs", FieldValue.arrayRemove(movie.getMovieID()));
                user.put("" + movie.getMovieID(), FieldValue.delete());

                documentReference.update(user).addOnSuccessListener(aVoid -> Log.d("TAG", "Bookmark was removed for user" + userID));
            }
        }).attachToRecyclerView(rvBookmarks);
    }

    public static void showSnackBar(View v){
        Snackbar.make(v, "Movie Was Removed", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvBookmarks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }
}