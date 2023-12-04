package algonquin.cst2335.androidfinalproject.hmsong.ui;

import android.app.Application;

import androidx.room.Room;

import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;

/**
 * Application class for the Song App, responsible for initializing the Room database.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class SongApp extends Application {

    /**
     * The Room database instance for storing favorite songs.
     */
    public static FavoriteSongDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Room database
        database = Room.databaseBuilder(getApplicationContext(), FavoriteSongDatabase.class, "favorite_song_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
