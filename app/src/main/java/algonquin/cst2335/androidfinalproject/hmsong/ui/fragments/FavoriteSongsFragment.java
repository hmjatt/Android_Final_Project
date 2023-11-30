package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;  // Import the correct class
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentFavoriteSongsBinding;

public class FavoriteSongsFragment extends Fragment {

    private FavoriteSongAdapter favoriteSongAdapter;
    private List<FavoriteSong> favoriteSongs;
    private FavoriteSongDatabase database;

    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HmFragmentFavoriteSongsBinding binding = HmFragmentFavoriteSongsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewFavoriteSongs;

        // Initialize Room database
        database = FavoriteSongDatabase.getInstance(requireContext());

        // Retrieve favorite songs from the database
        favoriteSongs = database.favoriteSongDao().getFavoriteSongs();


        // Set up RecyclerView and adapter
        favoriteSongAdapter = new FavoriteSongAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteSongAdapter);

        return view;
    }
}
