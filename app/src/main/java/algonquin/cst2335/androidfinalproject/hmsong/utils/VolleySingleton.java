package algonquin.cst2335.androidfinalproject.hmsong.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class for managing the Volley RequestQueue.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class VolleySingleton {

    /**
     * The single instance of the VolleySingleton.
     */
    private static VolleySingleton instance;

    /**
     * The Volley RequestQueue.
     */
    private RequestQueue requestQueue;

    /**
     * Private constructor to create a new instance of VolleySingleton.
     *
     * @param context The application context.
     */
    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Get the instance of VolleySingleton.
     *
     * @param context The application context.
     * @return The instance of VolleySingleton.
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    /**
     * Get the Volley RequestQueue.
     *
     * @return The Volley RequestQueue.
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
