package edu.karen.nikoghosyan.moviedb.ui.information;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.MainActivity;
import edu.karen.nikoghosyan.moviedb.R;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class InformationMovieFragment extends Fragment {

    AnimatedBottomBar animatedBottomBar;
    ImageButton ibBack;

    TextView tvTitle;
    TextView tvRating;
    ImageView ivBackdrop;
    TextView tvGenre;
    TextView tvReleaseDate;
    TextView tvOverview;

    private InformationMovieViewModel informationMovieViewModel;

    public static InformationMovieFragment newInstance() {
        return new InformationMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.information_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGenre = view.findViewById(R.id.tvGenre);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getArguments().getString(Constants.MOVIE_TITLE));

        tvRating = view.findViewById(R.id.tvRating);
        tvRating.setText(String.valueOf(getArguments().getDouble(Constants.MOVIE_RATING)));

        ivBackdrop = view.findViewById(R.id.ivBackdrop);
        Picasso.get().load(getArguments().getString(Constants.MOVIE_BACKDROP_URL)).fit().into(ivBackdrop);

        informationMovieViewModel = new ViewModelProvider(this).get(InformationMovieViewModel.class);
        informationMovieViewModel.getGenresNames().observe(getViewLifecycleOwner(), (genres -> {

            int[] moviesIDs = getArguments().getIntArray(Constants.MOVIE_GENRE_IDS);

            StringBuilder genresNames = new StringBuilder();

            ((Runnable) () -> {

                int limit = moviesIDs.length;
                if (moviesIDs.length > 3) limit = 3;

                for (int i = 0; i < limit; i++) {
                    for (int j = 0; j < genres.size(); j++) {
                        if (moviesIDs[i] == genres.get(j).getGenreId()) {
                            genresNames.append(genres.get(j).getGenreName()).append(",");
                        }
                    }
                }
                tvGenre.setText(genresNames.substring(0, genresNames.length() - 1));
            }).run();
        }));

        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(getArguments().getString(Constants.MOVIE_RELEASE_DATE));

        tvOverview = view.findViewById(R.id.tvOverview);
        tvOverview.setText(getArguments().getString(Constants.MOVIE_OVERVIEW));
        tvOverview.setMovementMethod(new ScrollingMovementMethod());

        animatedBottomBar = getActivity().findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setVisibility(View.INVISIBLE);

        ibBack = view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {

            animatedBottomBar.setVisibility(View.VISIBLE);
            transitionAnim();
        });
    }

    public void transitionAnim() {
        MainActivity mainActivity = new MainActivity();

        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
                .replace(R.id.fragmentContainer, mainActivity.getCurrentFragment())
                .commit();
    }
}