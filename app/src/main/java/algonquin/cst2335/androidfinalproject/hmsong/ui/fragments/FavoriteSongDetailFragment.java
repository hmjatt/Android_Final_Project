package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.Bundle;
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

import algonquin.cst2335.androidfinalproject.R;

import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongDetailBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

public class FavoriteSongDetailFragment extends Fragment {

    private FavoriteSong favoriteSong;

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
            FavoriteSong favoriteSong = args.getParcelable("favoriteSong");

            if (favoriteSong != null) {
                // Log details
                Log.d("FavoriteSongDetail", "Title: " + favoriteSong.getTitle());
                Log.d("FavoriteSongDetail", "Duration: " + favoriteSong.getDuration());
                Log.d("FavoriteSongDetail", "Album Name: " + favoriteSong.getAlbumName());
                Log.d("FavoriteSongDetail", "Album Cover URL: " + favoriteSong.getAlbumCoverUrl());

                // Set the details in the layout
                binding.tvSongTitle.setText( getString(R.string.track_label) + favoriteSong.getTitle());
                binding.tvDuration.setText(getString(R.string.duration_label) + favoriteSong.getDuration());
                binding.tvAlbumName.setText(getString(R.string.album_label) + favoriteSong.getAlbumName());
                Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);
            } else {
                // Log if favoriteSong is null
                Log.e("FavoriteSongDetail", "FavoriteSong is null");
            }
        } else {
            // Log if arguments are null or do not contain "favoriteSong" key
            Log.e("FavoriteSongDetail", "Arguments are null or do not contain 'favoriteSong'");
        }

        return view;
    }



    private void populateUI(HmItemFavoriteSongDetailBinding binding) {
        Log.d("populateMsg", "populateUI: " + favoriteSong.toString());

        binding.tvSongTitle.setText(getString(R.string.track_label) + favoriteSong.getTitle());
        binding.tvDuration.setText(getString(R.string.duration_label) + favoriteSong.getDuration());
        binding.tvAlbumName.setText(getString(R.string.album_label) + favoriteSong.getAlbumName());

        // Load the album cover image
        Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);
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
        // Implement logic to remove the selected song from favorites and update UI

        // Add log statement
        Log.d("FavSongDetailFragment", "Song deleted: " + favoriteSong.getTitle());

        // Show a Snackbar with the option to undo
        Snackbar snackbar = Snackbar.make(requireView(), R.string.song_deleted, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, v -> {
            // Implement undo logic if needed
            Toast.makeText(requireContext(), R.string.undo_clicked, Toast.LENGTH_SHORT).show();
        });
        snackbar.show();
    }

}
