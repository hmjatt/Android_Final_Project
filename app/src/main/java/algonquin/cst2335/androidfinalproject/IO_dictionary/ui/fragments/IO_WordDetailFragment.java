package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DefinitionDao;
import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_WordDao;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.R;

public class IO_WordDetailFragment extends Fragment {

    private IO_DictionaryDatabase database;

    private TextView tvWordDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_io_fragment_word_definition, container, false);

        TextView tvWordDetail = view.findViewById(R.id.tvWordDetail);

        // Retrieve the selected word from the arguments
        IO_Word selectedWord = getArguments().getParcelable("selectedWord");

        if (tvWordDetail != null && selectedWord != null) {
            tvWordDetail.setText(selectedWord.getWord());
        }

        // Load definitions using the selected word
        loadDefinitions(selectedWord);

        return view;
    }

    public void updateWordDetail(IO_Word word) {
        if (tvWordDetail != null) {
            tvWordDetail.setText(word != null ? word.getWord() : "No word selected");
        } else {
            Log.e("WordDetailFragment", "TextView tvWordDetail not found in the layout");
        }
    }

    private void loadDefinitions(IO_Word selectedWord) {
        // Initialize the database
        database = IO_DictionaryDatabase.getInstance(requireContext());

        // Get the DAOs
        IO_WordDao wordDao = database.wordDao();
        IO_DefinitionDao definitionDao = database.definitionDao();

        // Retrieve definitions using the word ID
        LiveData<List<IO_Definition>> definitionsLiveData = definitionDao.getDefinitionsByWordId(selectedWord.getId());
        definitionsLiveData.observe(getViewLifecycleOwner(), new Observer<List<IO_Definition>>() {
            @Override
            public void onChanged(List<IO_Definition> definitions) {
                // Set up the RecyclerView and adapter for definitions
                RecyclerView recyclerView = requireView().findViewById(R.id.dictionaryRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                IO_DefinitionsAdapter definitionsAdapter = new IO_DefinitionsAdapter(definitions);
                recyclerView.setAdapter(definitionsAdapter);
            }
        });
    }


}
