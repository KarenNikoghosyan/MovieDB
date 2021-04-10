package edu.karen.nikoghosyan.moviedb.ui.information;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.genre.GenreAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;

public class InformationMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genresNames = new MutableLiveData<>();

    public InformationMovieViewModel(){

        GenreAPIManager manager = new GenreAPIManager();
        manager.getGenreNames(genresNames);
    }

    public MutableLiveData<List<Genre>> getGenresNames() {
        return genresNames;
    }
}