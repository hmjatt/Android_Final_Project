package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private List<Artist> artists;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Artist artist);
    }

    public ArtistAdapter(List<Artist> artists, OnItemClickListener listener) {
        this.artists = artists;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.bind(artist, listener);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView artistName;

        public ViewHolder(View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.tvArtistName);
        }

        public void bind(final Artist artist, final OnItemClickListener listener) {
            artistName.setText(artist.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(artist));
        }
    }
}
