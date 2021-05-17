package edu.karen.nikoghosyan.moviedb.ui.details;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.BookmarksMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;
import edu.karen.nikoghosyan.moviedb.ui.details.adapters.DetailsMovieAdapter;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import me.ibrahimsn.lib.CirclesLoadingView;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class DetailsMovieFragment extends Fragment {

    private AnimatedBottomBar animatedBottomBar;
    private ImageButton ibBack;

    private TextView tvTitle;
    private TextView tvRating;
    private ImageButton ibBookmark;
    private ImageView ivBackdrop;
    private ImageView ivSmallPoster;
    private TextView tvTitleName;
    private TextView tvGenre;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvLanguage;
    private TextView tvNoSimilarMovies;
    private TextView tvNoRecommendations;
    private ViewPager viewPager;

    private RecyclerView rvSimilar;
    private RecyclerView rvRecommendations;

    private CirclesLoadingView clSimilar;
    private CirclesLoadingView clRecommendations;

    private DocumentReference documentReference;
    private FirebaseFirestore fStore;
    private String userID;
    private BookmarksAdapter adapter;

    private boolean isBookmarked = false;

    private DetailsMovieViewModel detailsMovieViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            hideKeyboard();
            viewPager = getActivity().findViewById(R.id.viewPager);
            viewPager.setVisibility(View.INVISIBLE);
        }

        if (getArguments() == null) {
            return;
        }

        clSimilar = view.findViewById(R.id.clSimilar);
        clRecommendations = view.findViewById(R.id.clRecommendations);

        tvNoSimilarMovies = view.findViewById(R.id.tvNoSimilarMovies);
        tvNoSimilarMovies.setVisibility(View.INVISIBLE);

        tvNoRecommendations = view.findViewById(R.id.tvNoRecommendations);
        tvNoRecommendations.setVisibility(View.INVISIBLE);

        tvGenre = view.findViewById(R.id.tvGenre);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getArguments().getString(Constants.MOVIE_TITLE));

        tvTitleName = view.findViewById(R.id.tvTitleName);
        tvTitleName.setText(getArguments().getString(Constants.MOVIE_TITLE));

        tvRating = view.findViewById(R.id.tvRating);
        double formattedNum = Math.round(getArguments().getDouble(Constants.MOVIE_RATING) * 10.0) / 10.0;
        tvRating.setText(String.valueOf(formattedNum));

        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(getArguments().getString(Constants.MOVIE_RELEASE_DATE));

        tvLanguage = view.findViewById(R.id.tvLanguage);
        tvLanguage.setText(getArguments().getString(Constants.MOVIE_LANGUAGE));

        tvOverview = view.findViewById(R.id.tvOverview);
        tvOverview.setText(getArguments().getString(Constants.MOVIE_OVERVIEW));

        rvSimilar = view.findViewById(R.id.rvSimilar);
        rvRecommendations = view.findViewById(R.id.rvRecommendations);

        ivBackdrop = view.findViewById(R.id.ivBackdrop);
        Picasso
                .get()
                .load(getArguments().getString(Constants.MOVIE_BACKDROP_URL))
                .transform(new RoundedCornersTransformation(2, 2))
                .into(ivBackdrop, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        ivBackdrop.setImageResource(R.drawable.placeholder_image);
                    }
                });

        ivSmallPoster = view.findViewById(R.id.ivSmallPoster);
        Picasso
                .get()
                .load(getArguments().getString(Constants.MOVIE_IMAGE_URL))
                .transform(new RoundedCornersTransformation(15, 15))
                .fit()
                .into(ivSmallPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        ivSmallPoster.setImageResource(R.drawable.placeholder_image);
                    }
                });

        ibBookmark = view.findViewById(R.id.ibBookmark);

        isBookmarked();
        bookmarksToggle();

        ibBack = view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {
            viewPager.setVisibility(View.VISIBLE);

            Fragment genre = getParentFragmentManager().findFragmentByTag("GenreTag");
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            if (HomeMovieFragment.isGenre && genre != null) {

                activity
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragmentContainer, genre)
                        .addToBackStack(null)
                        .commit();
                HomeMovieFragment.isGenre = false;
            } else {
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                animatedBottomBar.setVisibility(View.VISIBLE);
            }
        });

        animatedBottomBar = requireActivity().findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setVisibility(View.INVISIBLE);

        getObservers();
    }

    private void bookmarksToggle() {
        ibBookmark.setOnClickListener(v -> {
            fStore = FirebaseFirestore.getInstance();

            if (!isBookmarked) {
                isBookmarked = true;

                ibBookmark.setImageResource(R.drawable.icon_bookmark_selected);
                if (getView() != null)
                    Snackbar.make(getView(), "Added To Bookmarks", Snackbar.LENGTH_SHORT)
                            .setAction("DISMISS", v1 -> {
                            })
                            .show();

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    documentReference = fStore.collection("users").document(userID);

                    Map<String, Object> user = new HashMap<>();
                    user.put("movieIDs", FieldValue.arrayUnion(Constants.MOVIE_ID));
                    user.put("" + Constants.MOVIE_ID, "Bookmarked");

                    documentReference.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            documentReference.update(user).addOnSuccessListener(aVoid -> Log.d("TAG", "Bookmark was added for user: " + userID));

                            addMovie();
                        } else {
                            Log.d("TAG", "User was not found: " + userID);
                        }
                    });
                }
            } else {
                isBookmarked = false;

                ibBookmark.setImageResource(R.drawable.icon_bookmark_unselected);
                if (getView() != null)
                    Snackbar.make(getView(), "Removed From Bookmarks", Snackbar.LENGTH_SHORT)
                            .setAction("DISMISS", v1 -> {
                            })
                            .show();
                documentReference = fStore.collection("users").document(userID);

                Map<String, Object> user = new HashMap<>();
                user.put("movieIDs", FieldValue.arrayRemove(Constants.MOVIE_ID));
                user.put("" + Constants.MOVIE_ID, FieldValue.delete());

                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        documentReference.update(user).addOnSuccessListener(aVoid -> Log.d("TAG", "Bookmark was removed for user" + userID));
                        removeMovie();
                    }
                });
            }
        });
    }

    private void isBookmarked() {
        fStore = FirebaseFirestore.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            documentReference = fStore.collection("users").document(userID);
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.contains("" + Constants.MOVIE_ID)) {
                    ibBookmark.setImageResource(R.drawable.icon_bookmark_selected);
                    isBookmarked = true;
                } else {
                    ibBookmark.setImageResource(R.drawable.icon_bookmark_unselected);
                    isBookmarked = false;
                }
            });
        }
    }

    private void getObservers() {
        detailsMovieViewModel = new ViewModelProvider(this).get(DetailsMovieViewModel.class);
        detailsMovieViewModel.getDetailsException().observe(getViewLifecycleOwner(), throwable -> {

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
        });

        detailsMovieViewModel.getGenresNames().observe(getViewLifecycleOwner(), (genres -> {
            if (getArguments() != null) {
                int[] moviesIDs = getArguments().getIntArray(Constants.MOVIE_GENRE_IDS);

                if (moviesIDs != null) {
                    StringBuilder genresNames = new StringBuilder();

                    int limit = moviesIDs.length;
                    if (moviesIDs.length > 3) limit = 3;

                    for (int i = 0; i < limit; i++) {
                        for (int j = 0; j < genres.size(); j++) {
                            if (moviesIDs[i] == genres.get(j).getGenreId()) {
                                genresNames.append(genres.get(j).getGenreName()).append(",");
                            }
                        }
                    }

                    if (genresNames.length() > 0) {
                        tvGenre.setText(genresNames.substring(0, genresNames.length() - 1));

                    } else {
                        tvGenre.setText(R.string.no_genres_were_found);
                    }
                } else {
                    tvGenre.setText(getArguments().getString(Constants.MOVIE_GENRE).substring(0, getArguments().getString(Constants.MOVIE_GENRE).length() - 1));
                }
            }
        }));

        detailsMovieViewModel.getSimilarMoviesByID().observe(getViewLifecycleOwner(), (movies -> {
            if (movies.size() == 0) {
                tvNoSimilarMovies.setVisibility(View.VISIBLE);
            }

            rvSimilar.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvSimilar.setAdapter(new DetailsMovieAdapter(movies));
            clSimilar.setVisibility(View.GONE);
        }));

        detailsMovieViewModel.getMoviesRecommendations().observe(getViewLifecycleOwner(), (movies -> {
            if (movies.size() == 0) {
                tvNoRecommendations.setVisibility(View.VISIBLE);
            }

            rvRecommendations.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvRecommendations.setAdapter(new DetailsMovieAdapter(movies));
            clRecommendations.setVisibility(View.GONE);
        }));

        SingleMovieViewModel singleMovieViewModel = new ViewModelProvider(this).get(SingleMovieViewModel.class);

        singleMovieViewModel.getSingleBookmarkedMovie().observe(getViewLifecycleOwner(), movies -> {
            adapter = new BookmarksAdapter(movies);
            BookmarksMovieFragment.rvBookmarks.setAdapter(adapter);
            adapter.notifyItemInserted(BookmarksAdapter.movieList.size() - 1);
        });
    }

    public void hideKeyboard() {
        if (getActivity() != null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void addMovie() {
        SingleMovieViewModel singleMovieViewModel = new ViewModelProvider(this).get(SingleMovieViewModel.class);

        singleMovieViewModel.updateData(Constants.MOVIE_ID);
    }

    public void removeMovie() {
        if (BookmarksAdapter.movieList != null) {
            for (int i = 0; i < BookmarksAdapter.movieList.size(); i++) {
                if (BookmarksAdapter.movieList.get(i).getMovieID() == Constants.MOVIE_ID) {
                    BookmarksAdapter.movieList.remove(i);
                    adapter = new BookmarksAdapter(BookmarksAdapter.movieList);
                    adapter.notifyItemRemoved(i);

                    BookmarksMovieFragment.rvBookmarks.setAdapter(adapter);
                }
            }
        }
    }
}