package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

    private List<IO_Word> partOfSpeeches;


    public IO_SavedWordsAdapter(List<IO_Word> savedWords, List<IO_Word> partOfSpeeches, OnSavedWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
        this.partOfSpeeches = partOfSpeeches;
        Log.d("SavedWordsAdapter", "Adapter initialized");
    }

    public void setSavedWords(List<IO_Word> savedWords, List<IO_Word> partOfSpeeches) {
        this.savedWords = savedWords;
        this.partOfSpeeches = partOfSpeeches;
        notifyDataSetChanged();
        Log.d("SavedWordsAdapter", "Saved words updated. New count: " + savedWords.size());
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        IO_Word savedWord = savedWords.get(position);
        holder.bind(savedWord);
        Log.d("SavedWordsAdapter", "onBindViewHolder called for position: " + position);
    }

    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.io_io_fragment_saved_words, parent, false);

        SavedWordViewHolder viewHolder = new SavedWordViewHolder(itemView);
        Log.d("SavedWordsAdapter", "onCreateViewHolder called");
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        int itemCount = savedWords.size();
        Log.d("SavedWordsAdapter", "getItemCount called. Count: " + itemCount);
        return itemCount;
    }

    public interface OnSavedWordClickListener {
        void onSavedWordClick(IO_Word savedWord);
    }

    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSavedWord;

        private final TextView tvPartOfSpeech;


        SavedWordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSavedWord = itemView.findViewById(R.id.textViewSavedWords);
            tvPartOfSpeech = itemView.findViewById(R.id.textViewPartOfSpeech);
            Log.d("SavedWordsAdapter", "SavedWordViewHolder created");
        }

        void bind(IO_Word savedWord) {
            tvSavedWord.setText(savedWord.getWord());
            tvPartOfSpeech.setText(savedWord.getPartOfSpeech());
            itemView.setOnClickListener(v -> {
                // ...

                // Notify the listener about the click event
                listener.onSavedWordClick(savedWord);
                Log.d("SavedWordsAdapter", "Item clicked: " + savedWord.getWord());
            });
        }
    }
}
