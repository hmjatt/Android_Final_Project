package algonquin.cst2335.androidfinalproject.song.ui;

// SongApp.java


import android.app.Application;

import androidx.room.Room;

import algonquin.cst2335.androidfinalproject.song.data.database.FavoriteSongDatabase;

public class SongApp extends Application {

    // Room database instance
    public static FavoriteSongDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Room database
        database = Room.databaseBuilder(getApplicationContext(), FavoriteSongDatabase.class, "favorite_song_db")
                .allowMainThreadQueries() // For simplicity (not recommended for production)
                .build();
    }
}
