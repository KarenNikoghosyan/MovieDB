package edu.karen.nikoghosyan.moviedb.ui.genre;

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
import me.ibrahimsn.lib.CirclesLoadingView;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class GenreMovieFragment extends Fragment {
    private AnimatedBottomBar animatedBottomBar;
    private ViewPager viewPager;

    private ImageButton ibBackFromGenre;
    private TextView tvGenreType;
    private RecyclerView rvGenres;
    private CirclesLoadingView clGenre;

    private GenreMovieViewModel genreMovieViewModel;

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

        ibBackFromGenre.setOnClickListener(v -> {
            viewPager.setVisibility(View.VISIBLE);

//            AppCompatActivity activity = (AppCompatActivity) v.getContext();
//            Fragment fragment = new HomeMovieFragment();

//            activity
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//                    .replace(R.id.fragmentContainer, fragment)
//                    .addToBackStack(null)
//                    .commit();

            getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            animatedBottomBar.setVisibility(View.VISIBLE);
        });

        if (getArguments() == null) {
            return;
        }
        tvGenreType.setText(getArguments().getString(Constants.GENRE_TYPE));

        genreMovieViewModel = new ViewModelProvider(this).get(GenreMovieViewModel.class);
        switch (getArguments().getString(Constants.GENRE_TYPE)) {
            case "Top Rated":
                genreMovieViewModel.getTopRatedLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Upcoming":
                genreMovieViewModel.getUpcomingLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Horror":
                genreMovieViewModel.getHorrorLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Comedy":
                genreMovieViewModel.getComedyLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Crime":
                genreMovieViewModel.getCrimeLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Animation":
                genreMovieViewModel.getAnimationLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
            case "Science Fiction":
                genreMovieViewModel.getScienceFictionLiveData().observe(getViewLifecycleOwner(), movies -> {
                    rvGenres.setAdapter(new GenreAdapter(movies));
                    recyclerViewAnimation();
                });
                break;
        }
    }

    private void getCurrentScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 6));
        }
    }

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