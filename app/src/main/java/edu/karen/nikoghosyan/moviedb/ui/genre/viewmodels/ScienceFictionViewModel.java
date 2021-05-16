package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.ScienceFictionAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class ScienceFictionViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> scienceFictionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionScienceFiction = new MutableLiveData<>();

    public ScienceFictionViewModel(){
        ScienceFictionAPIManager scienceFictionAPIManager = new ScienceFictionAPIManager();
        scienceFictionAPIManager.getScienceFiction(scienceFictionLiveData, exceptionScienceFiction);
    }
    public MutableLiveData<List<Movie>> getScienceFictionLiveData() {
        return scienceFictionLiveData;
    }
}
