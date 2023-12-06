package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IO_DefinitionListConverter {

    @TypeConverter
    public static List<IO_Definition> fromString(String value) {
        Type listType = new TypeToken<List<IO_Definition>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<IO_Definition> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
