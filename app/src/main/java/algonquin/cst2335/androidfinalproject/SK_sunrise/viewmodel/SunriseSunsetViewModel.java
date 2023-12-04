package algonquin.cst2335.androidfinalproject.SK_sunrise.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import algonquin.cst2335.androidfinalproject.SK_sunrise.data.AppDatabase;
import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

public class SunriseSunsetViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private MutableLiveData<List<Location>> allLocations;  // Use MutableLiveData

    public SunriseSunsetViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        allLocations = new MutableLiveData<>();
        loadData();  // Load data on initialization
    }

    // Getter method for allLocations
    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    // Method to insert a location into the database
    public void insertLocation(Location location) {
        // Insert the location into the database using Room
        new Thread(() -> {
            appDatabase.locationDao().insertLocation(location);
            loadData();  // Reload data after insertion
        }).start();
    }

    // Helper method to load data from the database
    private void loadData() {
        new Thread(() -> {
            List<Location> locations = appDatabase.locationDao().getAllLocations();
            allLocations.postValue(locations);  // Post the result to the main thread
        }).start();
    }
}
