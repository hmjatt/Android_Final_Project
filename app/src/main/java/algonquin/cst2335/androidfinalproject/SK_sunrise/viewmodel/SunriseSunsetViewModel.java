package algonquin.cst2335.androidfinalproject.SK_sunrise.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import algonquin.cst2335.androidfinalproject.SK_sunrise.data.AppDatabase;
import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

public class SunriseSunsetViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;

    public SunriseSunsetViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
    }

    // Method to get sunrise/sunset data from the database or network
    public LiveData<String> getSunriseSunsetData(double latitude, double longitude) {
        // For simplicity, use MutableLiveData to simulate the data retrieval
        MutableLiveData<String> resultLiveData = new MutableLiveData<>();

        // In a real-world scenario, implement logic to retrieve data from the database or network
        // For now, let's simulate the data with a simple message
        String resultMessage = "Sunrise and sunset data for " + latitude + ", " + longitude;
        resultLiveData.setValue(resultMessage);

        return resultLiveData;
    }

    // Method to insert a location into the database
    public void insertLocation(Location location) {
        // Insert the location into the database using Room
        new Thread(() -> appDatabase.locationDao().insertLocation(location)).start();
    }
}
