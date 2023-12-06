
package algonquin.cst2335.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import algonquin.cst2335.androidfinalproject.CF_recipe.RecipeSearchFragment;
import algonquin.cst2335.androidfinalproject.IO_dictionary.ui.activities.IO_DictionaryActivity;
import algonquin.cst2335.androidfinalproject.SK_sunrise.ui.SunriseSunsetFragment;
import algonquin.cst2335.androidfinalproject.hmsong.ui.activities.SongSearchActivity;


/**
 * The MainActivity class is the main entry point of the application.
 * It manages the navigation drawer and handles the selection of different app features.
 *
 * @author Chris Francis
 * @author Harmeet Matharoo
 * @author Iuliia Obukhova
 * @author Sahyun Kang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private boolean isSongSearchActivityLaunched = false;

    private boolean isDictionaryActivityLaunched = false;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab the no app selected textview
        TextView tvNoAppSelected = findViewById(R.id.tvNoAppSelected);

        // Show no app selected textview When no app is selected
        tvNoAppSelected.setVisibility(View.VISIBLE);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Find our drawer view
        nvDrawer = findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    /**
     * Set up the ActionBarDrawerToggle for the navigation drawer.
     *
     * @return The configured ActionBarDrawerToggle.
     */
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    /**
     * Set up the navigation drawer content.
     *
     * @param navigationView The NavigationView to set up.
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    /**
     * Handle the selection of items in the navigation drawer.
     *
     * @param menuItem The selected MenuItem.
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;

        // Grab the no app selected textview
        TextView tvNoAppSelected = findViewById(R.id.tvNoAppSelected);

        // Hide no app selected textview When an app is selected
        tvNoAppSelected.setVisibility(View.GONE);

        if (menuItem.getItemId() == R.id.sunrise_sunset_lookup) {
            fragmentClass = SunriseSunsetFragment.class;
        } else if (menuItem.getItemId() == R.id.recipe_search) {
            fragmentClass = RecipeSearchFragment.class;
        } else if (menuItem.getItemId() == R.id.dictionary) {
            if (!isDictionaryActivityLaunched) {
                fragmentClass = IO_DictionaryActivity.class;
                Intent intent = new Intent(this, fragmentClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                isDictionaryActivityLaunched = true;
            } else {
                // If already launched, bring it to the front
                Intent intent = new Intent(this, IO_DictionaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        } else if (menuItem.getItemId() == R.id.song_search) {
            // Launch SongSearchActivity only if not already launched
            if (!isSongSearchActivityLaunched) {
                fragmentClass = SongSearchActivity.class;
                Intent intent = new Intent(this, fragmentClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                isSongSearchActivityLaunched = true;
            } else {
                // If already launched, bring it to the front
                Intent intent = new Intent(this, SongSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
            return; // Return to avoid further execution of the method
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
        }
    }

    /**
     * Handle options item selection.
     *
     * @param item The selected MenuItem.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your activity to start interacting with the user.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
