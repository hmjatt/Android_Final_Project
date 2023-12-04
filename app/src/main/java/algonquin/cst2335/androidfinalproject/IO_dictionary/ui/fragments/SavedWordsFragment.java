package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
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
        View view = inflater.inflate(R.layout.io_recycleview_dictionary, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.dictionaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use DummyData class to get dummy saved words
        List<SavedWord> dummySavedWords = DummyData.getDummySavedWords();

        savedWordsAdapter = new SavedWordsAdapter(dummySavedWords, savedWord -> {
            Log.d("SavedWordsFragment", "Saved Word clicked: " + savedWord.getSavedWord());

            SavedWordDefinitionFragment savedWordDetailFragment = new SavedWordDefinitionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(SavedWordDefinitionFragment.ARG_SAVED_WORD_ID, savedWord.getId()); // Pass the ID
            savedWordDetailFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.flContent, savedWordDetailFragment)
                    .addToBackStack(null)
                    .commit();
        });


        recyclerView.setAdapter(savedWordsAdapter);

        return view;
    }
}
