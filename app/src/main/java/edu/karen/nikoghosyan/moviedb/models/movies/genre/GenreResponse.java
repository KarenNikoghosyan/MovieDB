package edu.karen.nikoghosyan.moviedb.models.movies.genre;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenreResponse {

    @SerializedName("genres")
    ArrayList<Genre> genres;

    public GenreResponse(){}

    public ArrayList<Genre> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "GenreResponse{" +
                "genres=" + genres +
                '}';
    }
}
