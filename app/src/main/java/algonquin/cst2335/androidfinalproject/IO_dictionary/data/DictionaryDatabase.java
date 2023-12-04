// io_dictionary.data.DictionaryDatabase
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {io_dictionary.data.FavoriteWord.class}, version = 1, exportSchema = false)
public abstract class DictionaryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "dictionary_database";
    private static DictionaryDatabase instance;

    public abstract io_dictionary.data.FavoriteWordDao favoriteWordDao();

    public static synchronized DictionaryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DictionaryDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
