package edu.karen.nikoghosyan.moviedb.models.api.genre;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreService {
    @GET("movie/top_rated?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US")
    Call<MovieResponse> getTopRated(@Query("page") int page);

    @GET("movie/upcoming?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US")
    Call<MovieResponse> getUpcoming(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=27")
    Call<MovieResponse> getHorror(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=35")
    Call<MovieResponse> getComedy(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=80")
    Call<MovieResponse> getCrime(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=16")
    Call<MovieResponse> getAnimation(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=878")
    Call<MovieResponse> getScienceFiction(@Query("page") int page);

    @GET("discover/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&with_genres=28")
    Call<MovieResponse> getAction(@Query("page") int page);
}

