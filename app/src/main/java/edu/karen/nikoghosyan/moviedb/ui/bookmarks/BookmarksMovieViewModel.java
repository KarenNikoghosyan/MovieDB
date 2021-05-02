package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.Constants;
import edu.karen.nikoghosyan.moviedb.models.api.bookmarks.BookmarksAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.bookmarks.adapters.BookmarksAdapter;

public class BookmarksMovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> bookmarkedMovies = new MutableLiveData<>();
    private final BookmarksAPIManager bookmarksAPIManager;

    public BookmarksMovieViewModel(){
        bookmarksAPIManager = new BookmarksAPIManager();
        bookmarksAPIManager.getMovies(bookmarkedMovies);
    }

    public MutableLiveData<List<Movie>> getBookmarkedMovies() {
        return bookmarkedMovies;
    }

    public void updateBookmarks(){
        bookmarksAPIManager.getMovies(bookmarkedMovies);
    }
}