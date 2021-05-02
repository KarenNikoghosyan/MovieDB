package edu.karen.nikoghosyan.moviedb.models.movies.genre;

public class GenreObject {

    private int id;
    private String name;

    public GenreObject(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
