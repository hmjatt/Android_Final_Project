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

public class IO_SavedWordDefinitionAdapter extends RecyclerView.Adapter<IO_SavedWordDefinitionAdapter.DefinitionViewHolder> {

    private List<IO_Definition> definitions = new ArrayList<>();

    public void setDefinitions(List<IO_Definition> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_saved_word_definition, parent, false);
        IO_SavedWordDefinitionAdapter.DefinitionViewHolder viewHolder = new IO_SavedWordDefinitionAdapter.DefinitionViewHolder(itemView);
        return viewHolder;
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

    class DefinitionViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDefinition;

        DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvSavedWordDetail);
        }

        void bind(IO_Definition definition) {
            tvDefinition.setText(definition.getDefinition());

            // TODO: Customize the binding as needed
        }
    }
}
