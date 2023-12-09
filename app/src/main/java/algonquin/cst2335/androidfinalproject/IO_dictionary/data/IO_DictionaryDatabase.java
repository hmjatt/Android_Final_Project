package algonquin.cst2335.androidfinalproject.IO_dictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;

/**
 * Room database class for the IO Dictionary application.
 * Author: Iuliia Obukhova
 * Version: 1.0
 */
@Database(entities = {IO_Word.class, IO_Definition.class}, version = 3, exportSchema = false)
public abstract class IO_DictionaryDatabase extends RoomDatabase {

    private static IO_DictionaryDatabase instance;

    /**
     * Gets an instance of the database using the Singleton pattern.
     *
     * @param context The application context.
     * @return The database instance.
     */
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

    /**
     * Gets the DAO for accessing IO_Word entities.
     *
     * @return The IO_WordDao instance.
     */
    public abstract IO_WordDao wordDao();

    /**
     * Gets the DAO for accessing IO_Definition entities.
     *
     * @return The IO_DefinitionDao instance.
     */
    public abstract IO_DefinitionDao definitionDao();

    // Add any additional DAOs as needed
}
