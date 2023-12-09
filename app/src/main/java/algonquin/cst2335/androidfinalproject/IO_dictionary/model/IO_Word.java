package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a word in the IO dictionary.
 *
 * @Author Iuliia Obukhova
 * @Version 1.0
 */
@Entity(tableName = "word_table")
public class IO_Word implements Parcelable {

    /**
     * Parcelable.Creator to create IO_Word objects from a Parcel.
     */
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
    // New field for ID
    @PrimaryKey(autoGenerate = true)
    private long id;
    // The word itself
    @NonNull
    private String word;

    /**
     * Constructor to create an IO_Word object with a specified word.
     *
     * @param word The word to be associated with the IO_Word object.
     */
    public IO_Word(@NonNull String word) {
        this.word = word;
    }

    /**
     * Protected constructor to create IO_Word objects from a Parcel.
     */
    protected IO_Word(Parcel in) {
        word = in.readString();
        id = in.readLong(); // Read the new ID field from Parcel
    }

    /**
     * Getter for the new ID field.
     *
     * @return The ID of the IO_Word object.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the new ID field.
     *
     * @param id The ID to be set for the IO_Word object.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the word associated with the IO_Word object.
     *
     * @return The word.
     */
    @NonNull
    public String getWord() {
        return word;
    }

    /**
     * Parcelable method to describe the contents.
     *
     * @return An integer value representing the content description.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable method to write the object to a Parcel.
     *
     * @param dest  The Parcel to which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeLong(id); // Write the new ID field to Parcel
    }
}
