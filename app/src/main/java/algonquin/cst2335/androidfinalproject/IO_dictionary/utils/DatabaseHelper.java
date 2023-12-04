// DatabaseHelper.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.SavedWordDao;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;

@Database(entities = {SavedWord.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    private static DatabaseHelper instance;

    public abstract SavedWordDao savedWordDao();

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseHelper.class,
                            "dictionary_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
