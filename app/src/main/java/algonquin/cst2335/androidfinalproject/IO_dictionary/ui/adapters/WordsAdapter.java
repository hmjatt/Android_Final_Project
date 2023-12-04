// WordsAdapter.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.R;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordViewHolder> {

    private List<Word> words;

    public WordsAdapter(List<Word> words, OnWordClickListener listener) {
        this.words = words;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_fragment_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.bind(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    private OnWordClickListener listener;

    public interface OnWordClickListener {
        void onWordClick(Word word);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvWord;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.recyclerViewWords);
        }

        void bind(Word word) {
            tvWord.setText(word.getWord());
            itemView.setOnClickListener(v -> {
                // Log a message when a word is clicked
                Log.d("WordsAdapter", "Word clicked: " + word.getWord());

                // Notify the listener about the click event
                listener.onWordClick(word);
            });
        }
    }
}
