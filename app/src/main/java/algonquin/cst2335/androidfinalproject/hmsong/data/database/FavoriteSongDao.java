// FavoriteSongDao.java
package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

/**
 * Data Access Object (DAO) for interacting with the FavoriteSong table in the Room Database.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
@Dao
public interface FavoriteSongDao {

    /**
     * Insert a new FavoriteSong into the database.
     *
     * @param favoriteSong The FavoriteSong to be inserted.
     * @return The ID of the inserted FavoriteSong.
     */
    @Insert
    long saveFavoriteSong(FavoriteSong favoriteSong);

    /**
     * Retrieve all FavoriteSongs from the database.
     *
     * @return A list of all FavoriteSongs.
     */
    @Query("SELECT * FROM FavoriteSong")
    List<FavoriteSong> getFavoriteSongs();

    /**
     * Retrieve a FavoriteSong from the database based on its title, duration, and album name.
     *
     * @param title     The title of the FavoriteSong.
     * @param duration  The duration of the FavoriteSong.
     * @param albumName The album name of the FavoriteSong.
     * @return The matching FavoriteSong, or null if not found.
     */
    @Query("SELECT * FROM FavoriteSong WHERE TitleColumn = :title AND DurationColumn = :duration AND AlbumNameColumn = :albumName LIMIT 1")
    FavoriteSong getFavoriteSongByDetails(String title, String duration, String albumName);

    /**
     * Delete a FavoriteSong from the database.
     *
     * @param favoriteSong The FavoriteSong to be deleted.
     */
    @Delete
    void deleteFavoriteSong(FavoriteSong favoriteSong);

    // Additional methods as needed
}
