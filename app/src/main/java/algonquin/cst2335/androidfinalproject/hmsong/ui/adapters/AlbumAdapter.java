package algonquin.cst2335.androidfinalproject.hmsong.ui.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.hmsong.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> albumList;
    private OnAlbumItemClickListener listener;

    public AlbumAdapter(List<Album> albumList, OnAlbumItemClickListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hm_item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.bind(album);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public interface OnAlbumItemClickListener {
        void onAlbumItemClick(Album album);
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivCover;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAlbumTitle);
            ivCover = itemView.findViewById(R.id.ivAlbumCover);

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onAlbumItemClick(albumList.get(position));
                }
            });
        }

        public void bind(Album album) {
            tvTitle.setText(album.getTitle());

            // Creates an ImageRequest for loading the album cover image
            ImageRequest imgReq = new ImageRequest(
                    album.getCoverUrl(),
                    // Success listener for image loading
                    responseImage -> {
                        ivCover.setImageBitmap(responseImage);
                        Log.d("Image received", "Got the image");
                    },
                    1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                    // Error listener for image loading
                    error -> {
                        Log.d("Error", "Error loading image: " + error.getMessage());
                    }
            );

            // Add the ImageRequest to the Volley request queue
            Volley.newRequestQueue(itemView.getContext()).add(imgReq);
        }
    }
}
