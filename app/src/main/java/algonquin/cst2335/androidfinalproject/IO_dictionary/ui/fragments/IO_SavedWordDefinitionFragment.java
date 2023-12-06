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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.R;

public class IO_SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private int savedWordId;

    private TextView tvSavedWordDetail;
    private RecyclerView recyclerView;
    private IO_SavedWordDefinitionAdapter definitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_io_fragment_saved_word_definition, container, false);


        // Initialize views
        recyclerView = view.findViewById(R.id.savedDefinitionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedWordId = getArguments().getInt(ARG_SAVED_WORD_ID);
        IO_Word savedWord = IO_DictionaryDatabase.getInstance(requireContext()).wordDao().getWordById(savedWordId);

        // Initialize and set up the DefinitionsAdapter
        definitionsAdapter = new IO_SavedWordDefinitionAdapter();
        recyclerView.setAdapter(definitionsAdapter);

        // Make API request to get saved word definitions
        makeApiRequest(savedWord);

//        tvSavedWordDetail = fragmentDefinition.findViewById(R.id.tvSavedWordDetail);

        return view;
    }

    private void makeApiRequest(IO_Word savedWordId) {

        Log.d("SavedWordDefFragment", "Making API request for savedWordId: " + savedWordId);

        // Make an API request to get definitions for the saved word
//        savedWordId = getArguments().getInt(ARG_SAVED_WORD_ID);
        // Make API request to get saved word definitions
        makeApiRequest(savedWordId);

    }

    private List<IO_Definition> parseJsonResponse(JSONArray jsonResponse) {
        // Parse the JSON response and create a list of Definition objects
        // Implement your parsing logic based on the actual JSON structure
        List<IO_Definition> definitions = new ArrayList<>();
        // Parse the JSON response and add definitions to the list
        return definitions;
    }

}
