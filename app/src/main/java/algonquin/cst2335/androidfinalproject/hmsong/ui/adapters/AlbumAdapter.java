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

            // Use Picasso to load the album cover image
            Picasso.get().load(album.getCoverUrl()).into(ivCover);
        }
    }
}



