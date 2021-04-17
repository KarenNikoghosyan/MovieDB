package edu.karen.nikoghosyan.moviedb.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.api.genre.GenreAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.search.SearchAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class SearchMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Genre>> genresNames = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> moviesSearching = new MutableLiveData<>();
    private SearchAPIManager searchManager;

    public SearchMovieViewModel() {

        GenreAPIManager genreManager = new GenreAPIManager();
        genreManager.getGenreNames(genresNames);

        searchManager = new SearchAPIManager();
        searchManager.getMoviesWithSearching(moviesSearching, Constants.MOVIE_SEARCH);
    }

    public MutableLiveData<List<Genre>> getGenresNames() {
        return genresNames;
    }
    public MutableLiveData<List<Movie>> getMoviesWithSearching() {
        return moviesSearching;
    }
    public void updateMovieWithSearching() {
        searchManager.getMoviesWithSearching(moviesSearching, Constants.MOVIE_SEARCH);
    }

}