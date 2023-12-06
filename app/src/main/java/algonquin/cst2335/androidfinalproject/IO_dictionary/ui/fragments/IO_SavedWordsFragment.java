package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.IO_DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

public class IO_SavedWordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private IO_SavedWordsAdapter savedWordsAdapter;

    private List<IO_Word> dictionaryPartOfSpeeches;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_io_fragment_saved_words, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.savedWordRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dictionaryPartOfSpeeches = new ArrayList<>();


        // Initialize and set up the SavedWordsAdapter
        savedWordsAdapter = new IO_SavedWordsAdapter(new ArrayList<>(), dictionaryPartOfSpeeches, savedWord -> {
            Log.d("SavedWordsFragment", "Saved Word clicked: " + savedWord.getWord());
            IO_SavedWordDefinitionFragment savedWordDetailFragment = new IO_SavedWordDefinitionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(IO_SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, (int) savedWord.getId());
            savedWordDetailFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordDetailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(savedWordsAdapter);

        // Make API request to get saved words
        makeApiRequest();

        return view;
    }

    private void makeApiRequest() {
        // Observe changes in the database using LiveData
        IO_DictionaryDatabase.getInstance(requireContext()).wordDao().getAllWords().observe(getViewLifecycleOwner(), savedWords -> {
            try {
                updateRecyclerView(savedWords, dictionaryPartOfSpeeches);
            } catch (Exception e) {
                Log.e("SavedWordsFragment", "Error updating RecyclerView: " + e.getMessage());
            }
        });
    }

    private void updateRecyclerView(List<IO_Word> savedWords, List<IO_Word> partOfSpeeches) {
        savedWordsAdapter.setSavedWords(savedWords, partOfSpeeches);
        Log.d("SavedWordsFragment", "RecyclerView Updated with " + savedWords.size() + " saved words");

        // Additional logs for debugging
        for (IO_Word word : savedWords) {
            Log.d("SavedWordsFragment", "Word: " + word.getWord() + ", ID: " + word.getId());
        }
    }
}
