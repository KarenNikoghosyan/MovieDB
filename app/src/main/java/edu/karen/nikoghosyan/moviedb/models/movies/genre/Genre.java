package edu.karen.nikoghosyan.moviedb.models.movies.genre;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    private int genreId;
    @SerializedName("name")
    private String genreName;

    public Genre(){}

    public int getGenreId() {
        return genreId;
    }
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
