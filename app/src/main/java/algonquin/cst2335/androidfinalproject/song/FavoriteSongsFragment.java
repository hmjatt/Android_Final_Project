package algonquin.cst2335.androidfinalproject.song;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.song.FavoriteSong;
import algonquin.cst2335.androidfinalproject.song.FavoriteSongDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.song.FavoriteSong;
import algonquin.cst2335.androidfinalproject.song.FavoriteSongDatabase;

public class FavoriteSongsFragment extends Fragment {

    private FavoriteSongDatabase database;
    private RecyclerView recyclerView;
    private FavoriteSongsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_songs, container, false);

        // Initialize Room database
        database = SongApp.database;

        // Initialize RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.recyclerViewFavoriteSongs);
        adapter = new FavoriteSongsAdapter();

        // Set up RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Load and display favorite songs
        loadFavoriteSongs();

        return view;
    }

    private void loadFavoriteSongs() {
        // Execute the AsyncTask to load favorite songs
        Executors.newSingleThreadExecutor().execute(() -> {
            // Perform database operation on a background thread
            List<FavoriteSong> favoriteSongs = database.favoriteSongDao().getAllFavoriteSongs();

            // Update the adapter with the list of favorite songs on the main thread
            requireActivity().runOnUiThread(() -> {
                adapter.setFavoriteSongs(favoriteSongs);
                adapter.notifyDataSetChanged();
            });
        });
    }
}
