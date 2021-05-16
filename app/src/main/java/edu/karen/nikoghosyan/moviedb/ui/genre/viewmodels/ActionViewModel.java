package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.ActionAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class ActionViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> actionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionAction = new MutableLiveData<>();

    public ActionViewModel() {
        ActionAPIManager actionAPIManager = new ActionAPIManager();
        actionAPIManager.getAction(actionLiveData, exceptionAction);
    }

    public MutableLiveData<List<Movie>> getActionLiveData() {
        return actionLiveData;
    }
    public MutableLiveData<Throwable> getExceptionAction() {
        return exceptionAction;
    }
}
