package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordDefinitionBinding;

/**
 * Adapter class for displaying a list of saved IO_Definition items in a RecyclerView.
 *
 * @Author Iuliia Obukhova
 * @Version 1.0
 */
public class IO_SavedWordDefinitionAdapter
        extends RecyclerView.Adapter<IO_SavedWordDefinitionAdapter.DefinitionViewHolder> {

    // List to store saved IO_Definition items
    private List<IO_Definition> definitions = new ArrayList<>();
    private OnItemClickListener listener;

    /**
     * Set the list of saved IO_Definition items.
     *
     * @param definitions The list of saved IO_Definition items.
     */
    public void setDefinitions(List<IO_Definition> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }

    /**
     * Set the item click listener.
     *
     * @param listener The listener for item click events.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IoIoFragmentSavedWordDefinitionBinding binding =
                IoIoFragmentSavedWordDefinitionBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
        return new DefinitionViewHolder(binding);
    }

    /**
     * Called to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        IO_Definition definition = definitions.get(position);
        holder.bind(definition);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return definitions.size();
    }

    /**
     * Interface definition for a callback to be invoked when an item in the adapter is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(IO_Definition definition);
    }

    /**
     * ViewHolder class to hold references to UI elements for each item in the RecyclerView.
     */
    class DefinitionViewHolder extends RecyclerView.ViewHolder {

        private final IoIoFragmentSavedWordDefinitionBinding binding;

        DefinitionViewHolder(@NonNull IoIoFragmentSavedWordDefinitionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Set click listener for the item
            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(definitions.get(position));
                }
            });
        }

        /**
         * Bind the IO_Definition data to the UI elements.
         *
         * @param definition The IO_Definition item to bind.
         */
        void bind(IO_Definition definition) {
            binding.tvSavedWordDetail.setText(definition.getDefinition());
        }
    }
}
