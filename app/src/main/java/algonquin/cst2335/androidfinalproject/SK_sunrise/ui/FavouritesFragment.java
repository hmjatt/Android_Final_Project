package algonquin.cst2335.androidfinalproject.SK_sunrise.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.SK_sunrise.adapter.SunriseSunsetAdapter;

public class FavouritesFragment extends Fragment {

    private RecyclerView recyclerViewFavorites;
    private SunriseSunsetAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sk_fragment_favourites, container, false);

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the adapter with an empty list (for now)
        adapter = new SunriseSunsetAdapter(new ArrayList<>());
        recyclerViewFavorites.setAdapter(adapter);

        // Load and display the favorite locations from the database
        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        // Implement the logic to fetch the favorite locations from the database
        // Update the adapter with the fetched locations
    }
}
