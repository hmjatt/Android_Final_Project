// SavedWordsFragment.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.SavedWordsAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.DummyData;

public class SavedWordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SavedWordsAdapter savedWordsAdapter;

    public SavedWordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_activity_dictionay, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

// Use DummyData class to get dummy saved words
        List<SavedWord> dummySavedWords = DummyData.getDummySavedWords();

        savedWordsAdapter = new SavedWordsAdapter(dummySavedWords, savedWord -> {
            // Handle item click, load SavedWordDetailFragment with the selected saved word
            // Use a callback or communication method to communicate with the activity
            // and load the SavedWordDetailFragment.
            // For now, use logs to test the click event.
            Log.d("SavedWordsFragment", "Saved Word clicked: " + savedWord.getSavedWord());
        });
        recyclerView.setAdapter(savedWordsAdapter);

        return view;
    }
}
