// WordsAdapter.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.R;

public class IO_WordsAdapter extends RecyclerView.Adapter<IO_WordsAdapter.WordViewHolder> {

    private List<IO_Word> words;

    private List<IO_Word> partOfSpeeches;
    private OnWordClickListener listener;

    public IO_WordsAdapter(List<IO_Word> words, List<IO_Word> partOfSpeeches, OnWordClickListener listener) {
        this.words = words;
        this.partOfSpeeches = partOfSpeeches;
        this.listener = listener;

    }


    public void setWords(List<IO_Word> words, List<IO_Word> partOfSpeeches) {
        this.words = words;
        this.partOfSpeeches = partOfSpeeches;
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

    // Interface for handling the "Save" button click
    public interface OnSaveButtonClickListener {
        void onSaveButtonClick(IO_Word word);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvWord;
        private final TextView tvPartOfSpeech;

        private final Button btnSave;


        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.textViewWords);
            tvPartOfSpeech = itemView.findViewById(R.id.textViewPartOfSpeech);
            btnSave = itemView.findViewById(R.id.btnSaveWord);
        }

        void bind(IO_Word word) {
            tvWord.setText(word.getWord());
            tvPartOfSpeech.setText(word.getPartOfSpeech());

            // Additional logic to display multiple meanings if needed

            itemView.setOnClickListener(v -> {
                // Log a message when a word is clicked
                Log.d("WordsAdapter", "Word clicked: " + word.getWord());

                // Notify the listener about the click event
                listener.onWordClick(word);
            });

            // Add a click listener for the "Save" button
            btnSave.setOnClickListener(v -> {
                // Log a message when the "Save" button is clicked
                Log.d("WordsAdapter", "Save button clicked for word: " + word.getWord());

                // Call the method to save the word and its definitions to the database
                saveWordToDatabase(word);
            });

        }

        // New method to handle saving word and definitions
        private void saveWordToDatabase(IO_Word word) {
            // Check if the listener is not null and if the listener implements the saving interface
            if (listener != null && listener instanceof OnSaveButtonClickListener) {
                // Cast the listener to the saving interface
                OnSaveButtonClickListener saveButtonClickListener = (OnSaveButtonClickListener) listener;

                // Call the interface method to save the word and its definitions
                saveButtonClickListener.onSaveButtonClick(word);
            }
        }


    }
}
