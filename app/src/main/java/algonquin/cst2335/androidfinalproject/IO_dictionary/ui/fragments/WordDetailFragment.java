package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.R;

public class WordDetailFragment extends Fragment {

    public static final String ARG_SELECTED_WORD = "selected_word";

    public WordDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_fragment_word_definition, container, false);

        // Retrieve the selected word from arguments
        Word selectedWord = getArguments().getParcelable(ARG_SELECTED_WORD);

        // Display the selected word in a TextView for testing
        TextView tvWordDetail = view.findViewById(R.id.tvWordDetail);
        tvWordDetail.setText(selectedWord != null ? selectedWord.getWord() : "No word selected");

        return view;
    }
}
