package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentArtistSearchBinding;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentFavoriteSongsBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.SongAdapter;  // Import the correct class

public class FavoriteSongsFragment extends Fragment {

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

        // Initialize Room database
        database = FavoriteSongDatabase.getInstance(requireContext());



        // Use AsyncTask to fetch favorite songs in the background
        new FetchFavoriteSongsTask().execute();

        favoriteSongsList = new ArrayList<>();

        // Set up RecyclerView and adapter
        favoriteSongAdapter = new FavoriteSongAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteSongAdapter);

        // Set up item click listener to navigate to FavoriteSongDetailFragment
        // Inside FavoriteSongsFragment
        favoriteSongAdapter.setOnItemClickListener(favoriteSong -> {
            // Create a bundle to pass the selected favorite song to the detail fragment
            Bundle args = new Bundle();
            args.putParcelable("favoriteSong", (Parcelable) favoriteSong);

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

    private class FetchFavoriteSongsTask extends AsyncTask<Void, Void, List<FavoriteSong>> {

        @Override
        protected List<FavoriteSong> doInBackground(Void... voids) {
            // Fetch favorite songs from the database in the background
            return database.favoriteSongDao().getFavoriteSongs();
        }

        @Override
        protected void onPostExecute(List<FavoriteSong> result) {
            super.onPostExecute(result);
            // Update the UI with the fetched data
            favoriteSongsList = result;
            favoriteSongAdapter.setFavoriteSongs(favoriteSongsList);
            favoriteSongAdapter.notifyDataSetChanged();
        }
    }
}
