package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentArtistSearchBinding;
import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;

// Other imports...

public class FavoriteSongsFragment extends Fragment {

    private static final String ARG_SHOW_SEARCH = "arg_show_search";

    private EditText etSearch;

    private FavoriteSongAdapter favoriteSongAdapter;
    private List<FavoriteSong> favoriteSongsList;
    private FavoriteSongDatabase database;

    private RecyclerView recyclerView;

    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HmFragmentArtistSearchBinding binding = HmFragmentArtistSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerView;

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Retrieve the last searched term from SharedPreferences
        String lastSearchedTerm = sharedPreferences.getString("searchTerm", "");

        // Replace '+' with space for a more readable display
        lastSearchedTerm = lastSearchedTerm.replace("+", " ");

        // Use the last searched term to set the EditText
        etSearch = binding.etSearch;
        etSearch.setText(lastSearchedTerm);

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
            // Create a bundle to pass the selected favorite song to the detail fragment
            Bundle args = new Bundle();
            args.putParcelable("favoriteSong", favoriteSong);

            // Navigate to FavoriteSongDetailFragment
            FavoriteSongDetailFragment fragment = new FavoriteSongDetailFragment();
            fragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerSf, fragment)
                    .addToBackStack(null)
                    .commit();
        });



        return view;
    }
}
