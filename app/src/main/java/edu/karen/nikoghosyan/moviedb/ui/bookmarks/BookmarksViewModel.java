package edu.karen.nikoghosyan.moviedb.ui.bookmarks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.karen.nikoghosyan.moviedb.models.api.bookmarks.BookmarksAPIManager;
import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;

public class BookmarksViewModel extends ViewModel {
    BookmarksAPIManager bookmarksAPIManager;
    private final MutableLiveData<List<Movie>> bookmarkedMovies = new MutableLiveData<>();
    private final MutableLiveData<Throwable> bookmarksException = new MutableLiveData<>();

    public BookmarksViewModel(){
        bookmarksAPIManager = new BookmarksAPIManager();
        bookmarksAPIManager.getMovieIDs(bookmarkedMovies, bookmarksException);
    }

    public MutableLiveData<List<Movie>> getBookmarkedMovies() {
        return bookmarkedMovies;
    }
    public void reloadData(){
        bookmarksAPIManager.getMovieIDs(bookmarkedMovies, bookmarksException);
    }
    public MutableLiveData<Throwable> getBookmarksException() {
        return bookmarksException;
    }
}
