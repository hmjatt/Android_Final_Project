package algonquin.cst2335.androidfinalproject.IO_dictionary.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class for managing Volley requests in the IO Dictionary application.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
public class IO_DictionaryVolleySingleton {

    private static IO_DictionaryVolleySingleton instance;
    private final Context applicationContext;
    private RequestQueue requestQueue;

    /**
     * Private constructor to create a new instance of the singleton.
     *
     * @param context The application context.
     */
    private IO_DictionaryVolleySingleton(Context context) {
        this.applicationContext = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    /**
     * Gets the singleton instance of the IO_DictionaryVolleySingleton.
     *
     * @param context The application context.
     * @return The singleton instance.
     */
    public static synchronized IO_DictionaryVolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new IO_DictionaryVolleySingleton(context);
        }
        return instance;
    }

    /**
     * Gets the request queue for Volley requests.
     *
     * @return The RequestQueue instance.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext);
        }
        return requestQueue;
    }

    /**
     * Adds a request to the Volley request queue.
     *
     * @param req The request to be added.
     * @param <T> The type of the request.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
