// IO_Word.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "word_table")
public class IO_Word implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id; // New field for ID

    @NonNull
    private String word;

    public IO_Word(@NonNull String word) {
        this.word = word;
    }

    public static final Creator<IO_Word> CREATOR = new Creator<IO_Word>() {
        @Override
        public IO_Word createFromParcel(Parcel in) {
            return new IO_Word(in);
        }

        @Override
        public IO_Word[] newArray(int size) {
            return new IO_Word[size];
        }
    };

    protected IO_Word(Parcel in) {
        word = in.readString();
        id = in.readLong(); // Read the new ID field from Parcel
    }

    // Getter and setter for the new ID field
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeLong(id); // Write the new ID field to Parcel
    }
}
