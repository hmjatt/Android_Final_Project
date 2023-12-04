// io_dictionary.ui.adapters.DefinitionsAdapter
package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;

public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionsAdapter.ViewHolder> {
    private List<Definition> definitions;
    private SharedPreferences sharedPreferences;

    public DefinitionsAdapter(Context context, List<Definition> definitions) {
        this.definitions = definitions;
        this.sharedPreferences = context.getSharedPreferences("SearchTerms", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(/* Your layout resource for a single definition item */, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Definition definition = definitions.get(position);
        // Bind data to views in the ViewHolder

        // Save search term to SharedPreferences when an item is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = definition.getSearchTerm();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("last_search_term", searchTerm);
                editor.apply();

                // Add code to handle item click (e.g., show definitions for the clicked term)
            }
        });
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declare views in your definition item layout
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
        }
    }
}
