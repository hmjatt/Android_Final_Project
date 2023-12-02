package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentArtistSearchBinding;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentFavoriteSongsBinding;
import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;

// Other imports...

public class FavoriteSongsFragment extends Fragment {

//    private static final String ARG_SHOW_SEARCH = "arg_show_search";
//
//    private EditText etSearch;

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
        HmFragmentFavoriteSongsBinding binding = HmFragmentFavoriteSongsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerViewFs;

        // Set up RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteSongAdapter);


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

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerSf, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });



        return view;
    }
}
