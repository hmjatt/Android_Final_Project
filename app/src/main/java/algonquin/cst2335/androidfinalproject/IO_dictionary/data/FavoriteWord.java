// io_dictionary.data.FavoriteWord
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_words")
public class FavoriteWord {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;
    private String definition;

    public FavoriteWord(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
