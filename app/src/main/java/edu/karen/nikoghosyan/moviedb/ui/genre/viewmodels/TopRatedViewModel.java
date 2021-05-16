package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.TopRatedAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class TopRatedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> topRatedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionTopRated = new MutableLiveData<>();

    public TopRatedViewModel(){

        TopRatedAPIManager topRatedAPIManager = new TopRatedAPIManager();
        topRatedAPIManager.getTopRated(topRatedLiveData, exceptionTopRated);
    }

    public MutableLiveData<List<Movie>> getTopRatedLiveData() {
        return topRatedLiveData;
    }
    public MutableLiveData<Throwable> getExceptionTopRated() {
        return exceptionTopRated;
    }
}