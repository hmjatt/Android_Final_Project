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

public class FavoriteSongAdapter extends ListAdapter<FavoriteSong, FavoriteSongAdapter.FavoriteSongViewHolder> {

    private static OnItemClickListener onItemClickListener;
    private final Set<Long> savedSongIds = new HashSet<>();

    public interface OnItemClickListener {
        void onItemClick(FavoriteSong favoriteSong);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

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

    class FavoriteSongViewHolder extends RecyclerView.ViewHolder {

        private final HmItemFavoriteSongBinding binding;

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

        void bind(FavoriteSong favoriteSong) {
            binding.tvSongTitle.setText(favoriteSong.getTitle());
            binding.tvDuration.setText(favoriteSong.getDuration());
            binding.tvAlbumName.setText(favoriteSong.getAlbumName());

            Picasso.get().load(favoriteSong.getAlbumCoverUrl()).into(binding.ivAlbumCover);
        }
    }
}
