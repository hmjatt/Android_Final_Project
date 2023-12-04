package algonquin.cst2335.androidfinalproject.SK_sunrise.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.LocationViewHolder> {

    private List<Location> locations = new ArrayList<>();

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sk_item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location currentLocation = locations.get(position);
        // Set up the UI elements based on the location data
        holder.bind(currentLocation);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLocationName;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
        }

        public void bind(Location location) {
            // Set up the UI elements based on the location data
            tvLocationName.setText("Location: " + location.getLatitude() + ", " + location.getLongitude());
        }
    }
}