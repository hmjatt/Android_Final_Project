package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;// Import necessary libraries

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordsBinding;

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

    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IoIoFragmentSavedWordsBinding binding = IoIoFragmentSavedWordsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SavedWordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        IO_Word savedWord = savedWords.get(position);
        holder.bind(savedWord);
    }

    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    public interface OnSavedWordClickListener {
        void onSavedWordClick(IO_Word savedWord);
    }

    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final IoIoFragmentSavedWordsBinding binding;

        SavedWordViewHolder(@NonNull IoIoFragmentSavedWordsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(IO_Word savedWord) {
            binding.textViewSavedWords.setText(savedWord.getWord());
            itemView.setOnClickListener(v -> listener.onSavedWordClick(savedWord));
        }
    }
}
