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
import algonquin.cst2335.androidfinalproject.hmsong.model.Album;

/**
 * Adapter class for the RecyclerView to display a list of albums.
 *
 *  @author Harmeet Matharoo
 *  @version 1.0
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> albumList;
    private OnAlbumItemClickListener listener;

    /**
     * Constructor for the AlbumAdapter.
     *
     * @param albumList The list of albums to be displayed.
     * @param listener  The click listener for album items.
     */
    public AlbumAdapter(List<Album> albumList, OnAlbumItemClickListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder instance by inflating the album item layout.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The type of view to be created.
     * @return A new AlbumViewHolder instance.
     */
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hm_item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder   The AlbumViewHolder to bind data to.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.bind(album);
    }

    /**
     * Returns the total number of items in the dataset.
     *
     * @return The total number of albums in the list.
     */
    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * Interface to define the click listener for album items.
     */
    public interface OnAlbumItemClickListener {
        /**
         * Called when an album item is clicked.
         *
         * @param album The clicked album.
         */
        void onAlbumItemClick(Album album);
    }

    /**
     * ViewHolder class for the AlbumAdapter.
     */
    class AlbumViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivCover;

        /**
         * Constructor for the AlbumViewHolder.
         *
         * @param itemView The view for each album item.
         */
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAlbumTitle);
            ivCover = itemView.findViewById(R.id.ivAlbumCover);

            // Set click listener for each album item
            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onAlbumItemClick(albumList.get(position));
                }
            });
        }

        /**
         * Binds data to the AlbumViewHolder.
         *
         * @param album The album object to bind.
         */
        public void bind(Album album) {
            tvTitle.setText(album.getTitle());

            // Use Picasso to load the album cover image
            Picasso.get().load(album.getCoverUrl()).into(ivCover);
        }
    }
}
