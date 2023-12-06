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

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.R;


public class IO_DefinitionsAdapter extends RecyclerView.Adapter<IO_DefinitionsAdapter.DefinitionViewHolder> {

    private List<IO_Definition> definitions = new ArrayList<>();

    // Constructor to accept initial data
    public IO_DefinitionsAdapter(List<IO_Definition> definitions) {
        this.definitions = definitions;
    }


    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_io_fragment_word_definition, parent, false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        IO_Definition definition = definitions.get(position);
        holder.tvDefinition.setText(definition.getDefinition());
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public void updateData(List<IO_Definition> newDefinitions) {
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
