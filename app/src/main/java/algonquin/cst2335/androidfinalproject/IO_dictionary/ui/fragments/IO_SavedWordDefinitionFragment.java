package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordDefinitionBinding;

import java.util.List;

public class IO_SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private long savedWordId; // Change from int to long

    private IoIoFragmentSavedWordDefinitionBinding binding;
    private IO_SavedWordDefinitionAdapter definitionsAdapter;

    private IO_DictionaryDatabase dictionaryDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = IoIoFragmentSavedWordDefinitionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve the saved word ID from arguments
        savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);

        // Initialize and set up the DefinitionsAdapter
        definitionsAdapter = new IO_SavedWordDefinitionAdapter();
        binding.savedDefinitionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.savedDefinitionRecyclerView.setAdapter(definitionsAdapter);

        // Initialize the dictionaryDatabase
        dictionaryDatabase = IO_DictionaryDatabase.getInstance(requireContext());

        // Set up the item click listener for the definitions RecyclerView
        definitionsAdapter.setOnItemClickListener(definition -> {
            // Load the single definition fragment for the clicked definition
            loadDefinitionsFromDatabase(savedWordId);
        });

        // Load definitions from the local database
        loadDefinitionsFromDatabase(savedWordId);

        return view;
    }

    private void loadDefinitionsFromDatabase(long savedWordId) {
        dictionaryDatabase.definitionDao().getDefinitionsByWordId(savedWordId).observe(getViewLifecycleOwner(), ioDefinitions -> {
            updateRecyclerView(ioDefinitions);
        });
    }

    private void updateRecyclerView(List<IO_Definition> definitions) {
        definitionsAdapter.setDefinitions(definitions);
    }
}
