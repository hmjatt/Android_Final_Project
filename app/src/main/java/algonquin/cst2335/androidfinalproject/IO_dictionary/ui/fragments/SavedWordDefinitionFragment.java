package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.R;

public class SavedWordDefinitionFragment extends Fragment {

    public static final String ARG_SELECTED_SAVED_WORD = "selected_saved_word";

    public SavedWordDefinitionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_fragment_saved_word_definition, container, false);

        // Retrieve the selected saved word from arguments
        SavedWord selectedSavedWord = getArguments().getParcelable(ARG_SELECTED_SAVED_WORD);

        // Display the selected saved word in a TextView for testing
        TextView tvSavedWordDetail = view.findViewById(R.id.tvSavedWordDetail);
        tvSavedWordDetail.setText(selectedSavedWord != null ? selectedSavedWord.getSavedWord() : "No saved word selected");

        return view;
    }
}
