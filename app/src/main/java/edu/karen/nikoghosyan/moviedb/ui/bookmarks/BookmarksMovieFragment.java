package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.karen.nikoghosyan.moviedb.R;

public class BookmarksMovieFragment extends Fragment {

    private BookmarksMovieViewModel mViewModel;

    public static BookmarksMovieFragment newInstance() {
        return new BookmarksMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmarks_movie_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BookmarksMovieViewModel.class);
        // TODO: Use the ViewModel
    }

}