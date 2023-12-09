package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.R;

/**
 * Adapter class for displaying a list of words in a RecyclerView.
 *
 * @author Iuliia Obukhova
 * @version 1.0
 */
public class IO_WordsAdapter extends RecyclerView.Adapter<IO_WordsAdapter.WordViewHolder> {

    private List<IO_Word> words;
    private OnWordClickListener listener;

    /**
     * Constructor to initialize the adapter with a list of words and a word click listener.
     *
     * @param words    The list of words.
     * @param listener The listener for word click events.
     */
    public IO_WordsAdapter(List<IO_Word> words, OnWordClickListener listener) {
        this.words = words;
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_word, parent, false);
        return new WordViewHolder(itemView);
    }

    /**
     * Called to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        IO_Word word = words.get(position);
        holder.bind(word);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setWords(List<IO_Word> words) {
    }

    /**
     * Setter method for the word click listener.
     *
     * @param listener The word click listener to be set.
     */
    public void setOnWordClickListener(OnWordClickListener listener) {
        this.listener = listener;
    }

    /**
     * Setter method for updating the list of saved words and notifying the adapter.
     *
     * @param savedWords The updated list of saved words.
     */
    public void setSavedWords(List<IO_Word> savedWords) {
        this.words = savedWords;
        notifyDataSetChanged();
    }

    /**
     * Interface definition for a callback to be invoked when a word is clicked.
     */
    public interface OnWordClickListener {
        void onWordClick(IO_Word word);
    }

    // ViewHolder class to hold references to UI elements for each item in the RecyclerView.
    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvWord;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.textViewWords);
        }

        /**
         * Binds the data of a word to the corresponding UI elements.
         *
         * @param word The word object to bind.
         */
        void bind(IO_Word word) {
            tvWord.setText(word.getWord());

            // Set up click listener for the item view
            itemView.setOnClickListener(v -> {
                // Invoke the word click listener
                if (listener != null) {
                    listener.onWordClick(word);
                }
            });
        }
    }
}
