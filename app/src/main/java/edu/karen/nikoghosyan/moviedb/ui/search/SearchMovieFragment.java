package edu.karen.nikoghosyan.moviedb.ui.search;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.adapters.TextChangedAdapter;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.search.adapters.SearchMovieAdapter;
import me.ibrahimsn.lib.CirclesLoadingView;

public class SearchMovieFragment extends Fragment {

    private EditText etSearch;
    private CirclesLoadingView clSearch;
    private SearchMovieAdapter adapter;
    private ImageView ivSearchError;
    private TextView tvSearchErrorMessage;
    private List<Movie> moviesList;

    private RecyclerView rvMovieSearch;

    private SearchMovieViewModel searchMovieViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.etSearch);
        rvMovieSearch = view.findViewById(R.id.rvMovieSearch);

        clSearch = view.findViewById(R.id.clSearch);
        clSearch.setVisibility(View.GONE);

        ivSearchError = view.findViewById(R.id.ivSearchError);
        ivSearchError.setVisibility(View.GONE);

        tvSearchErrorMessage = view.findViewById(R.id.tvSearchErrorMessage);
        tvSearchErrorMessage.setVisibility(View.GONE);

        getCurrentScreenOrientation();

        etSearch.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> {
            getObservers();
        });

        onScrollListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        clSearch.setVisibility(View.GONE);
    }

    private void getCurrentScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovieSearch.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvMovieSearch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        }
    }

    private void getObservers() {
        Constants.MOVIE_SEARCH = etSearch.getText().toString();

        searchMovieViewModel = new ViewModelProvider(this).get(SearchMovieViewModel.class);
        clSearch.setVisibility(View.VISIBLE);

        searchMovieViewModel.updateMovieWithSearching();

        searchMovieViewModel.getMoviesWithSearching().observe(getViewLifecycleOwner(), (movies -> {
            moviesList = movies;

            rvMovieSearch.setAdapter(new SearchMovieAdapter(movies));

            clSearch.setVisibility(View.GONE);
            ivSearchError.setVisibility(View.GONE);
            tvSearchErrorMessage.setVisibility(View.GONE);

            if (etSearch.getText().length() <= 0) {
                adapter = new SearchMovieAdapter(movies);
                adapter.updateData(movies);
            }
        }));

        new Handler().postDelayed(() -> {
            if (moviesList == null) return;

            if (moviesList.size() == 0 && etSearch.getText().length() > 0) {
                ivSearchError.setVisibility(View.VISIBLE);
                tvSearchErrorMessage.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    private void onScrollListener() {
        rvMovieSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy != 0) {

                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovieSearch.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvMovieSearch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        }
    }
}