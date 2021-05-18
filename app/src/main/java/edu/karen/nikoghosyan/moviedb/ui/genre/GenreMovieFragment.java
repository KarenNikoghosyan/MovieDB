package edu.karen.nikoghosyan.moviedb.ui.genre;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.genre.adapters.GenreAdapter;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.ActionViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.AnimationViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.ComedyViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.CrimeViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.TopRatedViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.HorrorViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.ScienceFictionViewModel;
import edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels.UpcomingViewModel;
import me.ibrahimsn.lib.CirclesLoadingView;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class GenreMovieFragment extends Fragment {
    private AnimatedBottomBar animatedBottomBar;
    private ViewPager viewPager;

    private ImageButton ibBackFromGenre;
    private TextView tvGenreType;
    private RecyclerView rvGenres;
    private CirclesLoadingView clGenre;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.genre_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            viewPager = getActivity().findViewById(R.id.viewPager);
            viewPager.setVisibility(View.INVISIBLE);
        }

        ibBackFromGenre = view.findViewById(R.id.ibBackFromGenre);
        tvGenreType = view.findViewById(R.id.tvGenreType);
        animatedBottomBar = requireActivity().findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setVisibility(View.INVISIBLE);
        rvGenres = view.findViewById(R.id.rvGenres);
        clGenre = view.findViewById(R.id.clGenre);

        getCurrentScreenOrientation();

        //Return/Back button:
        ibBackFromGenre.setOnClickListener(v -> {
            viewPager.setVisibility(View.VISIBLE);

            getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            animatedBottomBar.setVisibility(View.VISIBLE);
        });

        if (getArguments() == null) {
            return;
        }
        tvGenreType.setText(getArguments().getString(Constants.GENRE_TYPE));

        //Checks the genre type that was sent from the home screen and loads the fragment accordingly:
        switch (getArguments().getString(Constants.GENRE_TYPE)) {
            case "Top Rated":
                TopRatedViewModel topRatedViewModel = new ViewModelProvider(this).get(TopRatedViewModel.class);
                topRatedViewModel.getTopRatedLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                topRatedViewModel.getExceptionTopRated().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Upcoming":
                UpcomingViewModel upcomingViewModel = new ViewModelProvider(this).get(UpcomingViewModel.class);
                upcomingViewModel.getUpcomingLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                upcomingViewModel.getExceptionUpcoming().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Horror":
                HorrorViewModel horrorViewModel = new ViewModelProvider(this).get(HorrorViewModel.class);
                horrorViewModel.getHorrorLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                horrorViewModel.getExceptionHorror().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Comedy":
                ComedyViewModel comedyViewModel = new ViewModelProvider(this).get(ComedyViewModel.class);
                comedyViewModel.getComedyLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                comedyViewModel.getExceptionComedy().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Crime":
                CrimeViewModel crimeViewModel = new ViewModelProvider(this).get(CrimeViewModel.class);
                crimeViewModel.getCrimeLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                crimeViewModel.getExceptionCrime().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Animation":
                AnimationViewModel animationViewModel = new ViewModelProvider(this).get(AnimationViewModel.class);
                animationViewModel.getAnimationLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                animationViewModel.getExceptionAnimation().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Science Fiction":
                ScienceFictionViewModel scienceFictionViewModel = new ViewModelProvider(this).get(ScienceFictionViewModel.class);
                scienceFictionViewModel.getScienceFictionLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                scienceFictionViewModel.getExceptionScienceFiction().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
            case "Action":
                ActionViewModel actionViewModel = new ViewModelProvider(this).get(ActionViewModel.class);
                actionViewModel.getActionLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                actionViewModel.getExceptionAction().observe(getViewLifecycleOwner(), throwable -> {
                    showExceptionError();
                });
                break;
        }
    }

    //Gets the current screen orientation:
    private void getCurrentScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 6));
        }
    }

    //RecyclerView animation:
    private void recyclerViewAnimation() {
        rvGenres.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                clGenre.setVisibility(View.GONE);
                rvGenres.getViewTreeObserver().removeOnPreDrawListener(this);

                for (int i = 0; i < rvGenres.getChildCount(); i++) {
                    View v = rvGenres.getChildAt(i);
                    v.setAlpha(0.0f);
                    v.animate().alpha(1.0f)
                            .setDuration(300)
                            .setStartDelay(i * 50)
                            .start();
                }
                return true;
            }
        });
    }

    //Shows error dialog if no internet connection was found
    private void showExceptionError(){
        clGenre.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Connectivity Error")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setMessage("Couldn't load data. Please check your internet connection.")
                .setPositiveButton("OK", (dialog, which) -> {
                });

        AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
    }

    //Screen orientation listener
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 6));
        }
    }
}