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

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.R;

public class IO_SavedWordsAdapter extends RecyclerView.Adapter<IO_SavedWordsAdapter.SavedWordViewHolder> {

    private List<IO_Word> savedWords;
    private OnSavedWordClickListener listener;

    public IO_SavedWordsAdapter(List<IO_Word> savedWords, OnSavedWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
    }

    public void setSavedWords(List<IO_Word> savedWords) {
        this.savedWords = savedWords;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        IO_Word savedWord = savedWords.get(position);
        holder.bind(savedWord);
    }

    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_saved_words, parent, false);
        return new SavedWordViewHolder(itemView);
    }

    public interface OnSavedWordClickListener {
        void onSavedWordClick(IO_Word savedWord);
    }

    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSavedWord;

        SavedWordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSavedWord = itemView.findViewById(R.id.textViewSavedWords);
        }

        void bind(IO_Word savedWord) {
            tvSavedWord.setText(savedWord.getWord());
            itemView.setOnClickListener(v -> {
                // ...

                // Notify the listener about the click event
                listener.onSavedWordClick(savedWord);
            });
        }
    }
}
