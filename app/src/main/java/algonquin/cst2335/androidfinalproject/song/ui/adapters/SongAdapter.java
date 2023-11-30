package algonquin.cst2335.androidfinalproject.song.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.song.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private OnSongItemClickListener listener;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    public void setOnItemClickListener(OnSongItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public interface OnSongItemClickListener {
        void onSongItemClick(Song song);
    }

    class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSongTitle;
        private TextView tvDuration;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            tvDuration = itemView.findViewById(R.id.tvDuration);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onSongItemClick(songList.get(position));
                }
            });
        }

        public void bind(Song song) {
            tvSongTitle.setText(song.getTitle());
            tvDuration.setText(song.getDuration());
        }
    }
}
