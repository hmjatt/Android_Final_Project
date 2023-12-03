package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentFavoriteSongsBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;

/**
 * Fragment to display a list of favorite songs and handle song deletion and restoration.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class FavoriteSongsFragment extends Fragment implements FavoriteSongDetailFragment.OnUndoDeleteListener {

    private FavoriteSongAdapter favoriteSongAdapter;
    private List<FavoriteSong> favoriteSongsList;
    private FavoriteSongDatabase database;

    private RecyclerView recyclerView;

    /**
     * Default constructor for the FavoriteSongsFragment.
     */
    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HmFragmentFavoriteSongsBinding binding = HmFragmentFavoriteSongsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerViewFs;

        // Initialize Room database
        database = FavoriteSongDatabase.getInstance(requireContext());

        favoriteSongsList = new ArrayList<>();

        // Set up RecyclerView and adapter
        favoriteSongAdapter = new FavoriteSongAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteSongAdapter);

        // Use Executor to fetch favorite songs in the background
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<FavoriteSong> result = database.favoriteSongDao().getFavoriteSongs();
            requireActivity().runOnUiThread(() -> {
                // Update the UI with the fetched data
                favoriteSongsList = result;
                favoriteSongAdapter.setFavoriteSongs(favoriteSongsList);
                favoriteSongAdapter.notifyDataSetChanged();
            });
        });

        // Set up item click listener to navigate to FavoriteSongDetailFragment
        favoriteSongAdapter.setOnItemClickListener(favoriteSong -> {
            FavoriteSongDetailFragment fragment = new FavoriteSongDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable("favoriteSong", favoriteSong);
            fragment.setArguments(args);
            fragment.setUndoDeleteListener(this); // Set the listener in the detail fragment

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerSf, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    // Implementation of the OnUndoDeleteListener interface
    @Override
    public void onUndoDelete() {
        // Reload the list of favorite songs when undo is performed
        reloadFavoriteSongs();
    }

    // Method to reload the list of favorite songs
    private void reloadFavoriteSongs() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<FavoriteSong> result = database.favoriteSongDao().getFavoriteSongs();
            requireActivity().runOnUiThread(() -> {
                // Update the UI with the fetched data after undo
                favoriteSongsList = result;
                favoriteSongAdapter.setFavoriteSongs(favoriteSongsList);
                favoriteSongAdapter.notifyDataSetChanged();

                // Show a Toast message when a song is restored
                Toast.makeText(requireContext(), R.string.song_restored, Toast.LENGTH_SHORT).show();
            });
        });
    }
}
