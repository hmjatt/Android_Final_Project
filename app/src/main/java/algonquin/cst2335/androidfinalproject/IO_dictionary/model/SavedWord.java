// SavedWord.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_word_table")
public class SavedWord {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String savedWord;

    public SavedWord(String savedWord) {
        this.savedWord = savedWord;
    }

    public int getId() {
        return id;
    }

    public String getSavedWord() {
        return savedWord;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSavedWord(String savedWord) {
        this.savedWord = savedWord;
    }
}
