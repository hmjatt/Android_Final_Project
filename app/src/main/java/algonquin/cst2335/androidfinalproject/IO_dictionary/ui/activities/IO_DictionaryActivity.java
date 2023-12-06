package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_WordDefinitionFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.IO_DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class IO_DictionaryActivity extends AppCompatActivity implements IO_WordsAdapter.OnWordClickListener {

    private EditText searchEditText;
    private Button searchButton;

    private IO_WordsAdapter wordsAdapter;
    private List<IO_Word> dictionaryWords;

    private IO_DictionaryDatabase dictionaryDatabase; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_io_activity_dictionary);

        // Assuming you have a RecyclerView with the ID "dictionaryRecycler" in your layout
        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);

        // Create an empty list of words or fetch it from somewhere
        dictionaryWords = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView
        wordsAdapter = new IO_WordsAdapter(dictionaryWords, this);
        recyclerView.setAdapter(wordsAdapter);

        // Set a LinearLayoutManager to your RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the dictionaryDatabase
        dictionaryDatabase = IO_DictionaryDatabase.getInstance(this);

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
            IO_SavedWordsFragment savedWordsFragment = new IO_SavedWordsFragment();
            // Use FragmentManager to replace the current fragment with SavedWordsFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        // Make API request using Volley or other networking library
        // Handle the JSON response and update the RecyclerView with definitions

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    // Log the API response
                    Log.d("DictionaryActivity", "API response received: " + response.toString());

                    // Parse the JSON response and update the RecyclerView
                    List<IO_Word> words = parseJsonResponse(response);

                    // Insert words into your dictionary
                    insertWordsIntoDictionary(words);

                    // Update the RecyclerView with definitions
                    updateRecyclerView(words);
                },
                error -> {
                    // Log the API error
                    Log.e("DictionaryActivity", "Error making API request: " + error.getMessage());

                    // Handle error if needed
                }
        );

        // Update the RecyclerView with definitions
        IO_DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void insertWordsIntoDictionary(List<IO_Word> words) {
        // Add logic to insert words into your dictionary
        // For example, assuming you have a DictionaryDatabaseHelper class
        // Assuming dbHelper is an instance of DatabaseHelper
        // DictionaryDatabase dbHelper = DictionaryDatabase.getInstance(getApplicationContext()); // Remove this line

        // Retrieve the Word object from the intent or wherever it's coming from
        Intent intent = getIntent();
        IO_Word word = intent.getParcelableExtra("word"); // Replace "word" with the actual key

        // Check if word is not null before proceeding
        if (word != null) {
            // Convert List<Definition> to String (you might need to adjust this based on your needs)
            String definitionsAsString = convertDefinitionsToString(word.getDefinitions());

            // Create a SavedWord object using the Word object
            IO_SavedWord savedWord = new IO_SavedWord(word.getWord(), definitionsAsString);

            // Insert the SavedWord into the database
            dictionaryDatabase.savedWordDao().insert(savedWord);
        }
    }

    // Add this method to your DictionaryActivity.java to convert List<Definition> to String
    private String convertDefinitionsToString(List<IO_Definition> definitions) {
        // Implement the logic to convert the list to a string, e.g., concatenate definitions
        // Return the resulting string
        // You might need to adjust this based on how you want to store the definitions
        return definitions.toString();
    }

    private List<IO_Word> parseJsonResponse(JSONArray jsonResponse) {
        // Parse the JSON response and create a list of Word objects
        List<IO_Word> words = new ArrayList<>();

        try {
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject definitionObject = jsonResponse.getJSONObject(i);

                // Extract the word
                String wordText = definitionObject.getString("word");

                // Check if the response has an array of meanings
                if (definitionObject.has("meanings")) {
                    JSONArray meaningsArray = definitionObject.getJSONArray("meanings");

                    // Iterate through meanings
                    for (int j = 0; j < meaningsArray.length(); j++) {
                        JSONObject meaningObject = meaningsArray.getJSONObject(j);

                        // Check if the meaningObject has an array of definitions
                        if (meaningObject.has("definitions")) {
                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");

                            String partOfSpeech = meaningObject.getString("partOfSpeech");

                            wordText = wordText + " - " + partOfSpeech;

                            // Create a Word object
                            IO_Word word = new IO_Word(wordText);

                            // Iterate through definitions
                            for (int k = 0; k < definitionsArray.length(); k++) {
                                JSONObject definition = definitionsArray.getJSONObject(k);

                                // Extract definition text
                                String definitionText = definition.getString("definition");

                                // Add the definition to the Word object
                                word.addDefinition(new IO_Definition(definitionText));
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

    public void updateRecyclerView(List<IO_Word> words) {
        wordsAdapter.setWords(words);
    }

    @Override
    public void onWordClick(IO_Word word) {
        // Handle item click, load WordDetailFragment with the selected word
        // Use a callback or communication method to communicate with the activity
        // and load the WordDetailFragment.
        // For now, use logs to test the click event.
        Log.d("DictionaryActivity", "Word clicked: " + word.getWord());

        // Replace the current fragment with WordDetailFragment
        IO_WordDefinitionFragment wordDetailFragment = new IO_WordDefinitionFragment();

        // Save the selected word to the database
        saveWordToDatabase(word);

        // Pass the selected word to WordDetailFragment using arguments
        Bundle bundle = new Bundle();
        bundle.putParcelable(IO_WordDefinitionFragment.ARG_SELECTED_WORD, word);
        wordDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, wordDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void saveWordToDatabase(IO_Word word) {
        // Save the word to the database asynchronously using Kotlin Coroutines
        new Thread(() -> {
            // For example, assuming you have a SavedWordDao in DictionaryDatabase
            IO_SavedWord savedWord = new IO_SavedWord(word.getWord(), word.getDefinitions().toString());
            dictionaryDatabase.savedWordDao().insert(savedWord);

            // You may also update the UI or provide a confirmation message
            // For now, use logs to test the save operation
            Log.d("DictionaryActivity", "Word saved to database: " + word.getWord());
        }).start();
    }

}
