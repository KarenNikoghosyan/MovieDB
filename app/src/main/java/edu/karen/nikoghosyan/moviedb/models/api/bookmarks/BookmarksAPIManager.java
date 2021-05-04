package edu.karen.nikoghosyan.moviedb.models.api.bookmarks;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookmarksAPIManager {
    private ArrayList<Long> movieIDs = new ArrayList<>();
    private final List<Movie> movieList = new ArrayList<>();

    ConnectionPool pool = new ConnectionPool(5, 30000, TimeUnit.MILLISECONDS);
    OkHttpClient client = new OkHttpClient
            .Builder()
            .connectionPool(pool)
            .build();

    Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final BookmarksMovieService bookmarksService = retrofit.create(BookmarksMovieService.class);

    public void getMovies(MutableLiveData<List<Movie>> moviesLiveData) {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                movieIDs = (ArrayList<Long>) documentSnapshot.get("movieIDs");

                if (documentSnapshot.exists()) {
                    if (movieIDs == null) return;
                    for (int i = 0; i < movieIDs.size(); i++) {
                        Call<Movie> movieHTTPRequest = bookmarksService.getMovies(movieIDs.get(i).intValue());
                        movieHTTPRequest.enqueue(new Callback<Movie>() {
                            @Override
                            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                                Movie movieResponse = response.body();
                                if (movieResponse != null) {
                                    movieList.add(movieResponse);
                                    moviesLiveData.postValue(movieList);
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                                Log.w("MyTag", "requestFailed", t);
                            }
                        });
                    }
                    movieList.clear();
                }
            });
        }
    }
}
