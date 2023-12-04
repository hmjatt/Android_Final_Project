// DefinitionsAdapter.java
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


public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionsAdapter.DefinitionViewHolder> {

    private List<Definition> definitions = new ArrayList<>();

    // Constructor to accept initial data
    public DefinitionsAdapter(List<Definition> definitions) {
        this.definitions = definitions;
    }


    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_fragment_word_definition, parent, false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        Definition definition = definitions.get(position);
        holder.tvDefinition.setText(definition.getDefinition());
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public void updateData(List<Definition> newDefinitions) {
        definitions.clear();
        definitions.addAll(newDefinitions);
        notifyDataSetChanged();
    }

    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDefinition;

        DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvWordDetail);
        }
    }
}
