package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Definition;
import algonquin.cst2335.androidfinalproject.R;

public class SavedWordDefinitionAdapter extends RecyclerView.Adapter<SavedWordDefinitionAdapter.DefinitionViewHolder> {

    private List<Definition> definitions = new ArrayList<>();

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_fragment_saved_word_definition, parent, false);
        return new DefinitionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        Definition definition = definitions.get(position);
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

        void bind(Definition definition) {
            tvDefinition.setText(definition.getDefinition());
            // TODO: Customize the binding as needed
        }
    }
}
