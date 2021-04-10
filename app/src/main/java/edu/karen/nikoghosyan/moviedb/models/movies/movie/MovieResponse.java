package edu.karen.nikoghosyan.moviedb.models.movies.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    ArrayList<Movie> movies;

    public MovieResponse(){}

    public ArrayList<Movie> getMovies() {
        return movies;
    }
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movies=" + movies +
                '}';
    }
}
