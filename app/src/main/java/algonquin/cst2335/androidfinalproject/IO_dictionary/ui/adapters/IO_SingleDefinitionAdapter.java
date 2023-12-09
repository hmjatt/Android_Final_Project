package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

/**
 * Adapter class for displaying a list of single definitions in a RecyclerView.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
public class IO_SingleDefinitionAdapter extends RecyclerView.Adapter<IO_SingleDefinitionAdapter.DefinitionViewHolder> {

    // List to store single definitions
    private List<String> definitions;
    private OnDeleteClickListener onDeleteClickListener;

    /**
     * Constructor to initialize the adapter with a list of single definitions and a delete click listener.
     *
     * @param definitions           The list of single definitions.
     * @param onDeleteClickListener The listener for delete click events.
     */
    public IO_SingleDefinitionAdapter(List<String> definitions, OnDeleteClickListener onDeleteClickListener) {
        this.definitions = definitions;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_io_fragment_single_definition, parent, false);
        return new DefinitionViewHolder(view);
    }

    /**
     * Called to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        String definition = definitions.get(position);
        holder.tvDefinition.setText(definition);

        // Set up delete button click listener
        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(position);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return definitions.size();
    }

    /**
     * Interface definition for a callback to be invoked when a delete button is clicked.
     */
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    /**
     * ViewHolder class to hold references to UI elements for each item in the RecyclerView.
     */
    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDefinition;
        TextView btnDelete;

        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvSavedWordDetail);
            btnDelete = itemView.findViewById(R.id.btnDeleteSavedDefinition);
        }
    }
}
