package algonquin.cst2335.androidfinalproject.song;


import android.app.Application;
import androidx.room.Room;

public class SongApp extends Application {

    // Database instance
    public static FavoriteSongDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Room database
        database = Room.databaseBuilder(getApplicationContext(),
                        FavoriteSongDatabase.class, "favorite_song_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
