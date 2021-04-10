package edu.karen.nikoghosyan.moviedb.models.api;

import edu.karen.nikoghosyan.moviedb.models.movies.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeMovieService {
    @GET("trending/movie/day?api_key=e40d9c03e1a4a736af381ffa2799b376")
    Call<MovieResponse> getTopTrending();

    @GET("movie/top_rated?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&page=1")
    Call<MovieResponse> getTopRated();
}
