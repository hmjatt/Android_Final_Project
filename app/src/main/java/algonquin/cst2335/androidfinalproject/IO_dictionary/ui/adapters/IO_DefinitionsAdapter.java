package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.R;

/**
 * Adapter class for displaying a list of IO_Definition items in a RecyclerView.
 *
 * @Author Iuliia Obukhova
 * @Version 1.0
 */
public class IO_DefinitionsAdapter extends RecyclerView.Adapter<IO_DefinitionsAdapter.DefinitionViewHolder> {

    // List to store IO_Definition items
    private List<IO_Definition> definitions = new ArrayList<>();

    /**
     * Constructor to accept initial data.
     *
     * @param definitions The list of IO_Definition items to be displayed.
     */
    public IO_DefinitionsAdapter(List<IO_Definition> definitions) {
        this.definitions = definitions;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_io_fragment_word_definition, parent, false);
        return new DefinitionViewHolder(view);
    }

    /**
     * Called to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        IO_Definition definition = definitions.get(position);
        holder.tvDefinition.setText(definition.getDefinition());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return definitions.size();
    }

    /**
     * Update the adapter data with a new list of IO_Definition items.
     *
     * @param newDefinitions The new list of IO_Definition items.
     */
    public void updateData(List<IO_Definition> newDefinitions) {
        definitions.clear();
        definitions.addAll(newDefinitions);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class to hold references to UI elements for each item in the RecyclerView.
     */
    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDefinition;

        DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvWordDetail);
        }
    }
}
