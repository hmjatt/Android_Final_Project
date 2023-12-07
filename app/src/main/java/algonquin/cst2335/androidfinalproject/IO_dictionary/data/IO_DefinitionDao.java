// IO_DefinitionDao.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;

@Dao
public interface IO_DefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDefinition(IO_Definition definition);

    @Query("DELETE FROM definition_table WHERE wordId = :wordId")
    void deleteDefinitionsForWord(long wordId);

    @Query("SELECT * FROM definition_table WHERE wordId = :wordId")
    LiveData<List<IO_Definition>> getDefinitionsByWordId(long wordId);

    // Add other queries as needed
}