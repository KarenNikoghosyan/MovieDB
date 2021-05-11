package edu.karen.nikoghosyan.moviedb.ui.genre;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.AnimationAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.ComedyAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.CrimeAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.HorrorAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.ScienceFictionAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.TopRatedAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.UpcomingAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class GenreMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> topRatedLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> upcomingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> horrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> comedyLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> crimeLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> animationLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> scienceFictionLiveData = new MutableLiveData<>();

    private final MutableLiveData<Throwable> exception = new MutableLiveData<>();

    public GenreMovieViewModel(){

        TopRatedAPIManager topRatedAPIManager = new TopRatedAPIManager();
        topRatedAPIManager.getTopRated(topRatedLiveData, exception);

        UpcomingAPIManager upcomingAPIManager = new UpcomingAPIManager();
        upcomingAPIManager.getUpcoming(upcomingLiveData);

        HorrorAPIManager horrorAPIManager = new HorrorAPIManager();
        horrorAPIManager.getHorror(horrorLiveData);

        ComedyAPIManager comedyAPIManager = new ComedyAPIManager();
        comedyAPIManager.getComedy(comedyLiveData);

        CrimeAPIManager crimeAPIManager = new CrimeAPIManager();
        crimeAPIManager.getCrime(crimeLiveData);

        AnimationAPIManager animationAPIManager = new AnimationAPIManager();
        animationAPIManager.getAnimation(animationLiveData);

        ScienceFictionAPIManager scienceFictionAPIManager = new ScienceFictionAPIManager();
        scienceFictionAPIManager.getScienceFiction(scienceFictionLiveData);
    }

    public MutableLiveData<List<Movie>> getTopRatedLiveData() {
        return topRatedLiveData;
    }
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
    public MutableLiveData<List<Movie>> getScienceFictionLiveData() {
        return scienceFictionLiveData;
    }
}