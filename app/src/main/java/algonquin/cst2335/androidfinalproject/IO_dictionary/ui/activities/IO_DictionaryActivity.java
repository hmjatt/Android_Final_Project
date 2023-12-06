package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_DefinitionsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_WordFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.IO_DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class IO_DictionaryActivity extends AppCompatActivity implements IO_WordsAdapter.OnWordClickListener,
        IO_WordsAdapter.OnSaveButtonClickListener {

    private EditText searchEditText;
    private Button searchButton;

    private IO_WordsAdapter wordsAdapter;
    private List<IO_Word> dictionaryWords;

    private List<IO_Word> dictionaryPartOfSpeeches;

    private IO_DictionaryDatabase dictionaryDatabase; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_io_activity_dictionary);

        // Assuming you have a RecyclerView with the ID "dictionaryRecycler" in your layout
        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);

        // Create an empty list of words or fetch it from somewhere
        dictionaryWords = new ArrayList<>();

        dictionaryPartOfSpeeches = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView
        wordsAdapter = new IO_WordsAdapter(dictionaryWords, dictionaryPartOfSpeeches, this);
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
//                    Log.d("DictionaryActivity", "API response received: " + response.toString());

                    // Parse the JSON response and update the RecyclerView
                    List<IO_Word> words = parseJsonResponse(response);
                    List<IO_Word> partOfSpeeches = parseJsonResponse(response);

//                    Log.d("partOfSpeeches", "API response received: " + partOfSpeeches);

                    // Update the RecyclerView with definitions
                    updateRecyclerView(words, partOfSpeeches);
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

    // Add this method to your DictionaryActivity.java to convert List<Definition> to String
    private String convertDefinitionsToString(List<IO_Definition> definitions) {
        // Implement the logic to convert the list to a string, e.g., concatenate definitions
        // Return the resulting string
        // You might need to adjust this based on how you want to store the definitions
        return definitions.toString();
    }

    private List<IO_Word> parseJsonResponse(JSONArray jsonResponse) {
        List<IO_Word> words = new ArrayList<>();

        try {
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject wordObject = jsonResponse.getJSONObject(i);

                // Extract the word
                String wordText = wordObject.getString("word");

                // Create a Word object
                IO_Word word = new IO_Word(wordText);

                // Check if the response has an array of meanings
                if (wordObject.has("meanings")) {
                    JSONArray meaningsArray = wordObject.getJSONArray("meanings");

                    // Iterate through meanings
                    for (int j = 0; j < meaningsArray.length(); j++) {
                        JSONObject meaningObject = meaningsArray.getJSONObject(j);

                        // Extract part of speech
                        String partOfSpeechText = meaningObject.getString("partOfSpeech");

                        // Set the part of speech for the Word object
                        word.setPartOfSpeech(partOfSpeechText);

                        // Check if the meaningObject has an array of definitions
                        if (meaningObject.has("definitions")) {
                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");

                            // Iterate through definitions
                            for (int k = 0; k < definitionsArray.length(); k++) {
                                JSONObject definition = definitionsArray.getJSONObject(k);

                                // Extract definition text
                                String definitionText = definition.getString("definition");

                                // Add the definition to the Word object
                                word.addDefinition(new IO_Definition(definitionText));
                            }
                        }
                    }

                    // Add the Word object to the list for each meaning
                    words.add(word);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return words;
    }


    public void updateRecyclerView(List<IO_Word> words, List<IO_Word> partOfSpeeches) {
        wordsAdapter.setWords(words, partOfSpeeches);
//        wordsAdapter.setPartOfSpeeches(parOfSpeeches);
        Log.d("partOfSpeeches", "API response received: " + partOfSpeeches);

    }

    @Override
    public void onWordClick(IO_Word word) {
        // Create a new DefinitionsFragment instance
        IO_DefinitionsFragment definitionsFragment = new IO_DefinitionsFragment();

        // Pass data to the fragment
        definitionsFragment.updateDefinitions(word.getDefinitions());

        // Replace the current fragment with DefinitionsFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, definitionsFragment)
                .addToBackStack(null)
                .commit();
    }

    // Helper method to convert List<IO_Definition> to List<String>
    private List<String> getDefinitionsAsStringList(List<IO_Definition> definitions) {
        List<String> definitionStrings = new ArrayList<>();
        for (IO_Definition definition : definitions) {
            definitionStrings.add(definition.getDefinition());
        }
        return definitionStrings;
    }

    @Override
    public void onSaveButtonClick(IO_Word word) {
        // Handle saving logic here
        saveWordToDatabase(word);
    }

    private void saveWordToDatabase(IO_Word word) {
        // Save the word to the database asynchronously using Kotlin Coroutines
        new Thread(() -> {
            // For example, assuming you have a WordDao in DictionaryDatabase
            dictionaryDatabase.wordDao().insertWord(word);

            // You may also update the UI or provide a confirmation message
            // For now, use logs to test the save operation
            Log.d("DictionaryActivity", "Word saved to database: " + word.getWord());
        }).start();
    }

}
