package algonquin.cst2335.androidfinalproject.hmsong.ui;

import android.app.Application;

import androidx.room.Room;

import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;

public class SongApp extends Application {

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
