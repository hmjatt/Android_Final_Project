package algonquin.cst2335.androidfinalproject.SK_sunrise.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {

    public abstract LocationDao locationDao();

    // Singleton pattern for the database
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "sunrise_sunset_database"
            ).build();
        }
        return instance;
    }
}
