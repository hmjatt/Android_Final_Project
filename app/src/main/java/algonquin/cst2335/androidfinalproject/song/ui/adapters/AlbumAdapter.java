package algonquin.cst2335.androidfinalproject.song.ui.adapters;

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
import algonquin.cst2335.androidfinalproject.song.model.Album;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
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

        private ImageView ivAlbumCover;
        private TextView tvAlbumTitle;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbumCover = itemView.findViewById(R.id.ivAlbumCover);
            tvAlbumTitle = itemView.findViewById(R.id.tvAlbumTitle);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onAlbumItemClick(albumList.get(position));
                }
            });
        }

        public void bind(Album album) {
            Picasso.get().load(album.getCoverUrl()).into(ivAlbumCover);
            tvAlbumTitle.setText(album.getTitle());
        }
    }
}
