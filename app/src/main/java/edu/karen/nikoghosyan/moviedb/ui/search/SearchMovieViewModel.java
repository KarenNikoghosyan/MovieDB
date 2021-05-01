package edu.karen.nikoghosyan.moviedb.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.api.search.SearchAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class  SearchMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> moviesSearching = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exception = new MutableLiveData<>();
    private final SearchAPIManager searchManager;

    public SearchMovieViewModel() {

        searchManager = new SearchAPIManager();
        searchManager.getMoviesWithSearching(moviesSearching, exception, Constants.MOVIE_SEARCH);
    }

    public MutableLiveData<List<Movie>> getMoviesWithSearching() {
        return moviesSearching;
    }
    public MutableLiveData<Throwable> getException() {
        return exception;
    }

    public void updateMovieWithSearching() {
        searchManager.getMoviesWithSearching(moviesSearching, exception ,Constants.MOVIE_SEARCH);
    }
}