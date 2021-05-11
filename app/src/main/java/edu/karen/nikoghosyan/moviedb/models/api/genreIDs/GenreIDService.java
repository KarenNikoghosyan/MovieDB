package edu.karen.nikoghosyan.moviedb.models.api.genreIDs;

import edu.karen.nikoghosyan.moviedb.models.movies.genre.GenreResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreIDService {
    @GET("genre/movie/list?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US")
    Call<GenreResponse> getGenresNames();
}
