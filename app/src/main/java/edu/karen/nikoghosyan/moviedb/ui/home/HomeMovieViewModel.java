package edu.karen.nikoghosyan.moviedb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.HomeAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.Movie;

public class HomeMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> topTrendingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> topRatedLiveData = new MutableLiveData<>();

    public HomeMovieViewModel(){

        HomeAPIManager manager = new HomeAPIManager();
        manager.getTopTrending(topTrendingLiveData);
        manager.getTopRated(topRatedLiveData);

    }

    public LiveData<List<Movie>> getTopTrendingLiveData(){
        return topTrendingLiveData;
    }
    public LiveData<List<Movie>> getTopRatedLiveData() { return topRatedLiveData; }
}