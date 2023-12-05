package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.DefinitionListConverter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;

@Database(entities = {Word.class, SavedWord.class}, version = 1, exportSchema = false)
@TypeConverters({DefinitionListConverter.class})
public abstract class DictionaryDatabase extends RoomDatabase {

    private static DictionaryDatabase instance;

    public abstract WordDao wordDao();

    public abstract SavedWordDao savedWordDao(); // Add this line

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
