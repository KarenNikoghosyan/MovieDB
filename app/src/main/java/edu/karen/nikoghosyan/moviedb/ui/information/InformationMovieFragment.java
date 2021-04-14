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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.MainActivity;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.favorite.FavoriteMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.search.SearchMovieFragment;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class InformationMovieFragment extends Fragment {

    private AnimatedBottomBar animatedBottomBar;
    private ImageButton ibBack;

    private TextView tvTitle;
    private TextView tvRating;
    private ImageView ivBackdrop;
    private ImageView ivSmallPoster;
    private TextView tvGenre;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvLanguage;
    private TextView tvNoSimilarMovies;

    private RecyclerView rvSimilar;

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

        if (getArguments() == null) { return; }

        tvNoSimilarMovies = view.findViewById(R.id.tvNoSimilarMovies);
        tvNoSimilarMovies.setVisibility(View.INVISIBLE);

        tvGenre = view.findViewById(R.id.tvGenre);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getArguments().getString(Constants.MOVIE_TITLE));

        tvRating = view.findViewById(R.id.tvRating);
        tvRating.setText(String.valueOf(getArguments().getDouble(Constants.MOVIE_RATING)));

        ivBackdrop = view.findViewById(R.id.ivBackdrop);
        Picasso
                .get()
                .load(getArguments().getString(Constants.MOVIE_BACKDROP_URL))
                .transform(new RoundedCornersTransformation(10, 10))
                .fit()
                .into(ivBackdrop);

        ivSmallPoster = view.findViewById(R.id.ivSmallPoster);
        Picasso
                .get()
                .load(getArguments().getString(Constants.MOVIE_IMAGE_URL))
                .transform(new RoundedCornersTransformation(15, 15))
                .fit()
                .into(ivSmallPoster);

        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(getArguments().getString(Constants.MOVIE_RELEASE_DATE));

        tvLanguage = view.findViewById(R.id.tvLanguage);
        tvLanguage.setText(getArguments().getString(Constants.MOVIE_Language));

        tvOverview = view.findViewById(R.id.tvOverview);
        tvOverview.setText(getArguments().getString(Constants.MOVIE_OVERVIEW));
        tvOverview.setMovementMethod(new ScrollingMovementMethod());

        rvSimilar = view.findViewById(R.id.rvSimilar);

        ibBack = view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {
            animatedBottomBar.setVisibility(View.VISIBLE);
            getParentFragmentManager().popBackStack();
        });

        animatedBottomBar = requireActivity().findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setVisibility(View.INVISIBLE);

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
            }).run();
            tvGenre.setText(genresNames.substring(0, genresNames.length() - 1));
        }));

        informationMovieViewModel.getSimilarMoviesByID().observe(getViewLifecycleOwner(), (movies -> {
            if (movies.size() == 0) {
                tvNoSimilarMovies.setVisibility(View.VISIBLE);
                return;
            }

            rvSimilar.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvSimilar.setAdapter(new SimilarMovieAdapter(movies));
        }));
    }
}