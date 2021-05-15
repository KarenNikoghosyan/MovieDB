package edu.karen.nikoghosyan.moviedb.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.api.details.DetailsAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.genreIDs.GenreIDAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class DetailsMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genresNames = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> similarMoviesByID = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> moviesRecommendations = new MutableLiveData<>();
    private final MutableLiveData<Throwable> detailsException = new MutableLiveData<>();

    public DetailsMovieViewModel(){

        GenreIDAPIManager genreManager = new GenreIDAPIManager();
        genreManager.getGenreNames(genresNames);

        DetailsAPIManager manager = new DetailsAPIManager();
        manager.getMoviesBySimilarID(similarMoviesByID, detailsException, Constants.MOVIE_ID);
        manager.getRecommendations(moviesRecommendations, Constants.MOVIE_ID);

    }

    public MutableLiveData<List<Genre>> getGenresNames() {
        return genresNames;
    }
    public MutableLiveData<List<Movie>> getSimilarMoviesByID() {
        return similarMoviesByID;
    }
    public MutableLiveData<List<Movie>> getMoviesRecommendations() {
        return moviesRecommendations;
    }
    public MutableLiveData<Throwable> getDetailsException() {
        return detailsException;
    }
}
