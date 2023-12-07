// IO_SavedWordDefinitionAdapter.java
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
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordDefinitionBinding;

public class IO_SavedWordDefinitionAdapter
        extends RecyclerView.Adapter<IO_SavedWordDefinitionAdapter.DefinitionViewHolder> {

    private List<IO_Definition> definitions = new ArrayList<>();
    private OnItemClickListener listener;

    public void setDefinitions(List<IO_Definition> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IoIoFragmentSavedWordDefinitionBinding binding =
                IoIoFragmentSavedWordDefinitionBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
        return new DefinitionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        IO_Definition definition = definitions.get(position);
        holder.bind(definition);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(IO_Definition definition);
    }

    class DefinitionViewHolder extends RecyclerView.ViewHolder {

        private final IoIoFragmentSavedWordDefinitionBinding binding;

        DefinitionViewHolder(@NonNull IoIoFragmentSavedWordDefinitionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {

                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(definitions.get(position));
                }
            });
        }

        void bind(IO_Definition definition) {
            binding.tvSavedWordDetail.setText(definition.getDefinition());
        }
    }
}
