// Song.java
package algonquin.cst2335.androidfinalproject.hmsong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a song with details such as title, duration, album name, and album cover URL.
 */
public class Song implements Parcelable {

    private String title;
    private String duration;
    private String albumName;
    private String albumCoverUrl;

    // Default constructor
    public Song() {
    }

    protected Song(Parcel in) {
        // Add null check for the entire Parcel
        if (in != null) {
            // Check for null values before reading
            title = in.readString();
            duration = in.readString();
            albumName = in.readString();
            albumCoverUrl = in.readString();

            // Add null checks and assign default values if needed
            if (title == null) {
                title = "";
            }
            if (duration == null) {
                duration = "";
            }
            if (albumName == null) {
                albumName = "";
            }
            if (albumCoverUrl == null) {
                albumCoverUrl = "";
            }
        } else {
            // Initialize with default values if the Parcel is null
            title = "";
            duration = "";
            albumName = "";
            albumCoverUrl = "";
        }
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(albumName);
        dest.writeString(albumCoverUrl);
    }
}
