package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class SavedWordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SavedWordsAdapter savedWordsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_activity_dictionay, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize and set up the SavedWordsAdapter
        savedWordsAdapter = new SavedWordsAdapter(new ArrayList<>(), savedWord -> {

            Log.d("SavedWordsFragment", "Saved Word clicked: " + savedWord.getSavedWord());

            SavedWordDefinitionFragment savedWordDetailFragment = new SavedWordDefinitionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
            savedWordDetailFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordDetailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(savedWordsAdapter);

        // Make API request to get saved words
        makeApiRequest();

        return view;
    }

    private void makeApiRequest() {
        // Make an API request to get the list of saved words
        String apiUrl = "https://api.example.com/saved-words";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    // Parse the JSON response and update the RecyclerView
                    List<SavedWord> savedWords = parseJsonResponse(response);
                    updateRecyclerView(savedWords);
                },
                error -> {
                    // Handle error if needed
                    Log.e("SavedWordsFragment", "Error making API request: " + error.getMessage());
                }
        );

        // Add the request to the Volley queue
        DictionaryVolleySingleton.getInstance(requireContext()).addToRequestQueue(request);
    }

    private List<SavedWord> parseJsonResponse(JSONArray jsonResponse) {
        // Parse the JSON response and create a list of SavedWord objects
        // Implement your parsing logic based on the actual JSON structure
        List<SavedWord> savedWords = new ArrayList<>();
        // Parse the JSON response and add saved words to the list
        return savedWords;
    }

    private void updateRecyclerView(List<SavedWord> savedWords) {
        savedWordsAdapter.setSavedWords(savedWords);
    }
}
