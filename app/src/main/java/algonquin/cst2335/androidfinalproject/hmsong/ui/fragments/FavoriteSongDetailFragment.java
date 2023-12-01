package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

// Inside FavoriteSongDetailFragment
public class FavoriteSongDetailFragment extends Fragment {

    private FavoriteSong favoriteSong;

    // Add necessary views

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hm_item_favorite_song, container, false);

        // Initialize views and set data

        // Set up the "Delete" button click listener
        Button deleteButton = view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(v -> deleteFavoriteSong());

        return view;
    }

    private void deleteFavoriteSong() {
        // Implement logic to remove the selected song from favorites and update UI
        // Use AsyncTask or other background task to perform database operation
    }
}
