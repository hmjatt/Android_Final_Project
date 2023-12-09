package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.IO_Word;
import algonquin.cst2335.androidfinalproject.databinding.IoIoFragmentSavedWordsBinding;

/**
 * Adapter class for displaying a list of saved IO_Word items in a RecyclerView.
 *
 * @Author Iuliia Obukhova
 * @Version 1.0
 */
public class IO_SavedWordsAdapter extends RecyclerView.Adapter<IO_SavedWordsAdapter.SavedWordViewHolder> {

    // List to store saved IO_Word items
    private List<IO_Word> savedWords;
    private OnSavedWordClickListener listener;

    /**
     * Constructor to initialize the adapter with a list of saved IO_Word items and a click listener.
     *
     * @param savedWords The list of saved IO_Word items.
     * @param listener   The listener for saved word click events.
     */
    public IO_SavedWordsAdapter(List<IO_Word> savedWords, OnSavedWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
    }

    /**
     * Set the list of saved IO_Word items.
     *
     * @param savedWords The list of saved IO_Word items.
     */
    public void setSavedWords(List<IO_Word> savedWords) {
        this.savedWords = savedWords;
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @NonNull
    @Override
    public SavedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IoIoFragmentSavedWordsBinding binding =
                IoIoFragmentSavedWordsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SavedWordViewHolder(binding);
    }

    /**
     * Called to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull SavedWordViewHolder holder, int position) {
        IO_Word savedWord = savedWords.get(position);
        holder.bind(savedWord);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    /**
     * Interface definition for a callback to be invoked when a saved word in the adapter is clicked.
     */
    public interface OnSavedWordClickListener {
        void onSavedWordClick(IO_Word savedWord);
    }

    /**
     * ViewHolder class to hold references to UI elements for each item in the RecyclerView.
     */
    class SavedWordViewHolder extends RecyclerView.ViewHolder {

        private final IoIoFragmentSavedWordsBinding binding;

        SavedWordViewHolder(@NonNull IoIoFragmentSavedWordsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Bind the IO_Word data to the UI elements.
         *
         * @param savedWord The IO_Word item to bind.
         */
        void bind(IO_Word savedWord) {
            binding.textViewSavedWords.setText(savedWord.getWord());
            itemView.setOnClickListener(v -> listener.onSavedWordClick(savedWord));
        }
    }
}
