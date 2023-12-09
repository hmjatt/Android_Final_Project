package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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


/**
 * The main activity for the IO Dictionary application.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
public class IO_DictionaryActivity extends AppCompatActivity
        implements IO_WordsAdapter.OnWordClickListener,
        IO_SavedWordsAdapter.OnSavedWordClickListener {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private EditText searchEditText;
    private Button searchButton;

    private IO_WordsAdapter wordsAdapter;
    private List<IO_Word> dictionaryWords;

    private IO_DictionaryDatabase dictionaryDatabase;

    private IO_SavedWordsFragment savedWordsFragment;
    private IO_SavedWordDefinitionFragment savedWordDefinitionFragment;

    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_io_activity_dictionary);
        setSupportActionBar(findViewById(R.id.toolbar));

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
            String searchTerm = searchEditText.getText().toString();
            if (!TextUtils.isEmpty(searchTerm)) {
                makeApiRequest(searchTerm);
            }
        });

        savedWordsFragment = new IO_SavedWordsFragment();
        savedWordDefinitionFragment = new IO_SavedWordDefinitionFragment();

        displayPreviouslySearchedWords();

        Button btnViewSavedDefinitions = findViewById(R.id.btnViewSavedWords);
        btnViewSavedDefinitions.setOnClickListener(view -> switchToSavedWordsFragment());
    }

    /**
     * Switches to the fragment displaying saved words.
     */
    private void switchToSavedWordsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContentDic, savedWordsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Switches to the fragment displaying the definitions of a saved word.
     *
     * @param savedWord The saved word for which to display definitions.
     */
    private void switchToSavedWordDefinitionFragment(IO_Word savedWord) {
        Bundle args = new Bundle();
        args.putLong(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
        savedWordDefinitionFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContentDic, savedWordDefinitionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Makes an API request to fetch word definitions.
     *
     * @param searchTerm The term to search for.
     */
    private void makeApiRequest(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        saveSearchTermToSharedPreferences(searchTerm);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    new Thread(() -> {
                        try {
                            List<IO_Word> words = parseJsonResponse(response);

                            handler.post(() -> {
                                if (wordAlreadyExists(words)) {
                                    showToast("Word search success");
                                } else {
                                    showToast("Word already exists");
                                }
                                updateRecyclerView(words);
                                wordsAdapter.notifyDataSetChanged(); // Add this line

                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error parsing JSON response");
                        }
                    }).start();
                },
                error -> {
                    showToast("Error making API request");
                }
        );

        IO_DictionaryVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    /**
     * Checks if a word already exists in the list.
     *
     * @param words The list of words to check.
     * @return True if the word already exists; false otherwise.
     */
    private boolean wordAlreadyExists(List<IO_Word> words) {
        if (words.isEmpty()) {
            return false;
        }

        IO_Word firstWord = words.get(0);

        // Use a background thread to check if the word already exists in the database
        Callable<Boolean> callable = () -> {
            IO_Word existingWord = dictionaryDatabase.wordDao().getWordByWordSync(firstWord.getWord());
            return existingWord != null;
        };

        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        try {
            return futureTask.get(); // This will block until the result is available
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Parses the JSON response from the API.
     *
     * @param jsonResponse The JSON response to parse.
     * @return The list of parsed words.
     * @throws JSONException If there is an error parsing the JSON response.
     */
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

    /**
     * Displays the options menu.
     *
     * @param menu The menu to inflate.
     * @return True to display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.io_dictionary_menu, menu);
        return true;
    }

    /**
     * Handles menu item selection.
     *
     * @param item The selected menu item.
     * @return True if the item is handled; false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.io_menu_help) {
            showInstructionsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows an instructions dialog.
     */
    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instructions");
        builder.setMessage("Instructions:\n" +
                "1. Search for Definitions:\n   - Enter a word in the provided EditText.\n   - Click the \"Search\" button to look up its definition.\n\n" +
                "2. View and Save Definitions:\n   - Definitions are fetched from https://api.dictionaryapi.dev/api/v2/entries/en/XXXX.\n   - Save search terms and definitions by clicking the \"Save\" button.\n\n" +
                "3. Navigate and Manage Saved Words:\n   - Tap \"View Saved Words\" to see a list of saved words with their definitions.\n   - Click on a saved word to view its definitions.\n   - While viewing saved words, there's an option to delete specific definitions.\n\n" +
                "4. Persistent Search Term:\n   - The last search term is saved using SharedPreferences.\n   - On app restart, the previous search term is auto-populated in the search field.\n");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /**
     * Fetches and saves definitions for a given word from the API.
     *
     * @param word             The word for which to fetch and save definitions.
     * @param definitionsArray The JSON array containing definitions.
     * @return The list of saved definitions.
     * @throws JSONException If there is an error parsing the JSON response.
     */
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

    /**
     * Handles the click event of the "View Saved Definitions" button.
     */
    private void btnViewSavedDefinitionsClick() {
        LiveData<List<IO_Word>> savedWordsLiveData = dictionaryDatabase.wordDao().getAllWords();
        savedWordsLiveData.observe(this, savedWords -> {
            handler.post(() -> wordsAdapter.setSavedWords(savedWords));
        });
    }

    /**
     * Updates the RecyclerView with the given list of words.
     *
     * @param words The list of words to display.
     */
    private void updateRecyclerView(List<IO_Word> words) {
        wordsAdapter.setWords(words);
    }

    /**
     * Handles the click event of a saved word, switching to the fragment displaying its definitions.
     *
     * @param savedWord The clicked saved word.
     */
    @Override
    public void onSavedWordClick(IO_Word savedWord) {
        switchToSavedWordDefinitionFragment(savedWord);
    }


    /**
     * Navigates to the fragment displaying the definitions of a saved word.
     *
     * @param savedWord The saved word for which to display definitions.
     */
    private void navigateToDefinitionsFragment(IO_Word savedWord) {
        IO_SavedWordDefinitionFragment definitionFragment = new IO_SavedWordDefinitionFragment();

        // Pass the saved word ID as an argument to the fragment
        Bundle args = new Bundle();
        args.putLong(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
        definitionFragment.setArguments(args);

        // Begin a fragment transaction to replace the current fragment with the definitions fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContentDic, definitionFragment);

        // Add the transaction to the back stack to allow back navigation
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }


    /**
     * Retrieves definitions from the database for a given word.
     *
     * @param word The word for which to retrieve definitions.
     * @return The list of definitions for the given word.
     */
    private List<IO_Definition> getDefinitionsFromDatabase(IO_Word word) {
        long wordId = word.getId();
        List<IO_Definition> definitions = new ArrayList<>();

        LiveData<List<IO_Definition>> definitionsLiveData = dictionaryDatabase.definitionDao().getDefinitionsByWordId(wordId);
        definitionsLiveData.observe(this, definitions::addAll);

        return definitions;
    }

    /**
     * Saves a definition to the database for a given word.
     *
     * @param word       The word for which to save the definition.
     * @param definition The definition to save.
     */
    private void saveDefinitionToDatabase(IO_Word word, IO_Definition definition) {
        long wordId = word.getId();
        definition.setWordId(wordId);

        runOnUiThread(() -> {
            new Thread(() -> {
                dictionaryDatabase.definitionDao().insertDefinition(definition);
            }).start();
        });
    }

    /**
     * Saves a word to the database.
     *
     * @param word The word to save.
     */
    private void saveWordToDatabase(IO_Word word) {
        IO_Word existingWord = dictionaryDatabase.wordDao().getWordByIdSync((int) word.getId());
        if (existingWord == null) {
            long wordId = dictionaryDatabase.wordDao().insertWord(word);
            word.setId(wordId);
        }
    }

    /**
     * Displays a toast message on the main thread.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(IO_DictionaryActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    /**
     * Handles the click event of a word, updating the UI to show word details and definitions.
     *
     * @param word The clicked word.
     */
    @Override
    public void onWordClick(IO_Word word) {
        IO_WordDetailFragment wordDetailFragment = (IO_WordDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flContentDic);
        if (wordDetailFragment != null) {
            wordDetailFragment.updateWordDetail(word);
        }

        List<IO_Definition> definitions = getDefinitionsFromDatabase(word);

        IO_DefinitionsAdapter definitionsAdapter = new IO_DefinitionsAdapter(definitions);

        RecyclerView recyclerView = findViewById(R.id.dictionaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(definitionsAdapter);
    }

    /**
     * Retrieves definitions from an API for a given word.
     *
     * @param word The word for which to fetch definitions.
     * @return The list of definitions from the API.
     */
    private List<IO_Definition> getDefinitionsFromApi(IO_Word word) {
        List<IO_Definition> definitions = new ArrayList<>();

        return definitions;
    }

    /**
     * Saves the search term to SharedPreferences.
     *
     * @param searchTerm The search term to save.
     */
    private void saveSearchTermToSharedPreferences(String searchTerm) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lastSearchTerm", searchTerm);
        editor.apply();
    }


    /**
     * Displays previously searched words.
     */
    private void displayPreviouslySearchedWords() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String lastSearchTerm = preferences.getString("lastSearchTerm", "");

        if (!TextUtils.isEmpty(lastSearchTerm)) {
            // Assume you have a TextView with id 'textViewLastSearchTerm' in your layout
            TextView textView = findViewById(R.id.searchWords);
            textView.setText(lastSearchTerm);
        }
    }

}
