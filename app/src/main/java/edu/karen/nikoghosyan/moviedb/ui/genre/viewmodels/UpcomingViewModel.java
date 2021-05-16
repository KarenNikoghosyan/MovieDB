package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.UpcomingAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class UpcomingViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> upcomingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionUpcoming = new MutableLiveData<>();

    public UpcomingViewModel(){
        UpcomingAPIManager upcomingAPIManager = new UpcomingAPIManager();
        upcomingAPIManager.getUpcoming(upcomingLiveData, exceptionUpcoming);
    }

    public MutableLiveData<List<Movie>> getUpcomingLiveData() {
        return upcomingLiveData;
    }
    public MutableLiveData<Throwable> getExceptionUpcoming() {
        return exceptionUpcoming;
    }
}
