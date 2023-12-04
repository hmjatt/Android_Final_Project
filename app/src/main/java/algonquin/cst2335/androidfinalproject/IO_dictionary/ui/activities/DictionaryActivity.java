package algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.SavedWordsFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.fragments.WordFragment;
import algonquin.cst2335.androidfinalproject.R;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_activity_dictionay);

//        NavigationView navigationView = findViewById(R.id.nvDictionary);
//
//        // Set up navigation item selected listener
//        navigationView.setNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_search:
//                    replaceFragment(new WordFragment());
//                    break;
//                case R.id.nav_saved_words:
//                    replaceFragment(new SavedWordsFragment());
//                    break;
//            }
//            return true;
//        });

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
}
