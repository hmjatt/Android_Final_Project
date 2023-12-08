package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
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
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_SavedWordDefinitionFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.IO_WordDetailFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.IO_DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;


public class IO_DictionaryActivity extends AppCompatActivity
        implements IO_WordsAdapter.OnWordClickListener, IO_WordsAdapter.OnSaveButtonClickListener,
        IO_SavedWordsAdapter.OnSavedWordClickListener {
    private final Handler handler = new Handler(Looper.getMainLooper());

    private EditText searchEditText;
    private Button searchButton;

    private IO_WordsAdapter wordsAdapter;
    private List<IO_Word> dictionaryWords;

    private IO_DictionaryDatabase dictionaryDatabase;

    private IO_SavedWordsFragment savedWordsFragment;
    private IO_SavedWordDefinitionFragment savedWordDefinitionFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_io_activity_dictionary);

        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);
        dictionaryWords = new ArrayList<>();

        wordsAdapter = new IO_WordsAdapter(dictionaryWords, this, this); // Pass the activity as both OnWordClickListener and OnSaveButtonClickListener
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

        savedWordsFragment = new IO_SavedWordsFragment();
        savedWordDefinitionFragment = new IO_SavedWordDefinitionFragment();

        Button btnViewSavedDefinitions = findViewById(R.id.btnViewSavedWords);
        btnViewSavedDefinitions.setOnClickListener(view -> switchToSavedWordsFragment());

    }

    private void switchToSavedWordsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContentDic, savedWordsFragment);
        fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
        fragmentTransaction.commit();
    }


    private void switchToSavedWordDefinitionFragment(IO_Word savedWord) {
        Bundle args = new Bundle();
        args.putLong(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
        savedWordDefinitionFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContentDic, savedWordDefinitionFragment);
        fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
        fragmentTransaction.commit();
    }

    private void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    new Thread(() -> {
                        try {
                            List<IO_Word> words = parseJsonResponse(response);
                            handler.post(() -> updateRecyclerView(words));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error parsing JSON response");
                        }
                    }).start();
                },
                error -> {
                    Log.e("DictionaryActivity", "Error making API request: " + error.getMessage());
                    showToast("Error making API request");
                }
        );

        IO_DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private List<IO_Word> parseJsonResponse(JSONArray jsonResponse) throws JSONException {
        List<IO_Word> words = new ArrayList<>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject wordObject = jsonResponse.getJSONObject(i);

            String wordText = wordObject.getString("word");
            IO_Word word = new IO_Word(wordText);

            if (wordObject.has("meanings")) {
                JSONArray meaningsArray = wordObject.getJSONArray("meanings");

                for (int j = 0; j < meaningsArray.length(); j++) {
                    JSONObject meaningObject = meaningsArray.getJSONObject(j);


                    IO_Word existingWord = dictionaryDatabase.wordDao().getWordByWordSync(wordText);

                    if (existingWord == null) {
                        if (meaningObject.has("definitions")) {
                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");

                            // Separate method for fetching and saving definitions
                            List<IO_Definition> definitions = fetchAndSaveDefinitions(word, definitionsArray);
                            words.add(word);
                        }
                    }
                }
            }

            // Set up the save button click listener
            wordsAdapter.setOnWordClickListener(this);
        }

        return words;
    }


    private List<IO_Definition> fetchAndSaveDefinitions(IO_Word word, JSONArray definitionsArray) throws JSONException {
        List<IO_Definition> definitions = new ArrayList<>();

        for (int k = 0; k < definitionsArray.length(); k++) {
            JSONObject definitionObject = definitionsArray.getJSONObject(k);
            String definitionText = definitionObject.getString("definition");

            IO_Definition def = new IO_Definition(definitionText);

            // Save word before saving definition
            saveWordToDatabase(word);

            // Save definition to the database with the word's foreign key
            saveDefinitionToDatabase(word, def);

            definitions.add(def);
        }

        return definitions;
    }


    private void btnViewSavedDefinitionsClick() {
        LiveData<List<IO_Word>> savedWordsLiveData = dictionaryDatabase.wordDao().getAllWords();
        savedWordsLiveData.observe(this, savedWords -> {
            handler.post(() -> wordsAdapter.setSavedWords(savedWords));
        });
    }

    private void updateRecyclerView(List<IO_Word> words) {
        wordsAdapter.setWords(words);
    }


    @Override
    public void onSaveButtonClick(IO_Word word) {
        // Handle the save button click for the selected word
        saveWordAndDefinitions(word);
        showToast("Word and definitions saved");
    }


    @Override
    public void onSavedWordClick(IO_Word savedWord) {
        switchToSavedWordDefinitionFragment(savedWord);
    }

    private void navigateToDefinitionsFragment(IO_Word savedWord) {
        IO_SavedWordDefinitionFragment definitionFragment = new IO_SavedWordDefinitionFragment();

        Bundle args = new Bundle();
        args.putLong(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
        definitionFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContentDic, definitionFragment)
                .addToBackStack(null)
                .commit();
    }


    private List<IO_Definition> getDefinitionsFromDatabase(IO_Word word) {
        long wordId = word.getId();
        List<IO_Definition> definitions = new ArrayList<>();

        LiveData<List<IO_Definition>> definitionsLiveData = dictionaryDatabase.definitionDao().getDefinitionsByWordId(wordId);
        definitionsLiveData.observe(this, definitions::addAll);

        return definitions;
    }


    private void saveDefinitionToDatabase(IO_Word word, IO_Definition definition) {
        long wordId = word.getId();
        definition.setWordId(wordId);

        runOnUiThread(() -> {
            new Thread(() -> {
                dictionaryDatabase.definitionDao().insertDefinition(definition);
                Log.d("DictionaryActivity", "Definition saved to database: " + definition.getDefinition());
            }).start();
        });
    }

    private void saveWordToDatabase(IO_Word word) {
        IO_Word existingWord = dictionaryDatabase.wordDao().getWordByIdSync((int) word.getId());
        if (existingWord == null) {
            long wordId = dictionaryDatabase.wordDao().insertWord(word);
            word.setId(wordId);
            Log.d("DictionaryActivity", "Word saved to database: " + word.getWord());
        } else {
            Log.d("DictionaryActivity", "Word already exists in the database: " + word.getWord());
        }
    }


    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(IO_DictionaryActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onWordClick(IO_Word word) {
        IO_WordDetailFragment wordDetailFragment = (IO_WordDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flContentDic);
        if (wordDetailFragment != null) {
            wordDetailFragment.updateWordDetail(word);
        } else {
            Log.e("DictionaryActivity", "WordDetailFragment not found in the layout");
        }

        Log.d("DictionaryActivity", "Clicked on word: " + (word != null ? word.getWord() : "null"));

        List<IO_Definition> definitions = getDefinitionsFromDatabase(word);
        Log.d("DictionaryActivity", "Number of definitions for the word: " + definitions.size());

        IO_DefinitionsAdapter definitionsAdapter = new IO_DefinitionsAdapter(definitions);

        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(definitionsAdapter);
    }

    private void saveWordAndDefinitions(IO_Word word) {
        saveWordToDatabase(word);
        List<IO_Definition> definitions = getDefinitionsFromApi(word);

        for (IO_Definition definition : definitions) {
            saveDefinitionToDatabase(word, definition);
        }
        showToast("Word and definitions saved");
    }

    private List<IO_Definition> getDefinitionsFromApi(IO_Word word) {
        List<IO_Definition> definitions = new ArrayList<>();

        // TODO: Implement your logic to fetch definitions from an API using the word parameter

        return definitions;
    }

}
