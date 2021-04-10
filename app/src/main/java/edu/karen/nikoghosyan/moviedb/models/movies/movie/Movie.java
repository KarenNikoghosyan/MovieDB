package edu.karen.nikoghosyan.moviedb.models.movies.movie;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Movie {

    @SerializedName("backdrop_path")
    private String backdropImage;

    @SerializedName("genre_ids")
    private int[] genres;

    @SerializedName("id")
    private int movieID;

    @SerializedName("original_language")
    private String language;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String movieImage;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double rating;

    private final String url = "https://image.tmdb.org/t/p/w500";

    public Movie(){}

    public String getBackdropImage() {
        return backdropImage;
    }
    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }
    public int[] getGenres() {
        return genres;
    }
    public void setGenres(int[] genres) {
        this.genres = genres;
    }
    public int getMovieID() {
        return movieID;
    }
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getMovieImage() {
        return movieImage;
    }
    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getImageURL(){
        return url + getMovieImage();
    }
    public String getBackdropImageURL(){
        return url + getBackdropImage();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "backdropImage='" + backdropImage + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", movieID=" + movieID +
                ", language='" + language + '\'' +
                ", overview='" + overview + '\'' +
                ", movieImage='" + movieImage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                '}';
    }
}
