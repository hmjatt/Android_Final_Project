package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

/**
 * Data Access Object (DAO) for IO_Word entities.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
@Dao
public interface IO_WordDao {

    /**
     * Inserts a new word into the database.
     *
     * @param word The word to be inserted.
     * @return The ID of the inserted word.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertWord(IO_Word word);

    /**
     * Retrieves a LiveData list of all words from the database.
     *
     * @return A LiveData list of IO_Word entities.
     */
    @Query("SELECT * FROM word_table")
    LiveData<List<IO_Word>> getAllWords();

    /**
     * Retrieves a specific word by its ID from the database.
     *
     * @param wordId The ID of the word to retrieve.
     * @return The IO_Word entity with the specified ID.
     */
    @Query("SELECT * FROM word_table WHERE id = :wordId")
    IO_Word getWordByIdSync(int wordId);

    /**
     * Retrieves a specific word by its word from the database.
     *
     * @param word The word to retrieve.
     * @return The IO_Word entity with the specified word.
     */
    @Query("SELECT * FROM word_table WHERE word = :word")
    IO_Word getWordByWordSync(String word);

    /**
     * Retrieves a LiveData list of definitions associated with a specific word ID.
     *
     * @param wordId The ID of the word for which to retrieve definitions.
     * @return A LiveData list of IO_Definition entities.
     */
    @Query("SELECT * FROM definition_table WHERE wordId = :wordId")
    LiveData<List<IO_Definition>> getDefinitionsByWordId(long wordId);

    // Add other queries as needed
}
