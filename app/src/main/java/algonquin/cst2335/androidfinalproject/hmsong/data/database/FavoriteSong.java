package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_songs")
public class FavoriteSong {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String duration;
    private String albumName;
    private String albumCoverUrl;

    // Constructors, getters, and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumCoverUrl() {
        return albumCoverUrl;
    }

    public void setAlbumCoverUrl(String albumCoverUrl) {
        this.albumCoverUrl = albumCoverUrl;
    }
}
