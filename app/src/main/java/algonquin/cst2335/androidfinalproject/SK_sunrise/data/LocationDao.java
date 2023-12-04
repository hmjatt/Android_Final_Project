package algonquin.cst2335.androidfinalproject.SK_sunrise.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<Location> getAllLocations();


    @Insert
    void insertLocation(Location location);

    @Delete
    void deleteLocation(Location location);

}