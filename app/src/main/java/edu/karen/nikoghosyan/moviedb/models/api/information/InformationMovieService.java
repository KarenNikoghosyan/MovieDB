package edu.karen.nikoghosyan.moviedb.models.api.information;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InformationMovieService {

    @GET("movie/{movie_id}/similar?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&page=1")
    Call<MovieResponse> getMoviesBySimilarID(@Path("movie_id") int movieID);

    @GET("movie/{movie_id}/recommendations?api_key=e40d9c03e1a4a736af381ffa2799b376&language=en-US&page=1")
    Call<MovieResponse> getRecommendations(@Path("movie_id") int movieID);
}
