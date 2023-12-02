package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

@Dao
public interface FavoriteSongDao {

    @Insert
    long saveFavoriteSong(FavoriteSong favoriteSong);

    @Query("SELECT * FROM FavoriteSong")
    List<FavoriteSong> getFavoriteSongs();

    @Query("SELECT * FROM FavoriteSong WHERE TitleColumn = :title AND DurationColumn = :duration AND AlbumNameColumn = :albumName LIMIT 1")
    FavoriteSong getFavoriteSongByDetails(String title, String duration, String albumName);

    @Delete
    void deleteFavoriteSong(FavoriteSong favoriteSong);

    // Additional methods as needed
}
