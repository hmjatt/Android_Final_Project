// DictionaryVolleySingleton.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DictionaryVolleySingleton {

    private static DictionaryVolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context applicationContext; // Change to applicationContext

    private DictionaryVolleySingleton(Context context) {
        this.applicationContext = context.getApplicationContext(); // Use applicationContext
        requestQueue = getRequestQueue();
    }

    public static synchronized DictionaryVolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new DictionaryVolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
