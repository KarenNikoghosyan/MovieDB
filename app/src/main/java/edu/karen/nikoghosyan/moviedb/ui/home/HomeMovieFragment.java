package edu.karen.nikoghosyan.moviedb.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;

import edu.karen.nikoghosyan.moviedb.LoadingActivity;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.HorrorAdapter;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.TopRatedAdapter;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.TopTrendingAdapter;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.UpcomingAdapter;

public class HomeMovieFragment extends Fragment {

    private HomeMovieViewModel homeMovieViewModel;
    private RecyclerView rvMoviesHome;
    private RecyclerView rvTopRated;
    private RecyclerView rvUpcoming;
    private RecyclerView rvHorror;
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

        rvMoviesHome = view.findViewById(R.id.rvMoviesHome);
        rvTopRated = view.findViewById(R.id.rvTopRated);
        rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvHorror = view.findViewById(R.id.rvHorror);

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

        homeMovieViewModel.getUpcomingLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvUpcoming.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvUpcoming.setAdapter(new UpcomingAdapter(movies));
        }));

        homeMovieViewModel.getHorrorLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvHorror.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvHorror.setAdapter(new HorrorAdapter(movies));
        }));

        btnLogout.setOnClickListener(v -> {
            if (getContext() == null) {
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Logout")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                    .setMessage("You're about to logout, are you sure?")
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    }).setPositiveButton("OK", (dialog, which) -> {
                        if (getActivity() == null) {
                            return;
                        }
                        getActivity().finish();

                        LoadingActivity.isLogged = false;
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getContext(), "Until The Next Time", Toast.LENGTH_LONG).show();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.dark_purple));
        });

    }
}