// Album.java
package algonquin.cst2335.androidfinalproject.hmsong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents an Album with an ID, title, and cover URL.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class Album implements Parcelable {

    private String id;
    private String title;
    private String coverUrl;

    /**
     * Constructs an Album with the specified ID, title, and cover URL.
     *
     * @param id       The ID of the album.
     * @param title    The title of the album.
     * @param coverUrl The URL of the album cover.
     */
    public Album(String id, String title, String coverUrl) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
    }

    protected Album(Parcel in) {
        id = in.readString();
        title = in.readString();
        coverUrl = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    /**
     * Gets the ID of the album.
     *
     * @return The ID of the album.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the title of the album.
     *
     * @return The title of the album.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the URL of the album cover.
     *
     * @return The URL of the album cover.
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(coverUrl);
    }
}
