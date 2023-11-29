// Song.java
package algonquin.cst2335.androidfinalproject.song;

public class  Song {
    private String title;
    private String duration;
    private String albumName;

    private String albumCoverUrl;

    // Constructors, getters, setters

    // Additional methods to set data for a song
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    // Additional methods to get data from a song
    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumCoverUrl() {
        return albumCoverUrl;
    }

    public void setAlbumCoverUrl(String albumCoverUrl) {
        this.albumCoverUrl = albumCoverUrl;
    }
}
