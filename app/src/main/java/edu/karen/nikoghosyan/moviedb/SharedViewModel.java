package edu.karen.nikoghosyan.moviedb;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.bookmarks.BookmarksAPIManager;
import edu.karen.nikoghosyan.moviedb.models.api.details.DetailsAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.genre.Genre;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> bookmarkedMovies = new MutableLiveData<>();
    private final BookmarksAPIManager bookmarksAPIManager;

    private final MutableLiveData<List<Genre>> genresNames = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> similarMoviesByID = new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> moviesRecommendations = new MutableLiveData<>();

    public SharedViewModel(){

        bookmarksAPIManager = new BookmarksAPIManager();
        bookmarksAPIManager.getMovies(bookmarkedMovies);

        DetailsAPIManager manager = new DetailsAPIManager();
        manager.getMoviesBySimilarID(similarMoviesByID, Constants.MOVIE_ID);
        manager.getRecommendations(moviesRecommendations, Constants.MOVIE_ID);
    }

    public MutableLiveData<List<Movie>> getBookmarkedMovies() {
        return bookmarkedMovies;
    }
    public void updateBookmarks(){
        bookmarksAPIManager.getMovies(bookmarkedMovies);
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
}
