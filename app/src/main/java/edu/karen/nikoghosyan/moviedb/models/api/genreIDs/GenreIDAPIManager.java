package edu.karen.nikoghosyan.moviedb.models.api.genreIDs;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.GenreResponse;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenreIDAPIManager {

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

    private final GenreIDService genreIDService = retrofit.create(GenreIDService.class);

    public void getGenreNames(MutableLiveData<List<Genre>> genreLiveDate) {

        Call<GenreResponse> genreHTTPRequest = genreIDService.getGenresNames();
        genreHTTPRequest.enqueue(new Callback<GenreResponse>() {

            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                GenreResponse genreResponse = response.body();

                if (genreResponse != null) {

                    ArrayList<Genre> genres = genreResponse.getGenres();
                    genreLiveDate.postValue(genres);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
