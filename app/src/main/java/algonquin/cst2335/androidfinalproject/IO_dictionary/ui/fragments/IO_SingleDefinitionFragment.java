package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.IO_dictionary.data.IO_DictionaryDatabase;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSingleDefinitionBinding;

public class IO_SingleDefinitionFragment extends Fragment {

    private static final String ARG_DEFINITION_TEXT = "definition_text";
    private static final String ARG_SAVED_WORD_ID = "saved_word_id";

    private IO_DictionaryDatabase dictionaryDatabase;

    public IO_SingleDefinitionFragment() {
        // Required empty public constructor
    }

    public static IO_SingleDefinitionFragment newInstance(String definitionText) {
        IO_SingleDefinitionFragment fragment = new IO_SingleDefinitionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DEFINITION_TEXT, definitionText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IoIoFragmentSingleDefinitionBinding binding =
                IoIoFragmentSingleDefinitionBinding.inflate(inflater, container, false);

        TextView tvDefinition = binding.tvSavedWordDetail;
        if (getArguments() != null) {
            String definitionText = getArguments().getString(ARG_DEFINITION_TEXT);
            long savedWordId = getArguments().getLong(ARG_SAVED_WORD_ID);

            tvDefinition.setText(definitionText);

            // Initialize the dictionaryDatabase
            dictionaryDatabase = IO_DictionaryDatabase.getInstance(requireContext());

            // Set up the delete button click listener
            binding.btnDeleteSavedDefinition.setOnClickListener(v -> deleteWordAndDefinition(savedWordId));
        }

        return binding.getRoot();
    }

    private void deleteWordAndDefinition(long savedWordId) {
        new Thread(() -> {
            dictionaryDatabase.wordDao().deleteDefinitionForWord(savedWordId, getArguments().getString(ARG_DEFINITION_TEXT));

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Definition deleted for word ID: " + savedWordId, Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
