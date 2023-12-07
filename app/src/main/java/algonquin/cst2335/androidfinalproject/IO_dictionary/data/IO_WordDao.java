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
    long insertWord(IO_Word word);


    @Query("DELETE FROM word_table WHERE id = :wordId AND (SELECT COUNT(*) FROM word_table WHERE id = :wordId) = 0")
    void deleteWordIfNoDefinitions(long wordId);

    @Query("SELECT COUNT(*) FROM word_table WHERE word = :word AND partOfSpeech = :partOfSpeech")
    int countWordsByPartOfSpeech(String word, String partOfSpeech);

    @Query("SELECT * FROM word_table")
    LiveData<List<IO_Word>> getAllWords();

    @Query("SELECT * FROM word_table WHERE id = :id")
    LiveData<IO_Word> getWordById(int id);

    @Query("DELETE FROM word_table WHERE id = :wordId")
    void deleteWordById(long wordId);

    @Query("SELECT * FROM word_table WHERE id = :wordId")
    IO_Word getWordByIdSync(int wordId);

    @Query("DELETE FROM definition_table WHERE wordId = :wordId AND definition = :definitionText")
    void deleteDefinitionForWord(long wordId, String definitionText);


    @Query("DELETE FROM word_table WHERE id = :wordId")
    void deleteDefinitionsForWord(long wordId);


    // Add other queries as needed
}
