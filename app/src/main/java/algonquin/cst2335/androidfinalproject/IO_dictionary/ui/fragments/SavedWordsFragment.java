// io_dictionary.ui.fragments.SavedWordsFragment
package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import algonquin.cst2335.androidfinalproject.R;

public class SavedWordsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_fragment_saved_words, container, false);

        // Add code to display saved words in a RecyclerView

        return view;
    }
}
