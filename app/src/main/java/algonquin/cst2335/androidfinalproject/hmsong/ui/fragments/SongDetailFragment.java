package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import static algonquin.cst2335.androidfinalproject.hmsong.ui.SongApp.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;

/**
 * Fragment to display details of a song and allow users to save it to favorites.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class SongDetailFragment extends Fragment {

    private static final String ARG_SONG = "arg_song";

    private Song song;

    /**
     * Default constructor for the SongDetailFragment.
     */
    public SongDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Static method to create a new instance of SongDetailFragment with the given song.
     *
     * @param song The song to display details for.
     * @return A new instance of SongDetailFragment.
     */
    public static SongDetailFragment newInstance(Song song) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, song);
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

        // Retrieve the arguments from the bundle
        if (getArguments() != null) {
            song = getArguments().getParcelable(ARG_SONG);
        }

        // Set the song details to the views
        if (song != null) {
            tvTitle.setText(getString(R.string.track_label) + song.getTitle());
            tvAlbumName.setText(getString(R.string.album_label) + song.getAlbumName());
            Picasso.get().load(song.getAlbumCoverUrl()).into(ivAlbumCover);

            // Format the duration and set it in the layout
            String formattedDuration = formatDuration(Long.parseLong(song.getDuration()));
            binding.tvDuration.setText(getString(R.string.duration_label) + formattedDuration);
        }

        // Set up the "Save to Favorites" button click listener
        btnSaveToFavorites.setOnClickListener(v -> saveSongToFavorites(song));

        return view;
    }

    private void saveSongToFavorites(Song song) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Check if the song already exists in the favorites
            if (!isSongAlreadyInFavorites(song)) {
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
            } else {
                requireActivity().runOnUiThread(() -> showToast("Song is already in favorites"));
            }
        });
    }

    private boolean isSongAlreadyInFavorites(Song song) {
        // Check if the song with the same title, duration, and album name already exists in favorites
        return database.favoriteSongDao().getFavoriteSongByDetails(
                song.getTitle(),
                song.getDuration(),
                song.getAlbumName()) != null;
    }

    // Add a helper method to format the duration
    private String formatDuration(long durationInSeconds) {
        long minutes = durationInSeconds / 60;
        long seconds = durationInSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private void showToast(String message) {
        // Display a toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
