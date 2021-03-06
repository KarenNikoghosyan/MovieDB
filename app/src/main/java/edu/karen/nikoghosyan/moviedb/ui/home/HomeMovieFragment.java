package edu.karen.nikoghosyan.moviedb.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.LoadingActivity;
import edu.karen.nikoghosyan.moviedb.LoginActivity;
import edu.karen.nikoghosyan.moviedb.R;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;
import edu.karen.nikoghosyan.moviedb.ui.genre.GenreMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.MovieAdapter;
import edu.karen.nikoghosyan.moviedb.ui.home.adapters.TopTrendingAdapter;
import maes.tech.intentanim.CustomIntent;
import me.ibrahimsn.lib.CirclesLoadingView;

public class HomeMovieFragment extends Fragment {

    private HomeMovieViewModel homeMovieViewModel;
    private RecyclerView rvMoviesHome;
    private RecyclerView rvTopRated;
    private RecyclerView rvUpcoming;
    private RecyclerView rvHorror;
    private RecyclerView rvComedy;
    private RecyclerView rvCrime;
    private RecyclerView rvAnimation;
    private RecyclerView rvScienceFiction;
    private RecyclerView rvAction;
    private ImageButton btnLogout;
    private CircleImageView btnGallery;
    private TextView tvName;

    private CirclesLoadingView clTopTrending;
    private CirclesLoadingView clTopRated;
    private CirclesLoadingView clUpcoming;
    private CirclesLoadingView clHorror;
    private CirclesLoadingView clComedy;
    private CirclesLoadingView clCrime;
    private CirclesLoadingView clAnimation;
    private CirclesLoadingView clScienceFiction;
    private CirclesLoadingView clAction;

    private TextView tvTopRatedButton;
    private TextView tvUpcomingButton;
    private TextView tvHorrorButton;
    private TextView tvComedyButton;
    private TextView tvCrimeButton;
    private TextView tvAnimationButton;
    private TextView tvScienceFictionButton;
    private TextView tvActionButton;

    public static boolean isGenre = false;

    private String userID;
    private DocumentReference documentReference;
    private FirebaseFirestore fStore;
    private String name = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGallery = view.findViewById(R.id.btnGallery);
        tvName = view.findViewById(R.id.tvName);

