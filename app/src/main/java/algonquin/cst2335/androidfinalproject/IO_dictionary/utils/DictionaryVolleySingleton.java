// io_dictionary.utils.DictionaryVolleySingleton
package algonquin.cst2335.androidfinalproject.IO_dictionary.utils;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DictionaryVolleySingleton {
    private static DictionaryVolleySingleton instance;
    private RequestQueue requestQueue;

    private DictionaryVolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized DictionaryVolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new DictionaryVolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
