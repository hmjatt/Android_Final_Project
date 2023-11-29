// SongAdapter.java
package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import algonquin.cst2335.androidfinalproject.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
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
        holder.bind(song);
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

        public void bind(Song song) {
            // Bind data to views
            songTitle.setText(song.getTitle());
            duration.setText(song.getDuration());
            albumName.setText(song.getAlbumName());

            // You may need to load the album cover image using a library like Picasso or Glide
            // For simplicity, I'm assuming there's a method `getAlbumCoverUrl()` in the Song class
            // that returns the URL for the album cover image.

        }
    }
}
