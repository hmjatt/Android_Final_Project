// WordDao.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

@Dao
public interface IO_WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWord(IO_Word word);

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

//    @Query("SELECT * FROM word_table WHERE word = :word AND partOfSpeech = :partOfSpeech LIMIT 1")
//    IO_Word getWordByWordAndSpeech(String word, String partOfSpeech);


    // Add other queries as needed
}