        //Checks if the current user isn't null
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            fStore = FirebaseFirestore.getInstance();
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            //Gets the userID document from FireStore:
            documentReference = fStore.collection("users").document(userID);
            //If successful:
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                //Sets the image profile by decoding the string from FireStore:
                if (documentSnapshot.contains("profileImage") && !documentSnapshot.getString("profileImage").isEmpty()) {
                    String profileImage = (String) documentSnapshot.get("profileImage");
                    btnGallery.setImageBitmap(decodeBase64(profileImage));
                }
                else {
                    btnGallery.setImageResource(R.drawable.icon_user_default);
                }
                //Sets the user's name:
                if (documentSnapshot.contains("name")) {
                    tvName.setText(documentSnapshot.getString("name"));
                }
                else {
                    tvName.setText("");
                }
            });
        }

        rvMoviesHome = view.findViewById(R.id.rvMoviesHome);
        rvTopRated = view.findViewById(R.id.rvTopRated);
        rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvHorror = view.findViewById(R.id.rvHorror);
        rvComedy = view.findViewById(R.id.rvComedy);
        rvCrime = view.findViewById(R.id.rvCrime);
        rvAnimation = view.findViewById(R.id.rvAnimation);
        rvScienceFiction = view.findViewById(R.id.rvScienceFiction);
        rvAction = view.findViewById(R.id.rvAction);

        clTopTrending = view.findViewById(R.id.clTopTrending);
        clTopRated = view.findViewById(R.id.clTopRated);
        clUpcoming = view.findViewById(R.id.clUpcoming);
        clHorror = view.findViewById(R.id.clHorror);
        clComedy = view.findViewById(R.id.clComedy);
        clCrime = view.findViewById(R.id.clCrime);
        clAnimation = view.findViewById(R.id.clAnimation);
        clScienceFiction = view.findViewById(R.id.clScienceFiction);
        clAction = view.findViewById(R.id.clAction);

        tvTopRatedButton = view.findViewById(R.id.tvTopRatedButton);
        tvUpcomingButton = view.findViewById(R.id.tvUpcomingButton);
        tvHorrorButton = view.findViewById(R.id.tvHorrorButton);
        tvComedyButton = view.findViewById(R.id.tvComedyButton);
        tvCrimeButton = view.findViewById(R.id.tvCrimeButton);
        tvAnimationButton = view.findViewById(R.id.tvAnimationButton);
        tvScienceFictionButton = view.findViewById(R.id.tvScienceFictionButton);
        tvActionButton = view.findViewById(R.id.tvActionButton);

        tvTopRatedButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Top Rated");
        });

        tvUpcomingButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Upcoming");
        });

        tvHorrorButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Horror");
        });

        tvComedyButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Comedy");
        });

        tvCrimeButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Crime");
        });

        tvAnimationButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Animation");
        });

        tvScienceFictionButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Science Fiction");
        });
        tvActionButton.setOnClickListener(v -> {
            loadGenreFragmentByName(v, "Action");
        });

        btnLogout = view.findViewById(R.id.btnLogout);

        getLiveDataObservers();

        //Logout button with a dialog:
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
                        BookmarksAdapter.movieList.clear();
                        Intent toLoginActivity = new Intent(getContext(), LoginActivity.class);
                        startActivity(toLoginActivity);
                        CustomIntent.customType(getContext(), "right-to-left");

                        getActivity().finish();

                        LoadingActivity.isLogged = false;
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getContext(), "Until The Next Time", Toast.LENGTH_LONG).show();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(requireActivity().getColor(R.color.dark_purple));
        });

        //Opens the gallery to choose an image profile:
        btnGallery.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Constants.IMAGE_CODE);
        });
    }

    //Loads the genre fragment when clicking on "ViewAll" on the home screen:
    private void loadGenreFragmentByName(View v, String s) {
        isGenre = true;
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Fragment fragment = new GenreMovieFragment();

        getBundle(fragment, s);

        activity
                .getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down)
                .replace(R.id.fragmentContainer, fragment, "GenreTag")
                .addToBackStack(null)
                .commit();
    }

    private void getBundle(Fragment fragment, String s) {
        Bundle args = new Bundle();
        args.putString(Constants.GENRE_TYPE, s);
        fragment.setArguments(args);
    }

    //Observers:
    private void getLiveDataObservers() {
        homeMovieViewModel = new ViewModelProvider(this).get(HomeMovieViewModel.class);
        homeMovieViewModel.getException().observe(getViewLifecycleOwner(), throwable -> {

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

        homeMovieViewModel.getTopTrendingLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvMoviesHome.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvMoviesHome.setAdapter(new TopTrendingAdapter(movies));
            clTopTrending.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getTopRatedLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvTopRated.setAdapter(new MovieAdapter(movies));
            clTopRated.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getUpcomingLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvUpcoming.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvUpcoming.setAdapter(new MovieAdapter(movies));
            clUpcoming.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getHorrorLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvHorror.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvHorror.setAdapter(new MovieAdapter(movies));
            clHorror.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getComedyLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvComedy.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvComedy.setAdapter(new MovieAdapter(movies));
            clComedy.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getCrimeLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvCrime.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvCrime.setAdapter(new MovieAdapter(movies));
            clCrime.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getAnimationLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvAnimation.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvAnimation.setAdapter(new MovieAdapter(movies));
            clAnimation.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getScienceFictionLiveData().observe(getViewLifecycleOwner(), (movies -> {

            rvScienceFiction.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvScienceFiction.setAdapter(new MovieAdapter(movies));
            clScienceFiction.setVisibility(View.GONE);
        }));

        homeMovieViewModel.getActionLiveData().observe(getViewLifecycleOwner(), movies -> {

            rvAction.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            rvAction.setAdapter(new MovieAdapter(movies));
            clAction.setVisibility(View.GONE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    fStore = FirebaseFirestore.getInstance();
                    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    documentReference = fStore.collection("users").document(userID);

                    //Encoding the image that was chose to Base64 and sending the string to FireStore database:
                    Map<String, Object> user = new HashMap<>();
                    user.put("profileImage", encodeToBase64(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage)));

                    //Updates the user document on FireStore, if successful:
                    documentReference.update(user).addOnSuccessListener(aVoid -> Log.d("TAG", "profile image was set for user: " + userID));
                }
                btnGallery.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
    }

    //Encode to Base64 method:
    private String encodeToBase64(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    //Decode from Base64 method:
    private Bitmap decodeBase64(String input){
        byte[] decodeByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }
}