package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import static algonquin.cst2335.androidfinalproject.hmsong.ui.SongApp.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongDetailBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

/**
 * Fragment to display details of a favorite song and provide the option to delete it.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class FavoriteSongDetailFragment extends Fragment {

    private FavoriteSong favoriteSong;

    private FavoriteSong deletedFavoriteSong; // Variable to store the deleted song temporarily

    private OnUndoDeleteListener undoDeleteListener;

    // Interface for the listener
    public interface OnUndoDeleteListener {
        void onUndoDelete();
    }

    // Setter method to set the listener
    public void setUndoDeleteListener(OnUndoDeleteListener listener) {
        this.undoDeleteListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HmItemFavoriteSongDetailBinding binding = HmItemFavoriteSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve the selected favorite song from arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("favoriteSong")) {
            favoriteSong = args.getParcelable("favoriteSong");

            if (favoriteSong != null) {
                // Set the details in the layout
                binding.tvSongTitle.setText(getString(R.string.track_label) + favoriteSong.getTitle());
                binding.tvAlbumName.setText(getString(R.string.album_label) + favoriteSong.getAlbumName());
                Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);

                // Format the duration and set it in the layout
                String formattedDuration = formatDuration(Long.parseLong(favoriteSong.getDuration()));
                binding.tvDuration.setText(getString(R.string.duration_label) + formattedDuration);
            }
        }

        // Set up delete button click listener
        Button deleteButton = view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        return view;
    }

    /**
     * Shows a confirmation dialog for deleting the favorite song.
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.delete_confirmation_title)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteSong())
                .setNegativeButton(R.string.no, null)
                .show();
    }

    /**
     * Deletes the favorite song from the database and shows a Snackbar with the option to undo.
     */
    private void deleteSong() {
        // Check if the favoriteSong is not null
        if (favoriteSong != null) {
            // Use Room database to delete the song
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Store the deleted song temporarily
                deletedFavoriteSong = favoriteSong;

                // Implement logic to remove the selected song from favorites and update UI
                database.favoriteSongDao().deleteFavoriteSong(favoriteSong);

                // Update UI on the main thread
                requireActivity().runOnUiThread(() -> {
                    // Show a Snackbar with the option to undo
                    Snackbar snackbar = Snackbar.make(requireView(), R.string.song_deleted, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.undo, v -> undoDelete());
                    snackbar.show();

                    // Optionally, you can navigate back to the list of favorite songs or perform any other UI update
                    // For example, if you want to navigate back to the list of favorite songs:
                    getParentFragmentManager().popBackStack();
                });
            });
        }
    }

    /**
     * Undoes the delete operation by inserting the deleted song back into the favorites.
     */
    private void undoDelete() {
        // Check if there is a deleted song to undo
        if (deletedFavoriteSong != null) {
            // Use Room database to insert the deleted song back into the favorites
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Insert the deleted song back into the favorites
                long newSongId = database.favoriteSongDao().saveFavoriteSong(deletedFavoriteSong);

                // Reset the deletedFavoriteSong variable
                deletedFavoriteSong = null;

                // Notify the listener (parent fragment or activity) to reload the list
                if (undoDeleteListener != null) {
                    undoDeleteListener.onUndoDelete();
                }
            });
        }
    }

    /**
     * Helper method to format the duration from seconds to "mm:ss" format.
     *
     * @param durationInSeconds The duration in seconds.
     * @return The formatted duration string.
     */
    private String formatDuration(long durationInSeconds) {
        long minutes = durationInSeconds / 60;
        long seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
