package edu.karen.nikoghosyan.moviedb.models.api.home;

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

public class HomeAPIManager {

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

    private final HomeMovieService homeMovieService = retrofit.create(HomeMovieService.class);

    public void getTopTrending(MutableLiveData<List<Movie>> moviesLiveData, MutableLiveData<Throwable> exceptionCallback) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getTopTrending();

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

    public void getTopRated(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getTopRated();

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

    public void getUpcoming(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getUpcoming();

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

    public void getHorror(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getHorror();

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

    public void getComedy(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getComedy();

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

    public void getCrime(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getCrime();

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

            }
        });
    }

    public void getAnimation(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getAnimation();

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

    public void getScienceFiction(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getScienceFiction();

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

    public void getAction(MutableLiveData<List<Movie>> movieLiveData) {

        Call<MovieResponse> movieHTTPRequest = homeMovieService.getAction();

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

            }
        });
    }
}
