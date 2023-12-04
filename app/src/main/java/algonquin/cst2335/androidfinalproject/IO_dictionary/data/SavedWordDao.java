// SavedWordDao.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;

import java.util.List;

@Dao
public interface SavedWordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedWord savedWord);

    @Delete
    void delete(SavedWord savedWord);

    @Query("SELECT * FROM saved_word_table")
    List<SavedWord> getAllSavedWords();

    @Query("SELECT * FROM saved_word_table WHERE id = :savedWordId")
    SavedWord getSavedWordById(int savedWordId);


}
