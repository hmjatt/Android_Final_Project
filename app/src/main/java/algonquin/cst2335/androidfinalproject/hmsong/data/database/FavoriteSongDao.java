package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

@Dao
public interface FavoriteSongDao {

    @Insert
    long saveFavoriteSong(FavoriteSong favoriteSong);

    @Query("SELECT * FROM favorite_songs")
    List<FavoriteSong> getAllFavoriteSongs();

    // Add more queries as needed
}
