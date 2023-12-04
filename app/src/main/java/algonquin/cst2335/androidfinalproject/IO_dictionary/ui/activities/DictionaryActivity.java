package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.WordDetailFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.WordFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class DictionaryActivity extends AppCompatActivity implements WordsAdapter.OnWordClickListener {

    private EditText searchEditText;
    private Button searchButton;

    private WordsAdapter wordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_activity_dictionay);

        // Display the default fragment when the activity is created
        replaceFragment(new WordFragment());

        // Set up EditText and Button
        searchEditText = findViewById(R.id.searchWords);
        searchButton = findViewById(R.id.btnSearchWords);

        searchButton.setOnClickListener(view -> {
            // Handle search button click
            Log.d("DictionaryActivity", "Search button clicked");

            String searchTerm = searchEditText.getText().toString();
            if (!TextUtils.isEmpty(searchTerm)) {
                // Make API request and update the RecyclerView with the definitions
                makeApiRequest(searchTerm);
            }
        });

        // Set up click listener for "View Saved Words" button
        Button btnViewSavedWords = findViewById(R.id.btnViewSavedWords);
        btnViewSavedWords.setOnClickListener(view -> {
            // Handle the button click, for example, navigate to the SavedWordsFragment
            SavedWordsFragment savedWordsFragment = new SavedWordsFragment();
            // Use FragmentManager to replace the current fragment with SavedWordsFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void replaceFragment(Fragment fragment) {
        // Replace the current fragment with the selected one
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateRecyclerView(List<Word> words) {
        wordsAdapter.setWords(words);
    }

    public void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        Log.d("DictionaryActivity", "Search button clicked");

        // Make API request using Volley or other networking library
        // Handle the JSON response and update the RecyclerView with definitions
        // Example code (you need to implement your own networking logic):

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    // Parse the JSON response and update the RecyclerView
                    List<Word> words = parseJsonResponse(response);
                    updateRecyclerView(words);
                },
                error -> {
                    // Handle error if needed
                }
        );

        // Update the RecyclerView with definitions
        DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private List<Word> parseJsonResponse(JSONObject jsonResponse) {
        // Parse the JSON response and create a list of Word objects
        // Example code (you need to implement your own parsing logic):
        List<Word> words = new ArrayList<>();
        try {
            // Check if the response has an array of definitions
            JSONArray definitionsArray = jsonResponse.getJSONArray("definitions");

            for (int i = 0; i < definitionsArray.length(); i++) {
                JSONObject definitionObject = definitionsArray.getJSONObject(i);

                // Extract relevant information from the definitionObject
                String word = definitionObject.getString("word");
                String definition = definitionObject.getString("definition");

                // Create a Word object and add it to the list
                Word wordObject = new Word(word, definition);
                words.add(wordObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parse the JSON response and add words to the list
        return words;
    }

    @Override
    public void onWordClick(Word word) {
        // Handle item click, load WordDetailFragment with the selected word
        // Use a callback or communication method to communicate with the activity
        // and load the WordDetailFragment.
        // For now, use logs to test the click event.
        Log.d("DictionaryActivity", "Word clicked: " + word.getWord());

        // Replace the current fragment with WordDetailFragment
        WordDetailFragment wordDetailFragment = new WordDetailFragment();

        // Pass the selected word to WordDetailFragment using arguments
        Bundle bundle = new Bundle();
        bundle.putParcelable(WordDetailFragment.ARG_SELECTED_WORD, (Parcelable) word);
        wordDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, wordDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
