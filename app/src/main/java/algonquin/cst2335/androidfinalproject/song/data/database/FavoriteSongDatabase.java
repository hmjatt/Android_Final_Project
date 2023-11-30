package algonquin.cst2335.androidfinalproject.song.data.database;

// FavoriteSongDatabase.java

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.song.model.FavoriteSong;

@Database(entities = {FavoriteSong.class}, version = 1, exportSchema = false)
public abstract class FavoriteSongDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favorite_songs_database";
    private static volatile FavoriteSongDatabase instance;

    public abstract FavoriteSongDao favoriteSongDao();

    public static synchronized FavoriteSongDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FavoriteSongDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }
}
