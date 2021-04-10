package edu.karen.nikoghosyan.moviedb.models.api.genre;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.GenreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenreAPIManager {

    private final Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final GenreService genreService = retrofit.create(GenreService.class);

    public void getGenreNames(MutableLiveData<List<Genre>> genreLiveDate) {

        Call<GenreResponse> genreHTTPRequest = genreService.getGenresNames();
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
