package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;

/**
 * Data Access Object (DAO) for interacting with the IO_Definition table in the Room database.
 * Author: Iuliia Obukhova
 * Version: 1.0
 */
@Dao
public interface IO_DefinitionDao {

    /**
     * Deletes a specific definition for a given word.
     *
     * @param wordId         The ID of the word.
     * @param definitionText The text of the definition to be deleted.
     * @return The number of rows affected (should be 1 if successful).
     */
    @Query("DELETE FROM definition_table WHERE wordId = :wordId AND definition = :definitionText")
    int deleteDefinitionForWord(long wordId, String definitionText);

    /**
     * Inserts or replaces a definition in the database.
     *
     * @param definition The definition to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDefinition(IO_Definition definition);

    /**
     * Retrieves all definitions for a given word ID.
     *
     * @param wordId The ID of the word.
     * @return A LiveData object containing a list of definitions for the given word.
     */
    @Query("SELECT * FROM definition_table WHERE wordId = :wordId")
    LiveData<List<IO_Definition>> getDefinitionsByWordId(long wordId);

    // Add other queries as needed
}
