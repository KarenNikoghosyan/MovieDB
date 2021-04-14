package edu.karen.nikoghosyan.moviedb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class Constants {
    public static final String MOVIE_TITLE = "movieTitle";
    public static final String MOVIE_RATING = "movieRating";
    public static final String MOVIE_BACKDROP_URL = "movieBackdropURL";
    public static final String MOVIE_GENRE_IDS = "movieGenreIDs";
    public static final String MOVIE_RELEASE_DATE = "movieReleaseDate";
    public static final String MOVIE_OVERVIEW = "movieOverview";
    public static final String MOVIE_IMAGE_URL = "movieImageURL";
    public static final String MOVIE_Language = "movieLanguage";

    public static int MOVIE_ID = 0;

    public static void getBundle(Fragment fragment, Movie movie){
        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE, movie.getTitle());
        args.putDouble(Constants.MOVIE_RATING, movie.getRating());
        args.putString(Constants.MOVIE_BACKDROP_URL, movie.getBackdropImageURL());
        args.putIntArray(Constants.MOVIE_GENRE_IDS, movie.getGenres());
        args.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        args.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());
        Constants.MOVIE_ID = movie.getMovieID();
        args.putString(Constants.MOVIE_IMAGE_URL, movie.getImageURL());
        args.putString(Constants.MOVIE_Language, movie.getLanguage());
        fragment.setArguments(args);
    }
}
