package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.utils.DictionaryVolleySingleton;
import algonquin.cst2335.androidfinalproject.R;

// WordFragment.java

public class WordFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;
    private EditText searchEditText;

    public WordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_activity_dictionay, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.dictionaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the WordsAdapter with a dummy listener for now
        wordsAdapter = new WordsAdapter(new ArrayList<>(), word -> {
            // Handle word click, you can replace this with your logic
            Log.d("WordFragment", "Word clicked: " + word.getWord());
        });

        recyclerView.setAdapter(wordsAdapter);

        return view;
    }
}
