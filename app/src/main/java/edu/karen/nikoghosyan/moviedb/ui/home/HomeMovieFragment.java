package edu.karen.nikoghosyan.moviedb.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import edu.karen.nikoghosyan.moviedb.R;

public class HomeMovieFragment extends Fragment {

    private HomeMovieViewModel homeMovieViewModel;
    private RecyclerView rvMoviesHome;
    private RecyclerView rvTopRated;
    private ImageButton btnLogout;

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

        new Handler().postDelayed(() -> {
            Snackbar.make(view, "Welcome Back", Snackbar.LENGTH_LONG).show();
        },300);

        rvMoviesHome = view.findViewById(R.id.rvMoviesHome);
        rvTopRated = view.findViewById(R.id.rvTopRated);
        btnLogout = view.findViewById(R.id.btnLogout);

        homeMovieViewModel = new ViewModelProvider(this).get(HomeMovieViewModel.class);

        homeMovieViewModel.getTopTrendingLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvMoviesHome.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvMoviesHome.setAdapter(new TopTrendingAdapter(movies));
        }));

        homeMovieViewModel.getTopRatedLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvTopRated.setAdapter(new TopRatedAdapter(movies));
        }));

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Good Bye", Toast.LENGTH_LONG).show();
        });

    }
}