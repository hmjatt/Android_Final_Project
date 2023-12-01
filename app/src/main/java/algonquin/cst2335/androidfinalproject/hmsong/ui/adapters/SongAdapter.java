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
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private OnSongItemClickListener listener;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    public void setOnItemClickListener(OnSongItemClickListener listener) {
        this.listener = listener;
    }

    public void setSongs(List<Song> songs) {
        this.songList = songs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hm_item_song, parent, false);
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

        private TextView tvTitle;
        private TextView tvDuration;
        private TextView tvAlbumName;

        private ImageView ivCover;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            ivCover = itemView.findViewById(R.id.ivAlbumCover);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onSongItemClick(songList.get(position));
                }
            });
        }

        public void bind(Song song) {
            tvTitle.setText(song.getTitle());
            tvDuration.setText(song.getDuration());
            tvAlbumName.setText(song.getAlbumName());

            // Creates an ImageRequest for loading the album cover image
            ImageRequest imgReq = new ImageRequest
                    (song.getAlbumCoverUrl(),
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

            // Adds the ImageRequest to the RequestQueue for fetching from the server
            Volley.newRequestQueue(itemView.getContext()).add(imgReq);
        }
    }
}
