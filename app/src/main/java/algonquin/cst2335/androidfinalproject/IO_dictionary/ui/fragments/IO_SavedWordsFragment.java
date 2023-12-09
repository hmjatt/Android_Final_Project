package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordsBinding;

/**
 * Fragment to display the list of saved words.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
public class IO_SavedWordsFragment extends Fragment implements IO_SavedWordsAdapter.OnSavedWordClickListener {

    private IoIoFragmentSavedWordsBinding binding;
    private IO_SavedWordsAdapter savedWordsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = IoIoFragmentSavedWordsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize RecyclerView
        binding.savedWordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize and set up the adapter
        savedWordsAdapter = new IO_SavedWordsAdapter(new ArrayList<>(), this);
        binding.savedWordRecyclerView.setAdapter(savedWordsAdapter);

        // Make API request to get saved words
        makeApiRequest();

        return view;
    }

    /**
     * Make an API request to get saved words from the database.
     */
    private void makeApiRequest() {
        IO_DictionaryDatabase.getInstance(requireContext()).wordDao().getAllWords().observe(getViewLifecycleOwner(), savedWords -> {
            try {
                updateRecyclerView(savedWords);
            } catch (Exception e) {
                // Handle error
            }
        });
    }

    /**
     * Update the RecyclerView with the provided list of saved words.
     *
     * @param savedWords The list of saved words to display.
     */
    private void updateRecyclerView(List<IO_Word> savedWords) {
        savedWordsAdapter.setSavedWords(savedWords);
    }

    /**
     * Handle the click event when a saved word is clicked.
     *
     * @param savedWord The clicked saved word.
     */
    @Override
    public void onSavedWordClick(IO_Word savedWord) {
        // Navigate to a new fragment to show definitions
        navigateToDefinitionsFragment(savedWord);
    }

    /**
     * Navigate to the fragment that will display definitions for the selected saved word.
     *
     * @param savedWord The selected saved word.
     */
    private void navigateToDefinitionsFragment(IO_Word savedWord) {
        // Create a new instance of the fragment that will display definitions
        IO_SavedWordDefinitionFragment definitionFragment = new IO_SavedWordDefinitionFragment();

        // Pass the saved word ID or other necessary data to the fragment
        Bundle args = new Bundle();
        args.putLong(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId());
        definitionFragment.setArguments(args);

        // Use FragmentManager to replace the current fragment with the new one
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContentDic, definitionFragment)
                .addToBackStack(null)
                .commit();
    }
}
