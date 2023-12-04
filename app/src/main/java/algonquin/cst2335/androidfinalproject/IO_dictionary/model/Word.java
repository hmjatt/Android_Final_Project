package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "word_table")
public class Word implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    private List<String> definitions;  // Assuming definitions is a list of strings


    public Word(String word) {
        this.word = word;
        this.definitions = new ArrayList<>();  // Initialize the list here
    }

    protected Word(Parcel in) {
        word = in.readString();
        definitions = in.createStringArrayList();
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

    public String getWord() {
        return word;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void addDefinition(String definition) {
        definitions.add(definition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeStringList(definitions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
