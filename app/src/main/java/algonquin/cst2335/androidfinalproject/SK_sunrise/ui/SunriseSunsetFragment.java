package algonquin.cst2335.androidfinalproject.SK_sunrise.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;
import algonquin.cst2335.androidfinalproject.SK_sunrise.utils.SharedPreferencesHelper;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.SK_sunrise.viewmodel.SunriseSunsetViewModel;


public class SunriseSunsetFragment extends Fragment {
    private EditText etLatitude;
    private EditText etLongitude;
    private Button btnLookup;
    private TextView tvResult;
    private Button btnSave;

    private SunriseSunsetViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sk_fragment_sunrise_sunset, container, false);

        etLatitude = view.findViewById(R.id.etLatitude);
        etLongitude = view.findViewById(R.id.etLongitude);
        btnLookup = view.findViewById(R.id.btnLookup);
        tvResult = view.findViewById(R.id.tvResult);
        btnSave = view.findViewById(R.id.btnSave);

        btnLookup.setOnClickListener(v -> performLookup());
        btnSave.setOnClickListener(v -> saveToFavorites());

        // Set the previous search term from SharedPreferences
        Location savedLocation = SharedPreferencesHelper.getSearchTerm(requireContext());
        etLatitude.setText(String.valueOf(savedLocation.getLatitude()));
        etLongitude.setText(String.valueOf(savedLocation.getLongitude()));

        viewModel = new ViewModelProvider(this).get(SunriseSunsetViewModel.class);

        setHasOptionsMenu(true);  // Enable onCreateOptionsMenu callback
        return view;
    }

    private void performLookup() {
        double latitude = Double.parseDouble(etLatitude.getText().toString());
        double longitude = Double.parseDouble(etLongitude.getText().toString());

        // Construct the API URL
        String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude +
                "&lng=" + longitude +
                "&timezone=UTC" +
                "&date=today";

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the API response and update tvResult
                        processApiResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here, e.g., show a Toast
                        Toast.makeText(requireContext(), "Error fetching sunrise/sunset data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    private void saveToFavorites() {
        if (viewModel != null) {
            double latitude = Double.parseDouble(etLatitude.getText().toString());
            double longitude = Double.parseDouble(etLongitude.getText().toString());

            Location location = new Location(latitude, longitude);

            // Insert the location into the database using ViewModel
            viewModel.insertLocation(location);

            // Provide appropriate feedback using Toast or Snackbar
            Toast.makeText(requireContext(), "Location saved to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("MyApp", "viewModel is null");
        }
    }


    // Helper method to process the API response
    private void processApiResponse(JSONObject response) {
        try {
            // Extract sunrise and sunset information from the JSON response
            String sunrise = response.getJSONObject("results").getString("sunrise");
            String sunset = response.getJSONObject("results").getString("sunset");

            // Display the results in tvResult
            String result = "Sunrise: " + sunrise + "\nSunset: " + sunset;
            tvResult.setText(result);
        } catch (Exception e) {
            // Handle any parsing errors or missing data
            Toast.makeText(requireContext(), "Error parsing sunrise/sunset data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sk_menu_location_search, menu);

        // Get the SearchView from the menu item
        MenuItem searchItem = menu.findItem(R.id.location_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up search functionality if needed
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_favorites) {
            // Replace the current fragment with FavoritesFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, new FavoritesFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (itemId == R.id.action_help) {
            // Handle the Help menu item click
            showHelpDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        // Implement the logic to show a dialog with instructions for using the interface
        new AlertDialog.Builder(requireContext())
                .setTitle("Help")
                .setMessage("This is where you put your help information.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}

