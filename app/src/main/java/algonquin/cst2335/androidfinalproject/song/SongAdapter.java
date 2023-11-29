// SongAdapter.java
package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> songs;
    private OnItemClickListener songListener;

    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    // Constructor with one argument
    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    // Setter for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.songListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_view, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView songTitle;
        private ImageView albumCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.tvSongTitleSv);
            albumCover = itemView.findViewById(R.id.ivAlbumCover);
        }

        public void bind(final Song song) {
            // Bind data to views
            songTitle.setText(song.getTitle());
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
