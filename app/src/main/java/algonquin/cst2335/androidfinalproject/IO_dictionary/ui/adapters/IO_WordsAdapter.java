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

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.R;

public class IO_WordsAdapter extends RecyclerView.Adapter<IO_WordsAdapter.WordViewHolder> {

    private List<IO_Word> words;
    private OnWordClickListener listener;

    public IO_WordsAdapter(List<IO_Word> words, OnWordClickListener listener) {
        this.words = words;
        this.listener = listener;
    }


    public void setWords(List<IO_Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        IO_Word word = words.get(position);
        holder.bind(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public interface OnWordClickListener {
        void onWordClick(IO_Word word);


    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvWord;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.textViewWords);
        }

        void bind(IO_Word word) {
            tvWord.setText(word.getWord());
            // Add code to bind data to additional views if needed
            // For example, ImageView, additional TextViews, etc.

            itemView.setOnClickListener(v -> {
                // Log a message when a word is clicked
                Log.d("WordsAdapter", "Word clicked: " + word.getWord());

                // Notify the listener about the click event
                listener.onWordClick(word);


            });
        }
    }
}
