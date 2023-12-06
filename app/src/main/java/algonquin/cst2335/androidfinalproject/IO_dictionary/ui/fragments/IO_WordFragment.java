package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;// WordFragment.java

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.R;

public class IO_WordFragment extends Fragment {

    private TextView tvWord;
    private TextView tvPartOfSpeech;
    private TextView tvDefinitions;

    private TextView tvWordTitle;

    private TextView tvDefTitle;

    public IO_WordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.io_io_fragment_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        tvWordTitle = view.findViewById(R.id.wordPlaceholder);
        tvWord = view.findViewById(R.id.textViewWords);
        tvPartOfSpeech = view.findViewById(R.id.textViewPartOfSpeech);
        tvDefTitle = view.findViewById(R.id.definitionPlaceholder);
        tvDefinitions = view.findViewById(R.id.tvWordDetail);

        tvDefinitions.setVisibility(View.GONE);
        tvDefTitle.setVisibility(View.GONE);

        // Retrieve data from arguments (assumed to be passed from the activity)
        Bundle args = getArguments();
        if (args != null) {
            displayWordData(
                    args.getString("word"),
                    args.getString("partOfSpeech"),
                    args.getStringArrayList("definitions")
            );
        }
    }

    private void displayWordData(String word, String partOfSpeech, List<String> definitions) {
        // Display word details in the fragment
        if (tvWord != null) {
            tvWord.setText(word);
        }

        if (tvPartOfSpeech != null) {
            tvPartOfSpeech.setText(partOfSpeech);
        }

        // Build a string with definitions and display it
        StringBuilder definitionsStringBuilder = new StringBuilder();
        if (definitions != null) {
            for (String definition : definitions) {
                definitionsStringBuilder.append("• ").append(definition).append("\n");
            }
        }

        if (tvDefinitions != null) {
            tvDefinitions.setText(definitionsStringBuilder.toString());
        }
    }
}
