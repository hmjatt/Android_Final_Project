package algonquin.cst2335.androidfinalproject.hmsong.ui.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import algonquin.cst2335.androidfinalproject.databinding.HmItemFavoriteSongBinding;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;

public class FavoriteSongAdapter extends ListAdapter<FavoriteSong, FavoriteSongAdapter.FavoriteSongViewHolder> {

    private static OnItemClickListener onItemClickListener;

    // Define an interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(FavoriteSong favoriteSong);
    }

    // Set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

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
        submitList(favoriteSongs);
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

            // Set up click listener for the item
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(getItem(position));
                }
            });
        }

        void bind(FavoriteSong favoriteSong) {
            binding.tvSongTitle.setText(favoriteSong.getTitle());
            binding.tvDuration.setText(favoriteSong.getDuration());
            binding.tvAlbumName.setText(favoriteSong.getAlbumName());

            // Creates an ImageRequest for loading the album cover image
            ImageRequest imgReq = new ImageRequest
                    (favoriteSong.getAlbumCoverUrl(),
                            // Success listener for image loading
                            responseImage -> {
                                binding.ivAlbumCover.setImageBitmap(responseImage);
                                Log.d("Image received", "Got the image");
                            },
                            1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                            // Error listener for image loading
                            error -> {
                                Log.d("Error", "Error loading image: " + error.getMessage());
                            }
                    );

            // Adds the ImageRequest to the RequestQueue for fetching from the server
            Volley.newRequestQueue(itemView.getContext()).add(imgReq);
        }
    }
}
