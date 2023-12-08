package algonquin.cst2335.androidfinalproject.SK_sunrise.utils;

import android.content.Context;
import android.content.SharedPreferences;

import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

public class SharedPreferencesHelper {

        private static final String PREFS_NAME = "SunriseSunsetPrefs";
        private static final String KEY_LATITUDE = "latitude";
        private static final String KEY_LONGITUDE = "longitude";

        public static void saveSearchTerm(Context context, Location location) {
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putFloat(KEY_LATITUDE, (float) location.getLatitude());
            editor.putFloat(KEY_LONGITUDE, (float) location.getLongitude());
            editor.apply();
        }

        public static Location getSearchTerm(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            double latitude = prefs.getFloat(KEY_LATITUDE, 0.0f);
            double longitude = prefs.getFloat(KEY_LONGITUDE, 0.0f);
            return new Location(latitude, longitude);
        }
    }

