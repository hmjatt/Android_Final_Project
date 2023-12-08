package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class IO_SingleDefinitionAdapter extends RecyclerView.Adapter<IO_SingleDefinitionAdapter.DefinitionViewHolder> {

    private List<String> definitions;
    private OnDeleteClickListener onDeleteClickListener;

    public IO_SingleDefinitionAdapter(List<String> definitions, OnDeleteClickListener onDeleteClickListener) {
        this.definitions = definitions;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_io_fragment_single_definition, parent, false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        String definition = definitions.get(position);
        holder.tvDefinition.setText(definition);

        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

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
