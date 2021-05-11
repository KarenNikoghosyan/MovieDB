package edu.karen.nikoghosyan.moviedb.models.api.search;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchMovieService {

    @GET("search/movie?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&include_adult=false")
    Call<MovieResponse> getMoviesWithSearching(@Query("query") String query, @Query("page") int page);
}
