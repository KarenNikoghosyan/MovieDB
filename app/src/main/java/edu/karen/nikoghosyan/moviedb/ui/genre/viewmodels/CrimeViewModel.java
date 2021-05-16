package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.CrimeAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class CrimeViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> crimeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionCrime = new MutableLiveData<>();

    public CrimeViewModel(){
        CrimeAPIManager crimeAPIManager = new CrimeAPIManager();
        crimeAPIManager.getCrime(crimeLiveData, exceptionCrime);
    }

    public MutableLiveData<List<Movie>> getCrimeLiveData() {
        return crimeLiveData;
    }
    public MutableLiveData<Throwable> getExceptionCrime() {
        return exceptionCrime;
    }
}
