// io_dictionary.data.FavoriteWordDao
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.FavoriteWord;

@Dao
public interface FavoriteWordDao {
    @Insert
    void insert(FavoriteWord favoriteWord);

    @Query("SELECT * FROM favorite_words")
    List<FavoriteWord> getAllFavoriteWords();

    // Add methods for other database operations
}
