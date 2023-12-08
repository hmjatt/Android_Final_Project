package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SingleDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSingleDefinitionBinding;

public class IO_SingleDefinitionFragment extends Fragment {

    private static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private IO_DictionaryDatabase dictionaryDatabase;
    private RecyclerView recyclerView;
    private IO_SingleDefinitionAdapter definitionAdapter;
    private List<String> definitions = new ArrayList<>();

    // Variable to store the last deleted definition
    private String lastDeletedDefinition;

    public IO_SingleDefinitionFragment() {
        // Required empty public constructor
    }

    public static IO_SingleDefinitionFragment newInstance(long savedWordId) {
        IO_SingleDefinitionFragment fragment = new IO_SingleDefinitionFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_SAVED_WORD_ID, savedWordId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IoIoFragmentSingleDefinitionBinding binding =
                IoIoFragmentSingleDefinitionBinding.inflate(inflater, container, false);

        recyclerView = binding.savedDefinitionRecyclerView;
        dictionaryDatabase = IO_DictionaryDatabase.getInstance(requireContext());

        // Set up the adapter for definitions
        definitionAdapter = new IO_SingleDefinitionAdapter(definitions, position -> {
            // Handle delete button click here
            onDeleteDefinitionClick(position);
        });

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(definitionAdapter);

        if (getArguments() != null) {
            long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);
            loadDefinitionsFromDatabase(savedWordId);
        }

        return binding.getRoot();
    }

    private void loadDefinitionsFromDatabase(long savedWordId) {
        dictionaryDatabase.wordDao().getDefinitionsByWordId(savedWordId).observe(getViewLifecycleOwner(), ioDefinitions -> {
            // Extract the definition texts from the list of IO_Definition objects
            List<String> definitionTexts = new ArrayList<>();
            for (IO_Definition ioDefinition : ioDefinitions) {
                definitionTexts.add(ioDefinition.getDefinition());
            }

            // Update the RecyclerView with the extracted definition texts
            updateRecyclerView(definitionTexts);
        });
    }


    private void updateRecyclerView(List<String> definitions) {
        this.definitions.clear();
        this.definitions.addAll(definitions);
        definitionAdapter.notifyDataSetChanged();
    }

    private void onDeleteDefinitionClick(int position) {
        if (position >= 0 && position < definitions.size()) {
            String definitionToDelete = definitions.get(position);
            showDeleteDefinitionDialog(definitionToDelete);
        }
    }

    private void deleteWordAndDefinition(String definitionToDelete) {
        new Thread(() -> {
            if (getArguments() != null) {
                long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);

                // Store the last deleted definition
                lastDeletedDefinition = definitionToDelete;

                // Change to int return type
                int deletedRows = dictionaryDatabase.wordDao().deleteDefinitionForWord(savedWordId, definitionToDelete);

                requireActivity().runOnUiThread(() -> {
                    // Check the number of deleted rows if needed
                    // For example, you can show a message based on the result
                    if (deletedRows > 0) {
                        loadDefinitionsFromDatabase(savedWordId);
                        showUndoSnackbar();
                    } else {
                        // Show failure message
                    }
                });
            }
        }).start();
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(requireView(), "Definition DELETED", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> undoDefinitionDeletion());
        snackbar.show();
    }

    private void undoDefinitionDeletion() {
        if (getArguments() != null) {
            long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);

            // Check if there is a last deleted definition to undo
            if (lastDeletedDefinition != null) {
                // Insert the last deleted definition back to the database
                new Thread(() -> {
                    IO_Definition definitionToInsert = new IO_Definition(lastDeletedDefinition);
                    definitionToInsert.setWordId(savedWordId);
                    dictionaryDatabase.definitionDao().insertDefinition(definitionToInsert);

                    // Load definitions again to update the UI
                    requireActivity().runOnUiThread(() -> loadDefinitionsFromDatabase(savedWordId));
                }).start();
            }
        }
    }


    private void showDeleteDefinitionDialog(String definitionToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this definition?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            // Proceed with the deletion
            deleteWordAndDefinition(definitionToDelete);
        });
        builder.setNegativeButton("NO", (dialog, which) -> {
            // Do nothing, as the user chose not to delete
        });
        builder.show();
    }


}
