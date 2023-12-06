// SavedWordDao.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_SavedWord;

@Dao
public interface IO_SavedWordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(IO_SavedWord savedWord);

    @Delete
    void delete(IO_SavedWord savedWord);

    @Query("SELECT * FROM saved_word_table")
    List<IO_SavedWord> getAllSavedWords();

    @Query("SELECT * FROM saved_word_table WHERE id = :savedWordId")
    IO_SavedWord getSavedWordById(int savedWordId);


}
