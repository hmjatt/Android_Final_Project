package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.FavoriteSongAdapter;

public class SongDetailFragment extends Fragment {

    private static final String ARG_SONG = "arg_song";
    private static final String ARG_SHOW_SEARCH = "arg_show_search";

    private Song song;
    private boolean showSearch;

    public SongDetailFragment() {
        // Required empty public constructor
    }

    public static SongDetailFragment newInstance(Song song, boolean showSearch) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, song);
        args.putBoolean(ARG_SHOW_SEARCH, showSearch);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HmFragmentSongDetailBinding binding = HmFragmentSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView tvTitle = binding.tvSongTitle;
        TextView tvDuration = binding.tvDuration;
        TextView tvAlbumName = binding.tvAlbumName;
        ImageView ivAlbumCover = binding.ivAlbumCover;
        Button btnSaveToFavorites = binding.btnSaveToFavorites;
//        Button btnBack = binding.btnBackSongDetail;

        // Retrieve the arguments from the bundle
        if (getArguments() != null) {
            song = getArguments().getParcelable(ARG_SONG);
            showSearch = getArguments().getBoolean(ARG_SHOW_SEARCH, false);
        }

        // Set the song details to the views
        if (song != null) {
            tvTitle.setText(song.getTitle());
            tvDuration.setText(song.getDuration());
            tvAlbumName.setText(song.getAlbumName());
            Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);

            // Use Picasso or Glide library to load the album cover image
            // Example: Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);
        }

        // Set up the "Save to Favorites" button click listener
        btnSaveToFavorites.setOnClickListener(v -> saveSongToFavorites(song));

        // Set up the "Back" button click listener
//        btnBack.setOnClickListener(v -> navigateBack());

        return view;
    }

    private void saveSongToFavorites(Song song) {
        // Use AsyncTask to perform database operation in the background
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                // Save the selected song to the Room database
                FavoriteSongDatabase database = FavoriteSongDatabase.getInstance(requireContext());
                FavoriteSong favoriteSong = new FavoriteSong();
                favoriteSong.setTitle(song.getTitle());
                favoriteSong.setDuration(song.getDuration());
                favoriteSong.setAlbumName(song.getAlbumName());
                favoriteSong.setAlbumCoverUrl(song.getAlbumCoverUrl());

                // Perform the database operation on a background thread
                return database.favoriteSongDao().saveFavoriteSong(favoriteSong);
            }

            @Override
            protected void onPostExecute(Long result) {
                super.onPostExecute(result);

                // Handle the result of the database operation
                if (result != -1) {
                    // Show a toast or Snackbar indicating success
                    // Example: showToast("Song saved to favorites");
                } else {
                    // Show a toast or Snackbar indicating failure
                    // Example: showToast("Failed to save song to favorites");
                }

                // If the fragment was launched from the search screen, navigate back to the search screen
//                if (showSearch) {
//                    navigateBack();
//                }
            }
        }.execute();
    }


//    private void navigateBack() {
//        // Replace the current fragment with the ArtistSearchFragment
//        ArtistSearchFragment artistSearchFragment = new ArtistSearchFragment();
//        getParentFragmentManager().beginTransaction()
//                .replace(R.id.fragmentContainerSf, artistSearchFragment)
//                .addToBackStack(null)
//                .commit();
//    }
}
