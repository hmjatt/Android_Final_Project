package algonquin.cst2335.androidfinalproject.hmsong.ui.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.hmsong.ui.fragments.FavoriteSongsFragment;

// Inside FavoritesActivity
public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add a button or navigation option to view favorites
        Button viewFavoritesButton = findViewById(R.id.btnViewFavorites);

        viewFavoritesButton.setOnClickListener(v -> {
            // Navigate to FavoriteSongsFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerSf, new FavoriteSongsFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
