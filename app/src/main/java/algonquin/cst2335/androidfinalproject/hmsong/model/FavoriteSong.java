package algonquin.cst2335.androidfinalproject.hmsong.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteSong implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "TitleColumn")
    private String title;

    @ColumnInfo(name = "DurationColumn")
    private String duration;

    @ColumnInfo(name = "AlbumNameColumn")
    private String albumName;

    @ColumnInfo(name = "AlbumCoverUrlColumn")
    private String albumCoverUrl;

    // Constructors, getters, and setters as needed

    public FavoriteSong(String title, String duration, String albumName, String albumCoverUrl) {
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.albumCoverUrl = albumCoverUrl;
    }

    // Other methods as needed


    protected FavoriteSong(Parcel in) {
        id = in.readLong();
        title = in.readString();
        duration = in.readString();
        albumName = in.readString();
        albumCoverUrl = in.readString();
    }

    public static final Creator<FavoriteSong> CREATOR = new Creator<FavoriteSong>() {
        @Override
        public FavoriteSong createFromParcel(Parcel in) {
            return new FavoriteSong(in);
        }

        @Override
        public FavoriteSong[] newArray(int size) {
            return new FavoriteSong[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(albumName);
        dest.writeString(albumCoverUrl);
    }
}
