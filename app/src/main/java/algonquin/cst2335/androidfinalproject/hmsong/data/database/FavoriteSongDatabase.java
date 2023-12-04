// FavoriteSongDatabase.java
package algonquin.cst2335.androidfinalproject.hmsong.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

/**
 * Room Database class for managing FavoriteSong entities.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
@Database(entities = {FavoriteSong.class}, version = 1, exportSchema = false)
public abstract class FavoriteSongDatabase extends RoomDatabase {

    /**
     * Get the DAO (Data Access Object) for FavoriteSong.
     *
     * @return The FavoriteSongDao.
     */
    public abstract FavoriteSongDao favoriteSongDao();

    /**
     * The single instance of the FavoriteSongDatabase.
     */
    private static FavoriteSongDatabase instance;

    /**
     * Create a singleton instance of the database.
     *
     * @param context The application context.
     * @return The singleton instance of FavoriteSongDatabase.
     */
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
