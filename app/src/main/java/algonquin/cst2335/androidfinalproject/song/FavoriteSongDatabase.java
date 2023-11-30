package algonquin.cst2335.androidfinalproject.song;

// FavoriteSongDatabase.java
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteSong.class}, version = 1, exportSchema = false)
public abstract class FavoriteSongDatabase extends RoomDatabase {
    public abstract FavoriteSongDao favoriteSongDao();

    private static FavoriteSongDatabase instance;

    public static synchronized FavoriteSongDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteSongDatabase.class, "favorite_song_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}