package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordsBinding;

public class IO_SavedWordsFragment extends Fragment implements IO_SavedWordsAdapter.OnSavedWordClickListener {

    private IoIoFragmentSavedWordsBinding binding;
    private IO_SavedWordsAdapter savedWordsAdapter;
    private List<IO_Word> dictionaryPartOfSpeeches;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = IoIoFragmentSavedWordsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        // Initialize RecyclerView
        binding.savedWordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dictionaryPartOfSpeeches = new ArrayList<>();

        savedWordsAdapter = new IO_SavedWordsAdapter(new ArrayList<>(), this);

        // Set the adapter to the RecyclerView
        binding.savedWordRecyclerView.setAdapter(savedWordsAdapter);

        // Make API request to get saved words
        makeApiRequest();

        return view;
    }

    private void makeApiRequest() {
        IO_DictionaryDatabase.getInstance(requireContext()).wordDao().getAllWords().observe(getViewLifecycleOwner(), savedWords -> {
            try {
                updateRecyclerView(savedWords);
            } catch (Exception e) {
                // Handle error
            }
        });
    }

    private void updateRecyclerView(List<IO_Word> savedWords) {
        savedWordsAdapter.setSavedWords(savedWords);
    }

    // Implement the OnSavedWordClickListener interface method
    @Override
    public void onSavedWordClick(IO_Word savedWord) {
        // Handle saved word click here
        // You can navigate to the details fragment or perform any desired action

        // For example, navigate to a new fragment to show definitions
        navigateToDefinitionsFragment(savedWord);
    }

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
                .replace(R.id.flContent, definitionFragment)
                .addToBackStack(null)
                .commit();
    }
}
