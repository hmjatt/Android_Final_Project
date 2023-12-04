package algonquin.cst2335.androidfinalproject.hmsong.ui.adapters;

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
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;

/**
 * Adapter class for the RecyclerView to display a list of songs.
 *
 * @author Harmeet Matharoo
 * @version 1.0
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private OnSongItemClickListener listener;

    /**
     * Constructor for the SongAdapter.
     *
     * @param songList The list of songs to display.
     */
    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    /**
     * Sets the click listener for the song items.
     *
     * @param listener The click listener to set.
     */
    public void setOnItemClickListener(OnSongItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the list of songs and notifies the adapter of the data change.
     *
     * @param songs The list of songs to set.
     */
    public void setSongs(List<Song> songs) {
        this.songList = songs;
        notifyDataSetChanged();
    }

    /**
     * Creates a new ViewHolder instance by inflating the song item layout.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The type of view to be created.
     * @return A new SongViewHolder instance.
     */
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hm_item_song, parent, false);
        return new SongViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder   The SongViewHolder to bind data to.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    /**
     * Returns the total number of items in the dataset.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return songList.size();
    }

    /**
     * Interface definition for a callback to be invoked when a song item is clicked.
     */
    public interface OnSongItemClickListener {
        void onSongItemClick(Song song);
    }

    /**
     * ViewHolder class for the SongAdapter.
     */
    class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDuration;
        private TextView tvAlbumName;
        private ImageView ivCover;

        /**
         * Constructor for the SongViewHolder.
         *
         * @param itemView The view for each song item.
         */
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            ivCover = itemView.findViewById(R.id.ivAlbumCover);

            // Set click listener for each song item
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onSongItemClick(songList.get(position));
                }
            });
        }

        /**
         * Binds data to the SongViewHolder.
         *
         * @param song The song object to bind.
         */
        public void bind(Song song) {
            tvTitle.setText(song.getTitle());
            tvDuration.setText(song.getDuration());
            tvAlbumName.setText(song.getAlbumName());

            // Use Picasso to load the album cover image
            Picasso.get().load(song.getAlbumCoverUrl()).into(ivCover);
        }
    }
}
