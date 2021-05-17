package edu.karen.nikoghosyan.moviedb.models.api.details;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleMovieAPIManager {
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

    private final SingleMovieService singleService = retrofit.create(SingleMovieService.class);

    public void getSingleMovie(MutableLiveData<List<Movie>> moviesLiveData, MutableLiveData<Throwable> exceptionCallback){

        Call<Movie> movieHTTPRequest = singleService.getMovies(Constants.MOVIE_ID);
        movieHTTPRequest.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieResponse = response.body();
                if (movieResponse != null) {
                    //BookmarksAPIManager.movieList.add(movieResponse);
                    //moviesLiveData.postValue(BookmarksAPIManager.movieList);
                    BookmarksAdapter.movieList.add(movieResponse);
                    moviesLiveData.postValue(BookmarksAdapter.movieList);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                exceptionCallback.postValue(t);
            }
        });

    }
}
