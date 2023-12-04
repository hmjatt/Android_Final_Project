// WordDao.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;

@Dao
public interface WordDao {

    @Insert
    void insertWord(Word word);

    @Query("SELECT * FROM word_table")
    List<Word> getAllWords();

    // Add other queries as needed
}
