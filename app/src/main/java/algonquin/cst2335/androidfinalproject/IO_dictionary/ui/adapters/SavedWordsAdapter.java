// SavedWordsAdapter.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.SavedWord;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.R;

public class SavedWordsAdapter extends RecyclerView.Adapter<SavedWordsAdapter.SavedWordViewHolder> {

    private List<SavedWord> savedWords;
    private OnSavedWordClickListener listener;

    public SavedWordsAdapter(List<SavedWord> savedWords, OnSavedWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_fragment_saved_words, parent, false);
        return new SavedWordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        SavedWord savedWord = savedWords.get(position);
        holder.bind(savedWord);
    }

    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    public interface OnSavedWordClickListener {
        void onSavedWordClick(SavedWord savedWord);
    }

    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSavedWord;

        SavedWordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSavedWord = itemView.findViewById(R.id.textViewSavedWords);
        }

        void bind(SavedWord savedWord) {
            tvSavedWord.setText(savedWord.getSavedWord());
            itemView.setOnClickListener(v -> {
                // Log a message when a saved word is clicked
                Log.d("SavedWordsAdapter", "Saved Word clicked: " + savedWord.getSavedWord());

                // Notify the listener about the click event
                listener.onSavedWordClick(savedWord);
            });
        }
    }
}
