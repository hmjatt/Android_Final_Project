// FavoriteSongDatabase.java
package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

@Database(entities = {FavoriteSong.class}, version = 1, exportSchema = false)
public abstract class FavoriteSongDatabase extends RoomDatabase {

    public abstract FavoriteSongDao favoriteSongDao();

    private static FavoriteSongDatabase instance;

    // Create a singleton instance of the database
    public static synchronized FavoriteSongDatabase getInstance(android.content.Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteSongDatabase.class, "favorite_song_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
