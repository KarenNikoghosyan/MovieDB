package edu.karen.nikoghosyan.moviedb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.home.HomeAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class HomeMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> topTrendingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> topRatedLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> upcomingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> horrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> comedyLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> crimeLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> animationLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> scienceFictionLiveData = new MutableLiveData<>();

    public HomeMovieViewModel(){

        HomeAPIManager manager = new HomeAPIManager();
        manager.getTopTrending(topTrendingLiveData);
        manager.getTopRated(topRatedLiveData);
        manager.getUpcoming(upcomingLiveData);
        manager.getHorror(horrorLiveData);
        manager.getComedy(comedyLiveData);
        manager.getCrime(crimeLiveData);
        manager.getAnimation(animationLiveData);
        manager.getScienceFiction(scienceFictionLiveData);
    }

    public LiveData<List<Movie>> getTopTrendingLiveData(){
        return topTrendingLiveData;
    }
    public LiveData<List<Movie>> getTopRatedLiveData() { return topRatedLiveData; }
    public MutableLiveData<List<Movie>> getUpcomingLiveData() {
        return upcomingLiveData;
    }
    public MutableLiveData<List<Movie>> getHorrorLiveData() {
        return horrorLiveData;
    }
    public MutableLiveData<List<Movie>> getComedyLiveData() {
        return comedyLiveData;
    }
    public MutableLiveData<List<Movie>> getCrimeLiveData() {
        return crimeLiveData;
    }
    public MutableLiveData<List<Movie>> getAnimationLiveData() {
        return animationLiveData;
    }
    public MutableLiveData<List<Movie>> getScienceFictionLiveData() {return scienceFictionLiveData;}
}