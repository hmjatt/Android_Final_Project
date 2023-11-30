package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.AsyncTask;
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

        // Use AsyncTask to fetch favorite songs in the background
        new FetchFavoriteSongsTask().execute();

        // Set up RecyclerView and adapter
        favoriteSongAdapter = new FavoriteSongAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteSongAdapter);

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
            favoriteSongs = result;
            favoriteSongAdapter.setFavoriteSongs(favoriteSongs);
            favoriteSongAdapter.notifyDataSetChanged();
        }


    }

//    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
//        this.favoriteSongs = favoriteSongs;
//        favoriteSongAdapter.notifyDataSetChanged();
//    }
}
