package edu.karen.nikoghosyan.moviedb.models.api.details;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsAPIManager {

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

    private final DetailsMovieService detailsService = retrofit.create(DetailsMovieService.class);

    public void getMoviesBySimilarID(MutableLiveData<List<Movie>> moviesLiveData, MutableLiveData<Throwable> exceptionCallback,int movieID) {

        Call<MovieResponse> movieHTTPRequest = detailsService.getMoviesBySimilarID(movieID);
        movieHTTPRequest.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                if (movieResponse != null) {
                    ArrayList<Movie> movies = movieResponse.getMovies();
                    moviesLiveData.postValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                exceptionCallback.postValue(t);
            }
        });
    }

    public void getRecommendations(MutableLiveData<List<Movie>> moviesLiveData, int movieID) {

        Call<MovieResponse> movieHTTPRequest = detailsService.getRecommendations(movieID);
        movieHTTPRequest.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                if (movieResponse != null) {
                    ArrayList<Movie> movies = movieResponse.getMovies();
                    moviesLiveData.postValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
