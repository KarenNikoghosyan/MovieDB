package edu.karen.nikoghosyan.moviedb.models.api.home;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeMovieService {
    @GET("trending/movie/day?api_key=e40d9c03e1a4a736af381ffa2799b376")
    Call<MovieResponse> getTopTrending();

    @GET("movie/top_rated?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&page=1")
    Call<MovieResponse> getTopRated();

    @GET("movie/upcoming?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&page=1")
    Call<MovieResponse> getUpcoming();

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=27")
    Call<MovieResponse> getHorror();
}
