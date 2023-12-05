package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DefinitionListConverter {

    @TypeConverter
    public static List<Definition> fromString(String value) {
        Type listType = new TypeToken<List<Definition>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Definition> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
