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
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.DummyData;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.R;
public class SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private int savedWordId;
    private TextView tvSavedWordDetail;
    private RecyclerView recyclerView;
    private DefinitionsAdapter definitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_recycleview_dictionary, container, false);

        View fragmentDefinition = inflater.inflate(R.layout.io_fragment_saved_word_definition, container, false);


        // Initialize views
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use DummyData class to get dummy definitions
        List<Definition> dummyDefinitions = DummyData.getDummySavedWordDefinitions();

        // Retrieve the selected word from arguments
//        Word selectedSavedWord = getArguments().getParcelable(ARG_SAVED_WORD_ID);

        // Retrieve the saved word ID from arguments
//        savedWordId = getArguments().getInt(ARG_SAVED_WORD_ID);

        // Initialize and set up the DefinitionsAdapter
        definitionsAdapter = new DefinitionsAdapter(dummyDefinitions);
        recyclerView.setAdapter(definitionsAdapter);

        // Logging the dummy definitions
        for (Definition definition : dummyDefinitions) {
            Log.d("DummySavedData", "Dummy Saved Definition: " + definition.getDefinition());
        }

        tvSavedWordDetail = fragmentDefinition.findViewById(R.id.tvSavedWordDetail);

        // Use the saved word ID to retrieve the saved word from the database
        SavedWord savedWord = getSavedWordById(savedWordId);

        if (savedWord != null) {
            // Display the saved word in the TextView
            tvSavedWordDetail.setText(savedWord.getSavedWord());
        }

        return view;
    }

    // Implement methods to retrieve saved word and associated definitions from the database
    private SavedWord getSavedWordById(int savedWordId) {
        // Query the database using the saved word ID
        // Use the SavedWordDao to retrieve the saved word by ID
        return null; // Replace with actual database query
    }
}
