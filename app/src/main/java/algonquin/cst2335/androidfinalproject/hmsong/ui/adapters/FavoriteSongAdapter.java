package algonquin.cst2335.androidfinalproject.hmsong.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

/**
 * Adapter class for displaying a list of favorite songs in a RecyclerView.
 * Utilizes the ListAdapter with DiffUtil for efficient updates.
 *
 * @author Harmeet Matharoo
 * @version 1.0
 */
public class FavoriteSongAdapter extends ListAdapter<FavoriteSong, FavoriteSongAdapter.FavoriteSongViewHolder> {

    private static OnItemClickListener onItemClickListener;
    private final Set<Long> savedSongIds = new HashSet<>();

    /**
     * Interface definition for a callback to be invoked when an item in the list is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(FavoriteSong favoriteSong);
    }

    /**
     * Sets the click listener for items in the adapter.
     *
     * @param listener The listener to be notified when an item is clicked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * Constructor for the FavoriteSongAdapter.
     */
    public FavoriteSongAdapter() {
        super(new DiffUtil.ItemCallback<FavoriteSong>() {
            @Override
            public boolean areItemsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    /**
     * Sets the list of favorite songs and updates the saved song IDs.
     *
     * @param favoriteSongs The list of favorite songs to be displayed.
     */
    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
        submitList(favoriteSongs);
        updateSavedSongIds(favoriteSongs);
    }

    private void updateSavedSongIds(List<FavoriteSong> favoriteSongs) {
        savedSongIds.clear();
        for (FavoriteSong song : favoriteSongs) {
            savedSongIds.add(song.getId());
        }
    }

    @NonNull
    @Override
    public FavoriteSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HmItemFavoriteSongBinding binding = HmItemFavoriteSongBinding.inflate(inflater, parent, false);
        return new FavoriteSongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteSongViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    /**
     * ViewHolder class for the RecyclerView, representing individual favorite song items.
     */
    class FavoriteSongViewHolder extends RecyclerView.ViewHolder {

        private final HmItemFavoriteSongBinding binding;

        /**
         * Constructor for the FavoriteSongViewHolder.
         *
         * @param binding The data binding for the ViewHolder.
         */
        FavoriteSongViewHolder(HmItemFavoriteSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    FavoriteSong clickedSong = getItem(position);
                    if (!savedSongIds.contains(clickedSong.getId())) {
                        onItemClickListener.onItemClick(clickedSong);
                        savedSongIds.add(clickedSong.getId());

                    }
                }
            });
        }

        /**
         * Binds the data of a favorite song to the ViewHolder.
         *
         * @param favoriteSong The favorite song to be displayed.
         */
        void bind(FavoriteSong favoriteSong) {
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(getItem(position));
                }
            });

            binding.tvSongTitle.setText(favoriteSong.getTitle());
            binding.tvDuration.setText(favoriteSong.getDuration());
            binding.tvAlbumName.setText(favoriteSong.getAlbumName());

            Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);
        }
    }
}
