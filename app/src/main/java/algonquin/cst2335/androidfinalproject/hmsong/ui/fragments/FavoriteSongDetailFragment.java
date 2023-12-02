package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        HmItemFavoriteSongDetailBinding binding = HmItemFavoriteSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle args = getArguments();
        if (args != null) {
            favoriteSong = args.getParcelable("favoriteSong");
            populateUI(binding);
        }

        Button deleteButton = view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        return view;
    }


    private void populateUI(HmItemFavoriteSongDetailBinding binding) {
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
    }

    private void deleteSong() {
        // Implement logic to remove the selected song from favorites and update UI

        // Show a Snackbar with the option to undo
        Snackbar snackbar = Snackbar.make(requireView(), R.string.song_deleted, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, v -> {
            // Implement undo logic if needed
            Toast.makeText(requireContext(), R.string.undo_clicked, Toast.LENGTH_SHORT).show();
        });
        snackbar.show();
    }
}
