// IO_Definition.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "definition_table",
        foreignKeys = @ForeignKey(
                entity = IO_Word.class,
                parentColumns = "id",
                childColumns = "wordId",
                onDelete = ForeignKey.CASCADE
        )
)
public class IO_Definition implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(index = true)
    private long wordId; // Reference to the parent IO_Word
    private String definition;


    public IO_Definition(String definition) {
        this.definition = definition;
    }


    public IO_Definition(Parcel in) {
        definition = in.readString();
        wordId = in.readLong();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public long getWordId() {
        return wordId;
    }

    public static final Creator<IO_Definition> CREATOR = new Creator<IO_Definition>() {
        @Override
        public IO_Definition createFromParcel(Parcel in) {
            return new IO_Definition(in);
        }

        @Override
        public IO_Definition[] newArray(int size) {
            return new IO_Definition[size];
        }
    };


    public String getDefinition() {
        return definition;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(definition);
        dest.writeLong(wordId);
    }
}
