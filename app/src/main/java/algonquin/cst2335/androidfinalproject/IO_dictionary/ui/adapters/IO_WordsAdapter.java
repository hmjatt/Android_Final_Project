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

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Definition;
import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.R;

public class IO_WordsAdapter extends RecyclerView.Adapter<IO_WordsAdapter.WordViewHolder> {

    private List<IO_Word> words;

    private OnWordClickListener listener;
    private OnSaveButtonClickListener onSaveButtonClickListener;


    public IO_WordsAdapter(List<IO_Word> words, OnWordClickListener listener, OnSaveButtonClickListener saveButtonClickListener) {
        this.words = words;
        this.listener = listener;
        this.onSaveButtonClickListener = saveButtonClickListener;
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

    public void setOnWordClickListener(OnWordClickListener listener) {
        this.listener = listener;
    }

    public void setOnSaveButtonClickListener(OnSaveButtonClickListener saveButtonClickListener) {
        this.onSaveButtonClickListener = saveButtonClickListener;
    }

    public void setSavedWords(List<IO_Word> savedWords) {
        this.words = savedWords;
        notifyDataSetChanged();
    }

    // Define an interface for handling save button clicks
    public interface OnSaveButtonClickListener {
        void onSaveButtonClick(IO_Word word);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvWord;


        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.textViewWords);
        }

        void bind(IO_Word word) {
            tvWord.setText(word.getWord());


            itemView.setOnClickListener(v -> {
                Log.d("WordsAdapter", "Word clicked: " + word.getWord());
                listener.onWordClick(word);
            });

        }
    }


}
