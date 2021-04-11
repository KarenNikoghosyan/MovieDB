package edu.karen.nikoghosyan.moviedb.models.api.information;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationAPIManager {

    Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final InformationMovieService informationService = retrofit.create(InformationMovieService.class);

    public void getMoviesBySimilarID(MutableLiveData<List<Movie>> moviesLiveData, int movieID) {

        Call<MovieResponse> movieHTTPRequest = informationService.getMoviesBySimilarID(movieID);
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
