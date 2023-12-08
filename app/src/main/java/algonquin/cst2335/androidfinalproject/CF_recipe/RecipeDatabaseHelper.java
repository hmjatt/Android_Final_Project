package algonquin.cst2335.androidfinalproject.CF_recipe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {


    public RecipeDatabaseHelper(Context context) {
        super(context, "RecipeDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
