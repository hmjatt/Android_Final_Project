package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

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
        deleteButton.setOnClickListener(v -> deleteFavoriteSong());

        return view;
    }

    private void populateUI(HmItemFavoriteSongDetailBinding binding) {
        // Populate UI components using 'favoriteSong'
        binding.tvSongTitle.setText(favoriteSong.getTitle());
        binding.tvDuration.setText(favoriteSong.getDuration());
        binding.tvAlbumName.setText(favoriteSong.getAlbumName());

        // Use Picasso to load the album cover image
        Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);
    }

    private void deleteFavoriteSong() {
        // Implement logic to remove the selected song from favorites and update UI
        // Use AsyncTask or other background task to perform database operation
    }
}
