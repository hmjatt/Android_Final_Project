package algonquin.cst2335.androidfinalproject.SK_sunrise.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.SK_sunrise.adapter.FavoritesAdapter;
import algonquin.cst2335.androidfinalproject.SK_sunrise.model.Location;
import algonquin.cst2335.androidfinalproject.SK_sunrise.viewmodel.SunriseSunsetViewModel;

public class FavoritesFragment extends Fragment {
    private SunriseSunsetViewModel viewModel;
    private RecyclerView recyclerViewFavorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sk_fragment_favourites, container, false);

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);

        // Set up RecyclerView and adapter
        FavoritesAdapter adapter = new FavoritesAdapter();
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewFavorites.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(SunriseSunsetViewModel.class);

        // Observe changes in the favorite locations and update the adapter
        viewModel.getAllLocations().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                adapter.setLocations(locations);
            }
        });

        return view;
    }
}
