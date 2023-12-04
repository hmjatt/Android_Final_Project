package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.R;

public class SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private int savedWordId;
    private TextView tvSavedWord;
    private RecyclerView recyclerView;
    private DefinitionsAdapter definitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_recycleview_dictionary, container, false);

        View fragmentDefinition = inflater.inflate(R.layout.io_fragment_word_definition, container, false);


        // Get the saved word ID from arguments
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_SAVED_WORD_ID)) {
            savedWordId = arguments.getInt(ARG_SAVED_WORD_ID);
        }

        // Initialize views
        tvSavedWord = fragmentDefinition.findViewById(R.id.tvWordDetail);
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use the saved word ID to retrieve the saved word from the database
        SavedWord savedWord = getSavedWordById(savedWordId);

        if (savedWord != null) {
            // Display the saved word in the TextView
            tvSavedWord.setText(savedWord.getSavedWord());

            // Use the saved word ID to retrieve the definitions associated with it
            List<Definition> savedDefinitions = getDefinitionsForSavedWord(savedWordId);

            // Initialize and set up the DefinitionsAdapter
            definitionsAdapter = new DefinitionsAdapter(savedDefinitions);
            recyclerView.setAdapter(definitionsAdapter);
        }

        return view;
    }

    // Implement methods to retrieve saved word and associated definitions from the database
    private SavedWord getSavedWordById(int savedWordId) {
        // Query the database using the saved word ID
        // Use the SavedWordDao to retrieve the saved word by ID
        return null; // Replace with actual database query
    }

    private List<Definition> getDefinitionsForSavedWord(int savedWordId) {
        // Query the database using the saved word ID
        // Use the DefinitionDao or a similar interface to retrieve the definitions associated with the saved word
        return null; // Replace with actual database query
    }
}
