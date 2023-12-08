package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordDefinitionAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordDefinitionBinding;

public class IO_SavedWordDefinitionFragment extends Fragment implements IO_SavedWordDefinitionAdapter.OnItemClickListener {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private long savedWordId;
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
        definitionsAdapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(IO_Definition definition) {
        // Handle item click here, for example, navigate to IO_SingleDefinitionFragment
        navigateToSingleDefinitionFragment(definition);
    }

    private void navigateToSingleDefinitionFragment(IO_Definition definition) {
        IO_SingleDefinitionFragment singleDefinitionFragment = IO_SingleDefinitionFragment.newInstance(definition.getWordId());

        // Use FragmentManager to replace the current fragment with the new one
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContentDic, singleDefinitionFragment)
                .addToBackStack(null)
                .commit();
    }
}
