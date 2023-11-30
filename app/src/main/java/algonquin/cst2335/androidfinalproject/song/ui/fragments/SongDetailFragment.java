// Add this class to the existing package
package algonquin.cst2335.androidfinalproject.song.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.FragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.song.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.song.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.song.model.Song;
import algonquin.cst2335.androidfinalproject.song.ui.SongApp;

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
        FragmentSongDetailBinding binding = FragmentSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView tvTitle = binding.tvSongTitle;
        TextView tvDuration = binding.tvDuration;
        TextView tvAlbumName = binding.tvAlbumName;
        ImageView ivAlbumCover = binding.ivAlbumCover;
        Button btnSaveToFavorites = binding.btnSaveToFavorites;
        Button btnBack = binding.btnBackSongDetail;

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

            // Use Picasso or Glide library to load the album cover image
            // Example: Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);
        }

        // Set up the "Save to Favorites" button click listener
        btnSaveToFavorites.setOnClickListener(v -> saveSongToFavorites(song));

        // Set up the "Back" button click listener
        btnBack.setOnClickListener(v -> navigateBack());

        return view;
    }

    private void saveSongToFavorites(Song song) {
        // Save the selected song to the Room database
        FavoriteSongDatabase database = SongApp.database;
        FavoriteSong favoriteSong = new FavoriteSong();
        favoriteSong.setTitle(song.getTitle());
        favoriteSong.setDuration(song.getDuration());
        favoriteSong.setAlbumName(song.getAlbumName());
        favoriteSong.setAlbumCoverUrl(song.getAlbumCoverUrl());

        long result = database.favoriteSongDao().saveFavoriteSong(favoriteSong);
        if (result != -1) {
            // Show a toast or Snackbar indicating success
            // Example: showToast("Song saved to favorites");
        } else {
            // Show a toast or Snackbar indicating failure
            // Example: showToast("Failed to save song to favorites");
        }

        // If the fragment was launched from the search screen, navigate back to the search screen
        if (showSearch) {
            navigateBack();
        }
    }

    private void navigateBack() {
        // Replace the current fragment with the ArtistSearchFragment
        ArtistSearchFragment artistSearchFragment = new ArtistSearchFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSf, artistSearchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
