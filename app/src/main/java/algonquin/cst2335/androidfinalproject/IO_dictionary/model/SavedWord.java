package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "saved_word_table")
public class SavedWord {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String savedWord;
    private String savedWordDefinition;

    public SavedWord(String savedWord, String savedWordDefinition) {
        this.savedWord = savedWord;
        this.savedWordDefinition = savedWordDefinition;
    }

    public int getId() {
        return id;
    }

    public String getSavedWord() {
        return savedWord;
    }

    public String getSavedWordDefinition() {
        return savedWordDefinition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSavedWord(String savedWord) {
        this.savedWord = savedWord;
    }

    public void setSavedWordDefinition(String savedWordDefinition) {
        this.savedWordDefinition = savedWordDefinition;
    }
}
