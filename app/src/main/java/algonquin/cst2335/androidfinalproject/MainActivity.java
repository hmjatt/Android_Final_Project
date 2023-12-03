package algonquin.cst2335.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import algonquin.cst2335.androidfinalproject.IO_dictionary.DictionaryFragment;
import algonquin.cst2335.androidfinalproject.CF_recipe.RecipeSearchFragment;
import algonquin.cst2335.androidfinalproject.hmsong.ui.activities.SongSearchActivity;
import algonquin.cst2335.androidfinalproject.SK_sunrise.SunriseSunsetFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private boolean isSongSearchActivityLaunched = false;


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

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

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
            fragmentClass = DictionaryFragment.class;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
