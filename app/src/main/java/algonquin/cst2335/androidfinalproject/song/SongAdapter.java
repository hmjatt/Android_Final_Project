// SongAdapter.java
package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import algonquin.cst2335.androidfinalproject.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> songs;

    private SongAdapter.OnItemClickListener songListener;

    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    public SongAdapter(List<Song> songs, SongAdapter.OnItemClickListener songListener) {
        this.songs = songs;
        this.songListener = songListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song, songListener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView songTitle, duration, albumName;
        private ImageView albumCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.tvSongTitle);
            duration = itemView.findViewById(R.id.tvDuration);
            albumName = itemView.findViewById(R.id.tvAlbumName);
            albumCover = itemView.findViewById(R.id.ivAlbumCover);
        }

        public void bind(final Song song, final SongAdapter.OnItemClickListener songListener) {
            // Bind data to views
            songTitle.setText(song.getTitle());
//            duration.setText(song.getDuration());
//            albumName.setText(song.getAlbumName());
            Picasso.get().load(song.getAlbumCoverUrl()).into(albumCover);

            itemView.setOnClickListener(v -> {
                // If you want to perform additional actions when a song is clicked,
                // you can call the onItemClick method of the listener
                if (songListener != null) {
                    songListener.onItemClick(song);
                }
            });
        }


    }
}
