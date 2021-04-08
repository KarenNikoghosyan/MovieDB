package edu.karen.nikoghosyan.moviedb.models.api;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.movies.Movie;
import edu.karen.nikoghosyan.moviedb.models.movies.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private final Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final MovieService movieService = retrofit.create(MovieService.class);

    public void getTopTrending(MutableLiveData<List<Movie>> moviesLiveData) {

        Call<MovieResponse> movieHTTPRequest = movieService.getTopTrending();

        movieHTTPRequest.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                if (movieResponse != null){

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

    public void getTopRated(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = movieService.getTopRated();

        movieHTTPRequest.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                if (movieResponse != null) {

                    ArrayList<Movie> movies = movieResponse.getMovies();
                    movieLiveData.postValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
