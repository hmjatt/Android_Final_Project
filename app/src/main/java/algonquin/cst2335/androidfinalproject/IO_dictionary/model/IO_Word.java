package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "word_table")
public class IO_Word implements Parcelable {

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
    @PrimaryKey
    @NonNull
    private String word;
    @TypeConverters(IO_DefinitionListConverter.class)
    private List<IO_Definition> definitions;

    public IO_Word(@NonNull String word) {
        this.word = word;
        this.definitions = new ArrayList<>();
    }

    protected IO_Word(Parcel in) {
        word = in.readString();
        // Use createTypedArrayList() with the Definition.CREATOR
        definitions = in.createTypedArrayList(IO_Definition.CREATOR);
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public List<IO_Definition> getDefinitions() {
        return definitions;
    }

    // Parcelable implementation here...

    public void setDefinitions(List<IO_Definition> definitions) {
        this.definitions = definitions;
    }

    public void addDefinition(IO_Definition definition) {
        definitions.add(definition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        // Use writeTypedList() with the definitions list
        dest.writeTypedList(definitions);
    }
}
