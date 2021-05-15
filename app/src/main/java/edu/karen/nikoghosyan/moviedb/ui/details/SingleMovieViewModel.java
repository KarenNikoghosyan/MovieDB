package edu.karen.nikoghosyan.moviedb.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.details.SingleMovieAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class SingleMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> singleBookmarkedMovie = new MutableLiveData<>();

    public SingleMovieViewModel() {
        SingleMovieAPIManager singleMovieAPIManager = new SingleMovieAPIManager();
        singleMovieAPIManager.getSingleMovie(singleBookmarkedMovie);
    }
    public MutableLiveData<List<Movie>> getSingleBookmarkedMovie() {
        return singleBookmarkedMovie;
    }

}
