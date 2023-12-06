// WordDao.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

@Dao
public interface IO_WordDao {

    @Insert
    void insertWord(IO_Word word);

    @Query("SELECT * FROM word_table")
    LiveData<List<IO_Word>> getAllWords(); // Change the return type to LiveData


    // Add other queries as needed
}
