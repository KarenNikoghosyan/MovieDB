package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.HorrorAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class HorrorViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> horrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionHorror = new MutableLiveData<>();

    public HorrorViewModel(){
        HorrorAPIManager horrorAPIManager = new HorrorAPIManager();
        horrorAPIManager.getHorror(horrorLiveData, exceptionHorror);
    }

    public MutableLiveData<List<Movie>> getHorrorLiveData() {
        return horrorLiveData;
    }
}
