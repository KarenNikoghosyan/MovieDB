package edu.karen.nikoghosyan.moviedb.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.details.SingleMovieAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class SingleMovieViewModel extends ViewModel {

    private SingleMovieAPIManager singleMovieAPIManager;
    private final MutableLiveData<List<Movie>> singleBookmarkedMovie = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionSingleMovie = new MutableLiveData<>();

    public SingleMovieViewModel() {
        singleMovieAPIManager = new SingleMovieAPIManager();
        singleMovieAPIManager.getSingleMovie(singleBookmarkedMovie, exceptionSingleMovie);
    }

    public MutableLiveData<List<Movie>> getSingleBookmarkedMovie() {
        return singleBookmarkedMovie;
    }
    public MutableLiveData<Throwable> getExceptionSingleMovie() {
        return exceptionSingleMovie;
    }
}
