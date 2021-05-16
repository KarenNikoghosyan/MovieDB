package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.ComedyAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class ComedyViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> comedyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionComedy = new MutableLiveData<>();

    public ComedyViewModel(){
        ComedyAPIManager comedyAPIManager = new ComedyAPIManager();
        comedyAPIManager.getComedy(comedyLiveData, exceptionComedy);
    }

    public MutableLiveData<List<Movie>> getComedyLiveData() {
        return comedyLiveData;
    }
}
