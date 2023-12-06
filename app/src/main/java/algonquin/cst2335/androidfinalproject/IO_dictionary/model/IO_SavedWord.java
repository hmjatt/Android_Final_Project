package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_word_table")
public class IO_SavedWord {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String savedWord;
    private String savedWordDefinition;

    public IO_SavedWord(String savedWord, String savedWordDefinition) {
        this.savedWord = savedWord;
        this.savedWordDefinition = savedWordDefinition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSavedWord() {
        return savedWord;
    }

    public void setSavedWord(String savedWord) {
        this.savedWord = savedWord;
    }

    public String getSavedWordDefinition() {
        return savedWordDefinition;
    }

    public void setSavedWordDefinition(String savedWordDefinition) {
        this.savedWordDefinition = savedWordDefinition;
    }
}
