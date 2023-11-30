package algonquin.cst2335.androidfinalproject.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.FragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.song.Song;

public class SongDetailFragment extends Fragment implements SaveSongTask.OnTaskCompleteListener {

    private static final String ARG_SONG = "arg_song";

    // Initialize Room database
    FavoriteSongDatabase database = SongApp.database;

    public static SongDetailFragment newInstance(Song song) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSongDetailBinding binding = FragmentSongDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve the song from arguments
        Song song = getArguments().getParcelable(ARG_SONG);

        // Display song details in the fragment
        binding.tvSongTitleSf.setText("Track: " + song.getTitle());
        binding.tvDurationSf.setText("Duration: " + song.getDuration());
        binding.tvAlbumNameSf.setText("Album: " + song.getAlbumName());

        Picasso.get().load(song.getAlbumCoverUrl()).into(binding.ivAlbumCoverSf);

        // Set up the "Save to Favorites" button
        Button saveToFavoritesButton = view.findViewById(R.id.btnSaveToFavorites);
        saveToFavoritesButton.setOnClickListener(v -> {
            // Start the AsyncTask to save the song to favorites
            new SaveSongTask(SongApp.database, this).execute(song);
        });

        // Set up the "View Favorites" button
        Button viewFavoritesButton = view.findViewById(R.id.btnViewFavorites);
        viewFavoritesButton.setOnClickListener(v -> navigateToFavoriteSongsFragment());

        return view;
    }

    @Override
    public void onTaskComplete(Long result) {
        // This method will be called when the AsyncTask completes
        if (result != -1) {
            showToast("Song saved to favorites");
        } else {
            showToast("Failed to save song to favorites");
        }
    }

    // Additional methods to retrieve and display favorite songs
    private void navigateToFavoriteSongsFragment() {
        // Replace the current fragment with the fragment displaying favorite songs
        FavoriteSongsFragment favoriteSongsFragment = new FavoriteSongsFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSf, favoriteSongsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
