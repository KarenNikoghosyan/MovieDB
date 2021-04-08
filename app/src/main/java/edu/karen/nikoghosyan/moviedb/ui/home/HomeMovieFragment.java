package edu.karen.nikoghosyan.moviedb.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.karen.nikoghosyan.moviedb.R;

public class HomeMovieFragment extends Fragment {

    private HomeMovieViewModel homeMovieViewModel;
    private RecyclerView rvMoviesHome;
    private RecyclerView rvTopRated;

    public static HomeMovieFragment newInstance() {
        return new HomeMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMoviesHome = view.findViewById(R.id.rvMoviesHome);
        rvTopRated = view.findViewById(R.id.rvTopRated);

        homeMovieViewModel = new ViewModelProvider(this).get(HomeMovieViewModel.class);

        homeMovieViewModel.getTopTrendingLiveData().observe(getViewLifecycleOwner(), (movies -> {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            rvMoviesHome.setLayoutManager(linearLayoutManager);
            rvMoviesHome.setAdapter(new TopTrendingAdapter(movies));
        }));

        homeMovieViewModel.getTopRatedLiveData().observe(getViewLifecycleOwner(), (movies -> {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            rvTopRated.setLayoutManager(linearLayoutManager);
            rvTopRated.setAdapter(new TopRatedAdapter(movies));
        }));
    }
}