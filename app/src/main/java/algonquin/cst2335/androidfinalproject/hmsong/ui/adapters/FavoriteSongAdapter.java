// Add this class to the existing package
package algonquin.cst2335.androidfinalproject.hmsong.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

public class FavoriteSongAdapter extends ListAdapter<FavoriteSong, FavoriteSongAdapter.FavoriteSongViewHolder> {


    private List<FavoriteSong> favoriteSongs;

    // Public constructor
    public FavoriteSongAdapter() {
        // Pass a DiffUtil.ItemCallback to the super constructor
        super(new DiffUtil.ItemCallback<FavoriteSong>() {
            @Override
            public boolean areItemsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
                // Implement your logic to check if items are the same
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
                // Implement your logic to check if item contents are the same
                return oldItem.equals(newItem);
            }
        });
    }

    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
        notifyDataSetChanged();
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

    static class FavoriteSongViewHolder extends RecyclerView.ViewHolder {

        private final HmItemFavoriteSongBinding binding;

        FavoriteSongViewHolder(HmItemFavoriteSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FavoriteSong favoriteSong) {
            binding.tvTitle.setText(favoriteSong.getTitle());
            binding.tvDuration.setText(favoriteSong.getDuration());
            binding.tvAlbumName.setText(favoriteSong.getAlbumName());
            // You can bind other views here if needed
        }
    }

    private static final DiffUtil.ItemCallback<FavoriteSong> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteSong>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteSong oldItem, @NonNull FavoriteSong newItem) {
            // You can customize this based on your requirements
            return oldItem.equals(newItem);
        }
    };
}
