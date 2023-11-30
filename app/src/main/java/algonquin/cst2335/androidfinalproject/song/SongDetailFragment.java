package algonquin.cst2335.androidfinalproject.song;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.CommonSongLayoutBinding;
import algonquin.cst2335.androidfinalproject.databinding.FragmentArtistSearchBinding;
import algonquin.cst2335.androidfinalproject.databinding.FragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.song.Song;

public class SongDetailFragment extends Fragment implements SaveSongTask.OnTaskCompleteListener {
    private static final String ARG_SONG = "arg_song";
    private static final String ARG_SHOW_SEARCH = "arg_show_search";

    // Initialize Room database
    FavoriteSongDatabase database = SongApp.database;


    public static SongDetailFragment newInstance(Song song, boolean showSearch) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, song);
        args.putBoolean(ARG_SHOW_SEARCH, showSearch);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonSongLayoutBinding binding = CommonSongLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        // Retrieve arguments
        Song song = getArguments().getParcelable(ARG_SONG);
        boolean showSearch = getArguments().getBoolean(ARG_SHOW_SEARCH, true);

        // Display song details in the fragment
        binding.tvSongTitleSf.setText("Track: " + song.getTitle());
        binding.tvDurationSf.setText("Duration: " + song.getDuration());
        binding.tvAlbumNameSf.setText("Album: " + song.getAlbumName());

        Picasso.get().load(song.getAlbumCoverUrl()).into(binding.ivAlbumCoverSf);

        // Set up the "Save to Favorites" button
        Button saveToFavoritesButton = binding.btnSaveToFavorites;
        saveToFavoritesButton.setOnClickListener(v -> {
            // Start the AsyncTask to save the song to favorites
            new SaveSongTask(SongApp.database, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, song);
        });

        // Set up the "View Favorites" button
        Button viewFavoritesButton = view.findViewById(R.id.btnViewFavorites);
        viewFavoritesButton.setOnClickListener(v -> navigateToFavoriteSongsFragment());

        // Check if it's a detailed view, and hide unnecessary elements
        if (!showSearch) {
            // Hide unnecessary elements when viewing message details
            binding.fragmentContainerSf.setVisibility(View.GONE);
            binding.srchThing.setVisibility(View.GONE);
        }

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

    private void navigateToFavoriteSongsFragment() {
        // Retrieve the showSearch value from arguments
        boolean showSearch = getArguments().getBoolean(ARG_SHOW_SEARCH, true);

        // Replace the current fragment with the fragment displaying favorite songs
        FavoriteSongsFragment favoriteSongsFragment = new FavoriteSongsFragment();

        // Pass the showSearch value to the new fragment
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_SEARCH, showSearch);
        favoriteSongsFragment.setArguments(args);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSf, favoriteSongsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
