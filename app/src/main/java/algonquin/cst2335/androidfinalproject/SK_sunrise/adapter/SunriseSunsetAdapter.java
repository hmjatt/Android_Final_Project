package algonquin.cst2335.androidfinalproject.SK_sunrise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;

public class SunriseSunsetAdapter extends RecyclerView.Adapter<SunriseSunsetAdapter.ViewHolder> {

    private List<Location> locationList;

    public SunriseSunsetAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sk_item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.locationName.setText(location.getName());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;

        ViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.tvLocationName);
        }
    }
}