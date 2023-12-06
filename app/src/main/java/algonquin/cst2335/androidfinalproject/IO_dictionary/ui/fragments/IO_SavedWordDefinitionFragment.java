package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

    private IO_DictionaryDatabase dictionaryDatabase; // Add this line

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

        // Initialize the dictionaryDatabase
        dictionaryDatabase = IO_DictionaryDatabase.getInstance(requireContext()); // Add this line

        // Load definitions from the local database
        loadDefinitionsFromDatabase(savedWordId);


        // Implement delete button logic here (for example, in a button click listener)
        Button deleteButton = view.findViewById(R.id.btnDeleteSavedDefinition);
        deleteButton.setOnClickListener(v -> {
            // Log statement to check if the delete button click is registered
            Log.d("DictionaryActivity", "Delete button clicked");

            // Delete the word and its definitions from the database
            deleteWordAndDefinitions(savedWordId);
        });


        // Log statement to check if the delete button is registered
        Log.d("DictionaryActivity", "Delete button is registered");

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

    private void deleteWordAndDefinitions(int savedWordId) {
        new Thread(() -> {
            // Delete the definitions for the selected word
            dictionaryDatabase.wordDao().deleteDefinitionsForWord(savedWordId);

            // Check if all definitions are deleted, and delete the word if true
            dictionaryDatabase.wordDao().deleteWordIfNoDefinitions(savedWordId);

            // Show a Toast indicating that the definitions have been deleted
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Definitions deleted for word ID: " + savedWordId, Toast.LENGTH_SHORT).show();
            });
        }).start();
    }


    private void updateRecyclerView(List<IO_Definition> definitions) {
        definitionsAdapter.setDefinitions(definitions);
    }


}
