package algonquin.cst2335.androidfinalproject.song;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteSongDao {
    @Insert
    long saveFavoriteSong(FavoriteSong favoriteSong);

    @Query("SELECT * FROM favorite_songs")
    List<FavoriteSong> getAllFavoriteSongs();

    @Query("DELETE FROM favorite_songs WHERE id = :songId")
    void deleteFavoriteSong(long songId);
}