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

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_SavedWord;
import algonquin.cst2335.androidfinalproject.R;

public class IO_SavedWordsAdapter extends RecyclerView.Adapter<IO_SavedWordsAdapter.SavedWordViewHolder> {

    private List<IO_SavedWord> savedWords;
    private OnSavedWordClickListener listener;

    public IO_SavedWordsAdapter(List<IO_SavedWord> savedWords, OnSavedWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
    }

    public void setSavedWords(List<IO_SavedWord> savedWords) {
        this.savedWords = savedWords;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_saved_words, parent, false);
        return new SavedWordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        IO_SavedWord savedWord = savedWords.get(position);
        holder.bind(savedWord);
    }

    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    public interface OnSavedWordClickListener {
        void onSavedWordClick(IO_SavedWord savedWord);
    }

    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSavedWord;

        SavedWordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSavedWord = itemView.findViewById(R.id.textViewSavedWords);
        }

        void bind(IO_SavedWord savedWord) {
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
