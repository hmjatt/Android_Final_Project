package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.IO_DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;


// ... (import statements remain unchanged)

public class IO_DictionaryActivity extends AppCompatActivity implements IO_WordsAdapter.OnWordClickListener {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private EditText searchEditText;
    private Button searchButton;

    private IO_WordsAdapter wordsAdapter;
    private List<IO_Word> dictionaryWords;


    private IO_DictionaryDatabase dictionaryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_io_activity_dictionary);

        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);
        dictionaryWords = new ArrayList<>();

        wordsAdapter = new IO_WordsAdapter(dictionaryWords, this);
        recyclerView.setAdapter(wordsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dictionaryDatabase = IO_DictionaryDatabase.getInstance(this);

        searchEditText = findViewById(R.id.searchWords);
        searchButton = findViewById(R.id.btnSearchWords);

        searchButton.setOnClickListener(view -> {
            Log.d("DictionaryActivity", "Search button clicked");

            String searchTerm = searchEditText.getText().toString();
            if (!TextUtils.isEmpty(searchTerm)) {
                makeApiRequest(searchTerm);
            }
        });

        Button btnViewSavedWords = findViewById(R.id.btnViewSavedWords);
        btnViewSavedWords.setOnClickListener(view -> {
            IO_SavedWordsFragment savedWordsFragment = new IO_SavedWordsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        updateSavedWordsRecyclerView();
    }

    private void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    new Thread(() -> {
                        List<IO_Word> words = parseJsonResponse(response);
                        handler.post(() -> updateRecyclerView(words));
                    }).start();
                },
                error -> {
                    Log.e("DictionaryActivity", "Error making API request: " + error.getMessage());
                }
        );

        IO_DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    private List<IO_Word> parseJsonResponse(JSONArray jsonResponse) {
        List<IO_Word> words = new ArrayList<>();

        try {
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject wordObject = jsonResponse.getJSONObject(i);

                String wordText = wordObject.getString("word");
                IO_Word word = new IO_Word(wordText);

                if (wordObject.has("meanings")) {
                    JSONArray meaningsArray = wordObject.getJSONArray("meanings");

                    for (int j = 0; j < meaningsArray.length(); j++) {
                        JSONObject meaningObject = meaningsArray.getJSONObject(j);


                        String partOfSpeechText = meaningObject.getString("partOfSpeech");
                        word.setPartOfSpeech(partOfSpeechText);

                        // Check if the word already exists in the database
                        IO_Word existingWord = dictionaryDatabase.wordDao().getWordByWordSync(wordText, partOfSpeechText);

                        if (existingWord == null) {

                            if (meaningObject.has("definitions")) {
                                JSONArray definitionsArray = meaningObject.getJSONArray("definitions");

                                for (int k = 0; k < definitionsArray.length(); k++) {
                                    JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                    String definitionText = definitionObject.getString("definition");

                                    IO_Definition def = new IO_Definition(definitionText);
                                    // Save word before saving definition
                                    saveWordToDatabase(word);
                                    saveDefinitionToDatabase(word, def);
                                }
                            }
                        }
                    }
                }
                // Insert the word into the database after retrieving part of speech
                words.add(word);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return words;
    }

    private void btnViewSavedWordsClick() {
        LiveData<List<IO_Word>> savedWordsLiveData = dictionaryDatabase.wordDao().getAllWords();
        savedWordsLiveData.observe(this, savedWords -> {
            handler.post(() -> wordsAdapter.setWords(savedWords));
        });
    }

    private void updateSavedWordsRecyclerView() {
        btnViewSavedWordsClick();
    }


    private void updateRecyclerView(List<IO_Word> words) {
        wordsAdapter.setWords(words);
    }

//    private void updateSavedWordsRecyclerView() {
//        runOnUiThread(() -> {
//            LiveData<List<IO_Word>> savedWordsLiveData = dictionaryDatabase.wordDao().getAllWords();
//            savedWordsLiveData.observe(this, savedWords -> {
//                handler.post(() -> wordsAdapter.setWords(savedWords));
//            });
//        });
//    }

    @Override
    public void onWordClick(IO_Word word) {
        TextView tvWordDetail = findViewById(R.id.tvWordDetail);

        if (tvWordDetail != null) {
            tvWordDetail.setText(word != null ? word.getWord() : "No word selected");
        } else {
            Log.e("DictionaryActivity", "TextView tvWordDetail not found in the layout");
        }

        List<IO_Definition> definitions = getDefinitionsFromDatabase(word);
        IO_DefinitionsAdapter definitionsAdapter = new IO_DefinitionsAdapter(definitions);

        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(definitionsAdapter);
    }

    private List<IO_Definition> getDefinitionsFromDatabase(IO_Word word) {
        long wordId = word.getId();
        List<IO_Definition> definitions = new ArrayList<>();

        // Observe the LiveData to get the list of definitions
        LiveData<List<IO_Definition>> definitionsLiveData = dictionaryDatabase.definitionDao().getDefinitionsByWordId(wordId);
        definitionsLiveData.observe(this, definitions::addAll);

        return definitions;
    }


    private void saveDefinitionToDatabase(IO_Word word, IO_Definition definition) {
        // Check if the word already exists in the database
        long wordId = word.getId();

        // Insert the provided definition into the database
        definition.setWordId(wordId);

        runOnUiThread(() -> {
            new Thread(() -> {
                dictionaryDatabase.definitionDao().insertDefinition(definition);
                Log.d("DictionaryActivity", "Definition saved to database: " + definition.getDefinition());
            }).start();
        });
    }

    private void saveWordToDatabase(IO_Word word) {
        // Check if the word already exists in the database
        IO_Word existingWord = dictionaryDatabase.wordDao().getWordByIdSync((int) word.getId());
        if (existingWord == null) {
            // If the word does not exist, insert it
            long wordId = dictionaryDatabase.wordDao().insertWord(word);
            word.setId(wordId);

            // Insert the provided definition into the database
            // Commented out for now, as definitions should be inserted separately
            // dictionaryDatabase.definitionDao().insertDefinition(definition);

            Log.d("DictionaryActivity", "Word saved to database: " + word.getWord());
        } else {
            // Log a message or handle the case where the word already exists
            Log.d("DictionaryActivity", "Word already exists in the database: " + word.getWord());
        }
    }


}