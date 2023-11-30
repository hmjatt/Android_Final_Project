package algonquin.cst2335.androidfinalproject.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

//import algonquin.cst2335.androidfinalproject.databinding.FragmentSongDetailBinding;
import com.squareup.picasso.Picasso;

import algonquin.cst2335.androidfinalproject.databinding.FragmentSongDetailBinding;
import algonquin.cst2335.androidfinalproject.song.Song;

public class SongDetailFragment extends Fragment {

    private static final String ARG_SONG = "arg_song";

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
        binding.tvSongTitleSf.setText(song.getTitle());
        binding.tvDurationSf.setText(song.getDuration());
        binding.tvAlbumNameSf.setText(song.getAlbumName());

        Picasso.get().load(song.getAlbumCoverUrl()).into(binding.ivAlbumCoverSf);


        return view;
    }


}
