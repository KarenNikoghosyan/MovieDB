package edu.karen.nikoghosyan.moviedb.ui.genre.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.genreapi.AnimationAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class AnimationViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> animationLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionAnimation = new MutableLiveData<>();

    public AnimationViewModel(){
        AnimationAPIManager animationAPIManager = new AnimationAPIManager();
        animationAPIManager.getAnimation(animationLiveData, exceptionAnimation);
    }

    public MutableLiveData<List<Movie>> getAnimationLiveData() {
        return animationLiveData;
    }
}
