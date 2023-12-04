// WordFragment.java

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
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.WordsAdapter;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.DummyData;

public class WordFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;

    public WordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_fragment_word, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use DummyData class to get dummy words
        List<Word> dummyWords = DummyData.getDummyWords();

        wordsAdapter = new WordsAdapter(dummyWords, word -> {
            // Handle item click, load WordDetailFragment with the selected word
            // Use a callback or communication method to communicate with the activity
            // and load the WordDetailFragment.
            // For now, use logs to test the click event.
            Log.d("WordFragment", "Word clicked: " + word.getWord());
        });
        recyclerView.setAdapter(wordsAdapter);

        return view;
    }
}
