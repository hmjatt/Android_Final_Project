package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import static algonquin.cst2335.androidfinalproject.hmsong.ui.SongApp.database;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongDetailBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

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
        Log.d("FavSongDetailFragment", "onCreateView");

        // Inflate the layout for this fragment
        HmItemFavoriteSongDetailBinding binding = HmItemFavoriteSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve the selected favorite song from arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("favoriteSong")) {
            favoriteSong = args.getParcelable("favoriteSong");

            if (favoriteSong != null) {
                // Log details
                Log.d("FavoriteSongDetail", "Title: " + favoriteSong.getTitle());
                Log.d("FavoriteSongDetail", "Duration: " + favoriteSong.getDuration());
                Log.d("FavoriteSongDetail", "Album Name: " + favoriteSong.getAlbumName());
                Log.d("FavoriteSongDetail", "Album Cover URL: " + favoriteSong.getAlbumCoverUrl());

                // Set the details in the layout
                binding.tvSongTitle.setText(getString(R.string.track_label) + favoriteSong.getTitle());
                binding.tvDuration.setText(getString(R.string.duration_label) + favoriteSong.getDuration());
                binding.tvAlbumName.setText(getString(R.string.album_label) + favoriteSong.getAlbumName());
                Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);

                // Populate UI (not necessary in onCreateView)
                // populateUI(binding);
            } else {
                // Log if favoriteSong is null
                Log.e("FavoriteSongDetail", "FavoriteSong is null");
            }
        } else {
            // Log if arguments are null or do not contain "favoriteSong" key
            Log.e("FavoriteSongDetail", "Arguments are null or do not contain 'favoriteSong'");
        }

        // Set up delete button click listener
        Button deleteButton = view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        return view;
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.delete_confirmation_title)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteSong())
                .setNegativeButton(R.string.no, null)
                .show();

        // Add log statement
        Log.d("FavSongDetailFragment", "Delete confirmation dialog shown");
    }

    private void deleteSong() {

        // Log statement to check if the method is being called
        Log.d("FavSongDetailFragment", "deleteSong() called");

        // Log details of the favoriteSong being deleted
        Log.d("FavSongDetailFragment", "Deleting Song - Title: " + favoriteSong.getTitle() + ", ID: " + favoriteSong.getId());


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
                    // Add log statement
                    Log.d("FavSongDetailFragment", "Song deleted: " + favoriteSong.getTitle());

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


    private void undoDelete() {
        // Check if there is a deleted song to undo
        if (deletedFavoriteSong != null) {
            // Use Room database to insert the deleted song back into the favorites
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Insert the deleted song back into the favorites
                long newSongId = database.favoriteSongDao().saveFavoriteSong(deletedFavoriteSong);

                // Log details of the undo operation
                Log.d("FavSongDetailFragment", "Undo Delete - Title: " + deletedFavoriteSong.getTitle() + ", ID: " + newSongId);

                // Reset the deletedFavoriteSong variable
                deletedFavoriteSong = null;

                // Notify the listener (parent fragment or activity) to reload the list
                if (undoDeleteListener != null) {
                    undoDeleteListener.onUndoDelete();
                }
            });
        } else {
            // Log if there is no deleted song to undo
            Log.d("FavSongDetailFragment", "No deleted song to undo");
        }
    }





}
