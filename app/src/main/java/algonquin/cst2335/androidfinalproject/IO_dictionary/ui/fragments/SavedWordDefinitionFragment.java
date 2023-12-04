package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.SavedWordDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private int savedWordId;
    private TextView tvSavedWordDetail;
    private RecyclerView recyclerView;
    private SavedWordDefinitionAdapter definitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_recycleview_dictionary, container, false);

        View fragmentDefinition = inflater.inflate(R.layout.io_fragment_saved_word_definition, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the saved word ID from arguments
        savedWordId = getArguments().getInt(ARG_SAVED_WORD_ID);

        // Initialize and set up the DefinitionsAdapter
        definitionsAdapter = new SavedWordDefinitionAdapter();
        recyclerView.setAdapter(definitionsAdapter);

        // Make API request to get saved word definitions
        makeApiRequest(savedWordId);

        tvSavedWordDetail = fragmentDefinition.findViewById(R.id.tvSavedWordDetail);

        return view;
    }

    private void makeApiRequest(int savedWordId) {
        // Make an API request to get definitions for the saved word
        String apiUrl = "https://api.example.com/definitions?savedWordId=" + savedWordId;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    // Parse the JSON response and update the RecyclerView
                    List<Definition> definitions = parseJsonResponse(response);
                    updateRecyclerView(definitions);
                },
                error -> {
                    // Handle error if needed
                    Log.e("SavedWordDefFragment", "Error making API request: " + error.getMessage());
                }
        );

        // Add the request to the Volley queue
        DictionaryVolleySingleton.getInstance(requireContext()).addToRequestQueue(request);
    }

    private List<Definition> parseJsonResponse(JSONArray jsonResponse) {
        // Parse the JSON response and create a list of Definition objects
        // Implement your parsing logic based on the actual JSON structure
        List<Definition> definitions = new ArrayList<>();
        // Parse the JSON response and add definitions to the list
        return definitions;
    }

    private void updateRecyclerView(List<Definition> definitions) {
        definitionsAdapter.setDefinitions(definitions);
    }

    // Other methods as before...
}
