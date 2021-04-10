package edu.karen.nikoghosyan.moviedb.ui.information;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.models.movies.Constants;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class InformationMovieFragment extends Fragment {
    AnimatedBottomBar animatedBottomBar;
    ImageButton ibBack;

    TextView tvTitle;
    TextView tvRating;
    ImageView ivBackdrop;

    private InformationMovieViewModel mViewModel;

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

        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getArguments().getString(Constants.MOVIE_TITLE));

        tvRating = view.findViewById(R.id.tvRating);
        tvRating.setText(String.valueOf(getArguments().getDouble(Constants.MOVIE_RATING)));

        ivBackdrop = view.findViewById(R.id.ivBackdrop);
        Picasso.get().load(getArguments().getString(Constants.MOVIE_BACKDROP_URL)).fit().into(ivBackdrop);

        animatedBottomBar = getActivity().findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setVisibility(View.INVISIBLE);

        ibBack = view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {

            animatedBottomBar.setVisibility(View.VISIBLE);
            getActivity().onBackPressed();
            transitionAnim();

        });
    }

    public void transitionAnim() {
        //getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations().
        //Animation animation = AnimationUtils.
    }
}