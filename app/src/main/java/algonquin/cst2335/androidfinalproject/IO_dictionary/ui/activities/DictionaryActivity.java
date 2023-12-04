// DictionaryActivity.java

package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import algonquin.cst2335.androidfinalproject.IO_dictionary.model.Word;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.adapters.WordsAdapter;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.WordDetailFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.WordFragment;
import algonquin.cst2335.androidfinalproject.R;

public class DictionaryActivity extends AppCompatActivity implements WordsAdapter.OnWordClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_activity_dictionay);

        // Display the default fragment when the activity is created
        replaceFragment(new WordFragment());
    }

    private void replaceFragment(Fragment fragment) {
        // Replace the current fragment with the selected one
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle navigation drawer toggle
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close the activity when the back arrow is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWordClick(Word word) {
        // Handle item click, load WordDetailFragment with the selected word
        // Use a callback or communication method to communicate with the activity
        // and load the WordDetailFragment.
        // For now, use logs to test the click event.
        Log.d("DictionaryActivity", "Word clicked: " + word.getWord());

        // Replace the current fragment with WordDetailFragment
        WordDetailFragment wordDetailFragment = new WordDetailFragment();

        // Pass the selected word to WordDetailFragment using arguments
        Bundle bundle = new Bundle();
        bundle.putParcelable(WordDetailFragment.ARG_SELECTED_WORD, (Parcelable) word);
        wordDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, wordDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
