package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;
import edu.karen.nikoghosyan.moviedb.ui.details.DetailsMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.details.SharedViewModel;

public class BookmarksMovieFragment extends Fragment {

    private SharedViewModel bookmarksMovieViewModel;
    public static RecyclerView rvBookmarks;
    private BookmarksAdapter adapter;

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

        bookmarksMovieViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        bookmarksMovieViewModel.getBookmarkedMovies().observe(getViewLifecycleOwner(), movies -> {

            adapter = new BookmarksAdapter(movies);
            rvBookmarks.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
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