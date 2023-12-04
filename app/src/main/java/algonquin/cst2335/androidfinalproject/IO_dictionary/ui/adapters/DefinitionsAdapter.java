// io_dictionary.ui.adapters.DefinitionsAdapter
package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io_dictionary.model.Definition;

public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionsAdapter.ViewHolder> {
    private List<Definition> definitions;

    public DefinitionsAdapter(List<Definition> definitions) {
        this.definitions = definitions;
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
