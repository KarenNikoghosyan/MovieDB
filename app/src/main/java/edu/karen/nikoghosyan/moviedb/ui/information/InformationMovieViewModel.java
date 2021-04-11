package edu.karen.nikoghosyan.moviedb.ui.information;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.api.genre.GenreAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.information.InformationAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class InformationMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genresNames = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> similarMoviesByID = new MutableLiveData<>();

    public InformationMovieViewModel(){

        GenreAPIManager genreManager = new GenreAPIManager();
        genreManager.getGenreNames(genresNames);

        InformationAPIManager informationManager = new InformationAPIManager();
        informationManager.getMoviesBySimilarID(similarMoviesByID, Constants.MOVIE_ID);
    }

    public MutableLiveData<List<Genre>> getGenresNames() {
        return genresNames;
    }

    public MutableLiveData<List<Movie>> getSimilarMoviesByID() {
        return similarMoviesByID;
    }
}