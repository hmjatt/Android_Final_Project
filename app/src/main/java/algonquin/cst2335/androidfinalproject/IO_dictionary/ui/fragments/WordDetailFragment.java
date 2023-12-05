package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.R;
public class WordDetailFragment extends Fragment {

    public static final String ARG_SELECTED_WORD = "selected_word";

    private RecyclerView recyclerView;
    private DefinitionsAdapter definitionsAdapter;

    public WordDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_activity_dictionay, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.dictionaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the selected word from arguments
        Word selectedWord = getArguments().getParcelable(ARG_SELECTED_WORD);

        // Display the selected word in a TextView for testing
        TextView tvWordDetail = view.findViewById(R.id.tvWordDetail);

        if (tvWordDetail != null) {
            tvWordDetail.setText(selectedWord != null ? selectedWord.getWord() : "No word selected");
        } else {
            // Log a message or handle the case where tvWordDetail is not found
            Log.e("WordDetailFragment", "TextView tvWordDetail not found in the layout");
        }

        // Initialize DefinitionsAdapter with the list of definitions from the selected word
        List<Definition> definitions = selectedWord != null ? selectedWord.getDefinitions() : null;
        definitionsAdapter = new DefinitionsAdapter(definitions);
        recyclerView.setAdapter(definitionsAdapter);

        return view;
    }

}
