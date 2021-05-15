package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.bookmarks.BookmarksAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class BookmarksViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> bookmarkedMovies = new MutableLiveData<>();
    private final MutableLiveData<Throwable> bookmarksException = new MutableLiveData<>();

    public BookmarksViewModel(){
        BookmarksAPIManager bookmarksAPIManager = new BookmarksAPIManager();
        bookmarksAPIManager.getMovies(bookmarkedMovies, bookmarksException);
    }

    public MutableLiveData<List<Movie>> getBookmarkedMovies() {
        return bookmarkedMovies;
    }
    //TODO:
    public MutableLiveData<Throwable> getBookmarksException() {
        return bookmarksException;
    }
}
