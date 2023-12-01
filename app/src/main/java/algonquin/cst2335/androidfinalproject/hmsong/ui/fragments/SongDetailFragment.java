package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import static algonquin.cst2335.androidfinalproject.hmsong.ui.SongApp.database;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
            tvTitle.setText("Track: " + song.getTitle());
            tvDuration.setText("Duration: " + song.getDuration());
            tvAlbumName.setText("Album: " + song.getAlbumName());
            Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);

            // Use Picasso or Glide library to load the album cover image
            // Example: Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);
        }

        // Set up the "Save to Favorites" button click listener
        btnSaveToFavorites.setOnClickListener(v -> saveSongToFavorites(song));


        return view;
    }


    // Inside saveSongToFavorites method
    private void saveSongToFavorites(Song song) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Create FavoriteSong object from Song
            algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong favoriteSong =
                    new algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong(
                            song.getTitle(),
                            song.getDuration(),
                            song.getAlbumName(),
                            song.getAlbumCoverUrl()
                    );

            // Save to database
            long result = database.favoriteSongDao().saveFavoriteSong(favoriteSong);

            requireActivity().runOnUiThread(() -> {
                if (result != -1) {
                    showToast("Song saved to favorites");
                } else {
                    showToast("Failed to save song to favorites");
                }
            });
        });
    }


    private void showToast(String message) {
        // Display a toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(String message) {
        // Display a Snackbar
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }



}
