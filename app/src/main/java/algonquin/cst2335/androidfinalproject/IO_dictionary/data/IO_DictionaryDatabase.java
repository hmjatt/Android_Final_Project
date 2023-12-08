// IO_DictionaryDatabase.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_DefinitionListConverter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

@Database(entities = {IO_Word.class, IO_Definition.class}, version = 3, exportSchema = false)
@TypeConverters({IO_DefinitionListConverter.class})
public abstract class IO_DictionaryDatabase extends RoomDatabase {

    private static IO_DictionaryDatabase instance;

    public static synchronized IO_DictionaryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            IO_DictionaryDatabase.class,
                            "dictionary_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract IO_WordDao wordDao();

    public abstract IO_DefinitionDao definitionDao();

    // Add any additional DAOs as needed
}