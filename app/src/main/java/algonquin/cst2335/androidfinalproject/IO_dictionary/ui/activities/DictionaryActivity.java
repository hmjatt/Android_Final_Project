// io_dictionary.ui.activities.DictionaryActivity
package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class DictionaryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DefinitionsAdapter definitionsAdapter;

    private Button showToastButton;
    private Button showSnackbarButton;

    private EditText searchEditText;
    private RequestQueue requestQueue;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.editTextSearch);
        requestQueue = DictionaryVolleySingleton.getInstance(this).getRequestQueue();

        sharedPreferences = getSharedPreferences("SearchTerms", MODE_PRIVATE);

        // Retrieve and set the last search term from SharedPreferences
        String lastSearchTerm = sharedPreferences.getString("last_search_term", "");
        searchEditText.setText(lastSearchTerm);

        showToastButton = findViewById(R.id.buttonShowToast);
        showSnackbarButton = findViewById(R.id.buttonShowSnackbar);

        showToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Toast notification
                Toast.makeText(DictionaryActivity.this, "This is a Toast!", Toast.LENGTH_SHORT).show();
            }
        });

        showSnackbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Snackbar notification
                Snackbar.make(v, "This is a Snackbar!", Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example data, replace with actual data from API response
        List<Definition> definitions = /* Your list of definitions */;

        definitionsAdapter = new DefinitionsAdapter(definitions);
        recyclerView.setAdapter(definitionsAdapter);

        // Add code for handling search functionality, API requests, and other UI elements
    }


    private void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response and update your UI or data accordingly
                        // Example: Update your RecyclerView adapter with the definitions
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        // Example: Show an error message or log the error
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save the current search term to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_search_term", searchEditText.getText().toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hm_menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Add your help instructions here.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
