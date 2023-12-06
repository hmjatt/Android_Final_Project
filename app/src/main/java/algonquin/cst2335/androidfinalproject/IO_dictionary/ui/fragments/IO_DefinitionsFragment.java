package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;// DefinitionsFragment.java

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.IO_DefinitionsAdapter;
import algonquin.cst2335.androidfinalproject.R;

public class IO_DefinitionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private IO_DefinitionsAdapter definitionsAdapter;


    private TextView tvWord;
    private TextView tvPartOfSpeech;
    private TextView tvDefinitions;

    private TextView tvWordTitle;

    private TextView tvDefTitle;

    public IO_DefinitionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        tvWordTitle.setVisibility(View.GONE);
        tvWord.setVisibility(View.GONE);
//        tvPartOfSpeech.setVisibility(View.GONE);
        tvDefinitions.setVisibility(View.VISIBLE);
        tvDefTitle.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.wordRecyclerView);

        // Initialize the definitionsAdapter here
        definitionsAdapter = new IO_DefinitionsAdapter(new ArrayList<>());

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(definitionsAdapter);


        // You may also add a title or other UI elements as needed
    }

    public void updateDefinitions(List<IO_Definition> newDefinitions) {
        if (definitionsAdapter != null) {
            definitionsAdapter.updateData(newDefinitions);
        } else {
            // Handle the case where the adapter is null
            Log.e("DefinitionsFragment", "DefinitionsAdapter is null");
        }
    }
}
