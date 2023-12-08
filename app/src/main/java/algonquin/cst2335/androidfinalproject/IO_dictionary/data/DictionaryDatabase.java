// DictionaryDatabase.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class DictionaryDatabase extends RoomDatabase {

    private static DictionaryDatabase instance;

    public abstract WordDao wordDao();

    public static synchronized DictionaryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DictionaryDatabase.class,
                            "dictionary_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
