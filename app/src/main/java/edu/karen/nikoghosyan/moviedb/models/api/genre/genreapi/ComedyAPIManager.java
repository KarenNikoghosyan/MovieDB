package edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.karen.nikoghosyan.moviedb.models.api.genre.GenreService;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComedyAPIManager {
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private int numberOfCalls = 10;
    private int page = 0;

    ConnectionPool pool = new ConnectionPool(5, 30000, TimeUnit.MILLISECONDS);
    OkHttpClient client = new OkHttpClient
            .Builder()
            .connectionPool(pool)
            .build();

    private final Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final GenreService genreService = retrofit.create(GenreService.class);

    public void getComedy(MutableLiveData<List<Movie>> moviesLiveData) {
        page++;
        Call<MovieResponse> movieHTTPRequest = genreService.getComedy(page);

        movieHTTPRequest.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null) {
                    ArrayList<Movie> movies = movieResponse.getMovies();
                    moviesList.addAll(movies);

                    numberOfCalls--;
                    if (numberOfCalls > 0) {
                        getComedy(moviesLiveData);
                    }
                    else {
                        moviesLiveData.postValue(moviesList);
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
