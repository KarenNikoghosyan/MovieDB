package edu.karen.nikoghosyan.moviedb.models.api.bookmarks;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookmarksMovieService {

    @GET("movie/{movie_id}?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US")
    Call<Movie> getMovies(@Path("movie_id") int movieID);
}
