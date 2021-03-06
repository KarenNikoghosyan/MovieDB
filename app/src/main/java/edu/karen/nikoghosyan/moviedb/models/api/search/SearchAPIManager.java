package edu.karen.nikoghosyan.moviedb.models.api.search;

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

public class SearchAPIManager {

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

    private final SearchMovieService searchMovieService = retrofit.create(SearchMovieService.class);

    public void getMoviesWithSearching(MutableLiveData<List<Movie>> moviesLiveData, MutableLiveData<Throwable> exceptionCallback,String query){

        Call<MovieResponse> movieHTTPRequest = searchMovieService.getMoviesWithSearching(query);
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

}
