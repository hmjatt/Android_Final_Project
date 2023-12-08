package algonquin.cst2335.androidfinalproject.CF_recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecyclerViewAdapter(List<Recipe> recipes) {

    }



    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Constructor, onCreateViewHolder, onBindViewHolder, getItemCount methods

    // ViewHolder class
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
