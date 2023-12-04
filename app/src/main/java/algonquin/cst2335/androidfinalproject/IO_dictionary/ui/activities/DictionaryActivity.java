package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
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

        // Assuming you have a RecyclerView with the ID "recyclerViewWords" in your layout
        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);

        // Create an empty list of words or fetch it from somewhere
        List<Word> initialWordList = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView
        wordsAdapter = new WordsAdapter(initialWordList, this);
        recyclerView.setAdapter(wordsAdapter);

        // Set a LinearLayoutManager to your RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Display the default fragment when the activity is created
//        replaceFragment(new WordFragment());

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
// Log the API request
        Log.d("DictionaryActivity", "Making API request to: " + apiUrl);

        // Make API request using Volley or other networking library
        // Handle the JSON response and update the RecyclerView with definitions
        // Example code (you need to implement your own networking logic):

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    // Log the API response
                    Log.d("DictionaryActivity", "API response received: " + response.toString());

                    // Parse the JSON response and update the RecyclerView
                    List<Word> words = parseJsonResponse(response);
                    updateRecyclerView(words);
                },
                error -> {
                    // Log the API error
                    Log.e("DictionaryActivity", "Error making API request: " + error.getMessage());

                    // Handle error if needed
                }
                );

        // Update the RecyclerView with definitions
        DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }








    private List<Word> parseJsonResponse(JSONArray jsonResponse) {
        // Parse the JSON response and create a list of Word objects
        List<Word> words = new ArrayList<>();

        try {
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject definitionObject = jsonResponse.getJSONObject(i);

                // Extract the word
                String wordText = definitionObject.getString("word");

                Log.d("word", "API response received: " + wordText);

                // Check if the response has an array of meanings
                if (definitionObject.has("meanings")) {
                    JSONArray meaningsArray = definitionObject.getJSONArray("meanings");

                    Log.d("meanings", "API response received: " + meaningsArray);

                    // Iterate through meanings
                    for (int j = 0; j < meaningsArray.length(); j++) {
                        JSONObject meaningObject = meaningsArray.getJSONObject(j);

                        Log.d("meaning", "API response received: " + meaningObject);

                        // Check if the meaningObject has an array of definitions
                        if (meaningObject.has("definitions")) {
                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");

                            Log.d("defs", "API response received: " + definitionsArray);

                            // Create a Word object
                            Word word = new Word(wordText);

                            // Iterate through definitions
                            for (int k = 0; k < definitionsArray.length(); k++) {
                                JSONObject definition = definitionsArray.getJSONObject(k);

                                // Extract definition text
                                String definitionText = definition.getString("definition");

                                Log.d("defT", "API response received: " + definitionText);


                                // Extract definition text
//                                String defin = definition.getString("definition");





                                // Add the definition to the Word object
                                word.addDefinition(definitionText);
                            }

                            // Add the Word object to the list
                            words.add(word);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
