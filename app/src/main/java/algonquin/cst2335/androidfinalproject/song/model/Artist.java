package algonquin.cst2335.androidfinalproject.song.model;

import java.util.List;

import algonquin.cst2335.androidfinalproject.song.model.Album;

public class Artist {

    private String id;
    private List<Album> albums;

    // Constructors, getters, and setters

    public Artist(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
