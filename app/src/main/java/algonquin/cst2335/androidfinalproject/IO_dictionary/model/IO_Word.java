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

    private String partOfSpeech;
    // Modify to store both the definition and part of speech
    @TypeConverters(IO_DefinitionListConverter.class)
    private List<IO_Definition> wordDetailsList;

    public IO_Word(@NonNull String word) {
        this.word = word;
        this.wordDetailsList = new ArrayList<>();
        this.partOfSpeech = getPartOfSpeech();
    }

    protected IO_Word(Parcel in) {
        word = in.readString();
        // Use createTypedArrayList() with the IO_Definition.CREATOR
        wordDetailsList = in.createTypedArrayList(IO_Definition.CREATOR);
        partOfSpeech = in.readString();
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<IO_Definition> getWordDetailsList() {
        return wordDetailsList;
    }

    public void setWordDetailsList(List<IO_Definition> wordDetailsList) {
        this.wordDetailsList = wordDetailsList;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public List<IO_Definition> getDefinitions() {
        return wordDetailsList;
    }

    public void setDefinitions(List<IO_Definition> wordDetailsList) {
        this.wordDetailsList = wordDetailsList;
    }

    public void addDefinition(IO_Definition wordDetails) {
        wordDetailsList.add(wordDetails);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        // Use writeTypedList() with the wordDetailsList
        dest.writeTypedList(wordDetailsList);
        dest.writeString(partOfSpeech);
    }
}
