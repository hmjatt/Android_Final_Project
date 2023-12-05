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
public class Word implements Parcelable {

    @PrimaryKey
    @NonNull
    private String word;

    @TypeConverters(DefinitionListConverter.class)
    private List<Definition> definitions;

    public Word(@NonNull String word) {
        this.word = word;
        this.definitions = new ArrayList<>();
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public void addDefinition(Definition definition) {
        definitions.add(definition);
    }

    // Parcelable implementation here...

    protected Word(Parcel in) {
        word = in.readString();
        // Use createTypedArrayList() with the Definition.CREATOR
        definitions = in.createTypedArrayList(Definition.CREATOR);
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

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
