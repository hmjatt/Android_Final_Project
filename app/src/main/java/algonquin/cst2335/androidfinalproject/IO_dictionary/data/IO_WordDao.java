// WordDao.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

@Dao
public interface IO_WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWord(IO_Word word);

    // 2. Delete Saved Definitions for a Specific Word
    @Query("DELETE FROM word_table WHERE id = :wordId")
    void deleteDefinitionsForWord(long wordId);

    // 3. Delete Word If All Definitions Are Deleted
    @Transaction
    @Query("DELETE FROM word_table WHERE id = :wordId AND (SELECT COUNT(*) FROM word_table WHERE id = :wordId) = 0")
    void deleteWordIfNoDefinitions(long wordId);


    // Custom query to check if the word with its part of speech already exists
    @Query("SELECT COUNT(*) FROM word_table WHERE word = :word AND partOfSpeech = :partOfSpeech")
    int countWordsByPartOfSpeech(String word, String partOfSpeech);

    @Query("SELECT * FROM word_table")
    LiveData<List<IO_Word>> getAllWords();

    // Add this query to get a word by its ID
    @Query("SELECT * FROM word_table WHERE id = :id")
    LiveData<IO_Word> getWordById(int id);

    @Query("DELETE FROM word_table WHERE id = :wordId")
    void deleteWordById(long wordId);

    @Query("SELECT * FROM word_table WHERE id = :wordId")
    IO_Word getWordByIdSync(int wordId);


    // Add other queries as needed
}
