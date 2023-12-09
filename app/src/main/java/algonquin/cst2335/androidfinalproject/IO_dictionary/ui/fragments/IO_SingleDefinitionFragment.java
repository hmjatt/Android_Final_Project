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
import java.util.concurrent.atomic.AtomicReference;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SingleDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSingleDefinitionBinding;

/**
 * Fragment to display the single definition for a saved word.
 *
 * @Author Iuliia Obukhova
 * @Version 1.0
 */
public class IO_SingleDefinitionFragment extends Fragment {

    private static final String DIALOG_TITLE = "Confirm Deletion";
    private static final String DIALOG_MESSAGE = "Are you sure you want to delete this definition?";

    private static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private IO_DictionaryDatabase dictionaryDatabase;
    private RecyclerView recyclerView;
    private IO_SingleDefinitionAdapter definitionAdapter;
    private List<String> definitions = new ArrayList<>();
    private AtomicReference<String> lastDeletedDefinition = new AtomicReference<>();

    /**
     * Default constructor for IO_SingleDefinitionFragment.
     */
    public IO_SingleDefinitionFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of IO_SingleDefinitionFragment.
     *
     * @param savedWordId The ID of the saved word.
     * @return A new instance of IO_SingleDefinitionFragment.
     */
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
        definitionAdapter = new IO_SingleDefinitionAdapter(definitions, position -> onDeleteDefinitionClick(position));

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(definitionAdapter);

        if (getArguments() != null) {
            long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);
            loadDefinitionsFromDatabase(savedWordId);
        }

        return binding.getRoot();
    }

    /**
     * Load definitions from the database and update the UI.
     *
     * @param savedWordId The ID of the saved word.
     */
    private void loadDefinitionsFromDatabase(long savedWordId) {
        dictionaryDatabase.wordDao().getDefinitionsByWordId(savedWordId).observe(getViewLifecycleOwner(), ioDefinitions -> {
            List<String> definitionTexts = new ArrayList<>();
            for (IO_Definition ioDefinition : ioDefinitions) {
                definitionTexts.add(ioDefinition.getDefinition());
            }
            updateRecyclerView(definitionTexts);
        });
    }

    /**
     * Update the RecyclerView with the provided list of definitions.
     *
     * @param definitions The list of definitions to display.
     */
    private void updateRecyclerView(List<String> definitions) {
        this.definitions.clear();
        this.definitions.addAll(definitions);
        definitionAdapter.notifyDataSetChanged();
    }

    /**
     * Handle the click event when a definition is to be deleted.
     *
     * @param position The position of the definition in the list.
     */
    private void onDeleteDefinitionClick(int position) {
        if (position >= 0 && position < definitions.size()) {
            String definitionToDelete = definitions.get(position);
            showDeleteDefinitionDialog(definitionToDelete);
        }
    }

    /**
     * Delete a definition for a specific saved word.
     *
     * @param savedWordId        The ID of the saved word.
     * @param definitionToDelete The definition to delete.
     */
    private void deleteDefinitionForWord(long savedWordId, String definitionToDelete) {
        new Thread(() -> {
            int deletedRows = dictionaryDatabase.definitionDao().deleteDefinitionForWord(savedWordId, definitionToDelete);

            requireActivity().runOnUiThread(() -> {
                if (deletedRows > 0) {
                    lastDeletedDefinition.set(definitionToDelete);
                    loadDefinitionsFromDatabase(savedWordId);
                    showUndoSnackbar();
                } else {
                    // Show failure message
                }
            });
        }).start();
    }

    /**
     * Undo the deletion of the last definition.
     */
    private void undoDefinitionDeletion() {
        if (getArguments() != null) {
            long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);
            String lastDeleted = lastDeletedDefinition.get();
            if (lastDeleted != null) {
                new Thread(() -> {
                    IO_Definition definitionToInsert = new IO_Definition(lastDeleted);
                    definitionToInsert.setWordId(savedWordId);
                    dictionaryDatabase.definitionDao().insertDefinition(definitionToInsert);
                    requireActivity().runOnUiThread(() -> loadDefinitionsFromDatabase(savedWordId));
                }).start();
            }
        }
    }

    /**
     * Display a Snackbar with an option to undo the last definition deletion.
     */
    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(requireView(), "Definition DELETED", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> undoDefinitionDeletion());
        snackbar.show();
    }

    /**
     * Display a dialog to confirm the deletion of a definition.
     *
     * @param definitionToDelete The definition to be deleted.
     */
    private void showDeleteDefinitionDialog(String definitionToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(DIALOG_TITLE);
        builder.setMessage(DIALOG_MESSAGE);
        builder.setPositiveButton("YES", (dialog, which) -> deleteDefinitionForWord(getArguments().getLong(ARG_SAVED_WORD_ID), definitionToDelete));
        builder.setNegativeButton("NO", (dialog, which) -> { /* Do nothing, as the user chose not to delete */ });
        builder.show();
    }
}
