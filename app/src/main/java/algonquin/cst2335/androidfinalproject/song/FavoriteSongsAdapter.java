package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.song.FavoriteSong;

public class FavoriteSongsAdapter extends RecyclerView.Adapter<FavoriteSongsAdapter.ViewHolder> {

    private List<FavoriteSong> favoriteSongs;

    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteSong favoriteSong = favoriteSongs.get(position);

        // Bind data to the ViewHolder
        holder.tvTitle.setText(favoriteSong.getTitle());
        holder.tvAlbumName.setText(favoriteSong.getAlbumName());
        holder.tvDuration.setText(favoriteSong.getDuration());
    }

    @Override
    public int getItemCount() {
        return favoriteSongs != null ? favoriteSongs.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAlbumName, tvDuration;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }
    }
}
