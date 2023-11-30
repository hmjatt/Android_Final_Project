// Add this class to the existing package
package algonquin.cst2335.androidfinalproject.song.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.FragmentFavoriteSongsBinding;
import algonquin.cst2335.androidfinalproject.song.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.song.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.song.ui.SongApp;
import algonquin.cst2335.androidfinalproject.song.ui.adapters.FavoriteSongAdapter;

import java.util.List;

public class FavoriteSongsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteSongAdapter adapter;

    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoriteSongsBinding binding = FragmentFavoriteSongsBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_favorite_songs, container, false);

        // Initialize the adapter
        initializeAdapter();

        recyclerView = binding.recyclerViewFavoriteSongs;
        Button backButton = binding.btnBackFavoriteSongs;

        // Initialize the RecyclerView and set its layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter and set it to the RecyclerView
        adapter = new FavoriteSongAdapter();
        recyclerView.setAdapter(adapter);

        // Set up the back button click listener
        backButton.setOnClickListener(v -> navigateToArtistSearchFragment());

        // Load and display the list of favorite songs
        loadFavoriteSongs();

        return view;
    }

    private void loadFavoriteSongs() {
        // Access the Room database to retrieve the list of favorite songs
        FavoriteSongDatabase database = SongApp.database;
        List<FavoriteSong> favoriteSongs = database.favoriteSongDao().getAllFavoriteSongs();

        // Update the adapter with the list of favorite songs
        adapter.submitList(favoriteSongs);
    }

    private void navigateToArtistSearchFragment() {
        // Replace the current fragment with the ArtistSearchFragment
        ArtistSearchFragment artistSearchFragment = new ArtistSearchFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSf, artistSearchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initializeAdapter() {
     // Create an instance of FavoriteSongAdapter
                adapter = new FavoriteSongAdapter();;

        // Set the adapter to your RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
