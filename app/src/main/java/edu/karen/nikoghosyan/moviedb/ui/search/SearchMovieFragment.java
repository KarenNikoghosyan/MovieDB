package edu.karen.nikoghosyan.moviedb.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import me.ibrahimsn.lib.CirclesLoadingView;

public class SearchMovieFragment extends Fragment {

    //TODO: Remove this line later
    private EditText etSearch;
    private ImageButton ibSearch;
    private CirclesLoadingView clSearch;
    private boolean isClicked = false;

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
        ibSearch = view.findViewById(R.id.ibSearch);
        rvMovieSearch = view.findViewById(R.id.rvMovieSearch);
        clSearch = view.findViewById(R.id.clSearch);
        clSearch.setVisibility(View.GONE);

        rvMovieSearch.setLayoutManager(new GridLayoutManager(getContext(), 3));


        ibSearch.setOnClickListener(v -> {
            if (getActivity() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            if (etSearch.getText().toString().equals("")) {
                etSearch.setError("Type Something");
                return;
            }

            Constants.MOVIE_SEARCH = etSearch.getText().toString();

            searchMovieViewModel = new ViewModelProvider(this).get(SearchMovieViewModel.class);
            clSearch.setVisibility(View.VISIBLE);

            if (isClicked) {
                searchMovieViewModel.updateMovieWithSearching();
                clSearch.setVisibility(View.GONE);
                rvMovieSearch.scheduleLayoutAnimation();
                return;
            }

            searchMovieViewModel.getMoviesWithSearching().observe(getViewLifecycleOwner(), (movies -> {

                new Handler().postDelayed(() -> {
                    rvMovieSearch.setAdapter(new SearchMovieAdapter(movies));
                    rvMovieSearch.scheduleLayoutAnimation();
                    clSearch.setVisibility(View.GONE);
                    isClicked = true;
                }, 1000);
            }));
        });
    }
}