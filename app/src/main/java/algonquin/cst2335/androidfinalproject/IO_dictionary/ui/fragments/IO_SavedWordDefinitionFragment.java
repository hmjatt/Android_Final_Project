package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.R;

import java.util.List;

public class IO_SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private int savedWordId;

    private RecyclerView recyclerView;
    private IO_SavedWordDefinitionAdapter definitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_io_fragment_saved_word_definition, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.savedDefinitionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the saved word ID from arguments
        savedWordId = getArguments().getInt(ARG_SAVED_WORD_ID);

        // Initialize and set up the DefinitionsAdapter
        definitionsAdapter = new IO_SavedWordDefinitionAdapter();
        recyclerView.setAdapter(definitionsAdapter);

        // Load definitions from the local database
        loadDefinitionsFromDatabase(savedWordId);

        return view;
    }

    private void loadDefinitionsFromDatabase(int savedWordId) {
        // Retrieve the IO_Word object from the database using the savedWordId
        IO_DictionaryDatabase.getInstance(requireContext()).wordDao().getWordById(savedWordId).observe(getViewLifecycleOwner(), ioWord -> {
            if (ioWord != null) {
                // Get the definitions from the IO_Word object
                List<IO_Definition> definitions = ioWord.getWordDetailsList();
                // Update the RecyclerView with the definitions
                updateRecyclerView(definitions);
            }
        });
    }

    private void updateRecyclerView(List<IO_Definition> definitions) {
        definitionsAdapter.setDefinitions(definitions);
    }
}
