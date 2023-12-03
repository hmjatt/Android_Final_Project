
package algonquin.cst2335.androidfinalproject.hmsong.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmActivitySongSearchBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.Album;
import algonquin.cst2335.androidfinalproject.hmsong.model.Artist;
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.AlbumAdapter;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.SongAdapter;
import algonquin.cst2335.androidfinalproject.hmsong.ui.fragments.FavoriteSongsFragment;
import algonquin.cst2335.androidfinalproject.hmsong.ui.fragments.SongDetailFragment;


/**
 * The SongSearchActivity class is responsible for handling song search functionality.
 * It allows users to search for artists, view their albums, and explore the tracks in each album.
 * Users can also view their favorite songs and get help information.
 *
 * @author Harmeet Matharoo
 * @version 1.0
 */
public class SongSearchActivity extends AppCompatActivity {

    private static final String ARG_SHOW_SEARCH = "arg_show_search";
    private EditText etSearch;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private SongAdapter songAdapter;
    private List<Artist> artistList;
    private List<Album> albumList;
    private List<Song> songList;
    private FavoriteSongDatabase database;
    private Toolbar toolbar;

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
        HmActivitySongSearchBinding binding = HmActivitySongSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Grab the no app selected textview
        TextView tvNoArtistSelected = binding.tvNoArtistSelected;

        // Show no app selected textview When no app is selected
        tvNoArtistSelected.setVisibility(View.VISIBLE);

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        // Retrieve the last searched term from SharedPreferences
        String lastSearchedTerm = sharedPreferences.getString("searchTerm", "");

        // Replace '+' with space for a more readable display
        lastSearchedTerm = lastSearchedTerm.replace("+", " ");

        // Use the last searched term to set the EditText
        etSearch = binding.etSearch;
        etSearch.setText(lastSearchedTerm);

        recyclerView = binding.recyclerView;
        artistList = new ArrayList<>();
        albumList = new ArrayList<>();
        songList = new ArrayList<>();

        albumAdapter = new AlbumAdapter(albumList, album -> searchTracks(album.getId(), album.getTitle(), album.getCoverUrl()));
        songAdapter = new SongAdapter(songList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(albumAdapter);

        // Set up the search button click listener
        binding.btnSearch.setOnClickListener(v -> {

            // Show no app selected textview When no app is selected
            tvNoArtistSelected.setVisibility(View.GONE);

            String query = etSearch.getText().toString().trim();

            try {
                query = URLEncoder.encode(query, "UTF-8");

                if (!query.isEmpty()) {
                    // Save the current search term to SharedPreferences
                    sharedPreferences.edit().putString("searchTerm", query).apply();
                    searchArtists(query);
                }

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        // Set up the "View Favorites" button
        Button viewFavoritesButton = findViewById(R.id.btnViewFavorites);
        viewFavoritesButton.setOnClickListener(v -> navigateToFavoriteSongsFragment());

        // Add an OnEditorActionListener to the EditText
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                // Perform the same action as the search button click
                binding.btnSearch.performClick();
                return true;
            }
            return false;
        });

        // Initialize Room database
        database = FavoriteSongDatabase.getInstance(this);

        // Set up the Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the toolbar
        setTitle(R.string.song_search);

        ActionBar actionBar = getSupportActionBar();

        // add home icon to toolbar
        if(actionBar != null){
            Drawable homeIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.hm_home, getTheme());
            Bitmap bitmap = ((BitmapDrawable) homeIcon).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }

        // Add a TouchListener to the main layout to hide the keyboard when touched
        View mainLayout = findViewById(R.id.song_activity_main_ele); // Replace with the ID of your main layout
        mainLayout.setOnTouchListener((v, event) -> {
            hideKeyboard();
            return false;
        });
    }

    // Add this method to hide the keyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
    }

    /**
     * Navigate to the FavoriteSongsFragment if the list of favorite songs is not empty.
     * Display a message if no favorite songs are found.
     */
    private void navigateToFavoriteSongsFragment() {
        hideKeyboard();
        new Thread(() -> {
            // Check if the list of favorite songs is empty
            if (!database.favoriteSongDao().getFavoriteSongs().isEmpty()) {
                // The list of favorite songs is not empty, navigate to the FavoriteSongsFragment
                runOnUiThread(() -> {
                    FavoriteSongsFragment favoriteSongsFragment = new FavoriteSongsFragment();
                    Bundle args = new Bundle();
                    args.putBoolean(ARG_SHOW_SEARCH, false);
                    favoriteSongsFragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerSf, favoriteSongsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } else {
                showToast("No favorite songs found.");
            }
        }).start();
    }

    /**
     * Display a toast message on the UI thread.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    /**
     * Search for artists based on the provided query.
     * Update the UI with the retrieved artist information.
     *
     * @param query The artist search query.
     */
    private void searchArtists(String query) {
        hideKeyboard();

        String url = "https://api.deezer.com/search/artist/?q=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    artistList.clear();

                    if (response.length() > 0) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            if (dataArray.length() > 0) {
                                JSONObject firstArtist = dataArray.getJSONObject(0);
                                String artistId = firstArtist.optString("id");

                                if (!artistId.isEmpty()) {
                                    Artist artist = new Artist(artistId);
                                    artistList.add(artist);

                                    // Search for albums using the artist id
                                    searchAlbums(artistId);

                                } else {
                                    showToast("Invalid JSON structure for artist data");
                                }
                            } else {
                                showToast("No artist data found in the response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error parsing JSON");
                        }
                    }
                },
                error -> handleVolleyError(error));

        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Search for albums based on the provided artist id.
     * Update the UI with the retrieved album information.
     *
     * @param artistId The id of the artist.
     */
    private void searchAlbums(String artistId) {
        String url = "https://api.deezer.com/artist/" + artistId + "/albums";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    albumList.clear();

                    if (response.length() > 0) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject albumObject = dataArray.getJSONObject(i);
                                String albumId = albumObject.getString("id");
                                String albumTitle = albumObject.getString("title");
                                String albumCoverUrl = albumObject.getString("cover_medium");
                                Album album = new Album(albumId, albumTitle, albumCoverUrl);

                                // Check if the album already exists in the list
                                if (!albumList.contains(album)) {
                                    // Add the new album to the list
                                    albumList.add(album);
                                }
                            }

                            // Update the RecyclerView with albums
                            recyclerView.setAdapter(albumAdapter);
                            albumAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> handleVolleyError(error));

        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Search for tracks based on the provided album id.
     * Update the UI with the retrieved track information.
     *
     * @param albumId       The id of the album.
     * @param albumTitle    The title of the album.
     * @param albumCoverUrl The URL of the album cover.
     */
    private void searchTracks(String albumId, String albumTitle, String albumCoverUrl) {
        String url = "https://api.deezer.com/album/" + albumId + "/tracks";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    songList.clear();

                    if (response.length() > 0) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject trackObject = dataArray.getJSONObject(i);
                                String songTitle = trackObject.getString("title");
                                String duration = trackObject.getString("duration");
                                String albumName = albumTitle;
                                String albumCover = albumCoverUrl;

                                Song song = new Song();
                                song.setTitle(songTitle);
                                song.setDuration(duration);
                                song.setAlbumName(albumName);
                                song.setAlbumCoverUrl(albumCover);
                                songList.add(song);
                            }

                            // Update the RecyclerView with songs
                            recyclerView.setAdapter(songAdapter);
                            songAdapter.notifyDataSetChanged();

                            // Set the song listener for the song adapter
                            songAdapter.setOnItemClickListener(song -> {
                                // Launch the song detail fragment with the selected song and showSearch parameter
                                SongDetailFragment songDetailFragment = SongDetailFragment.newInstance(song);

                                // Use FragmentTransaction to replace the current fragment with the song detail fragment
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerSf, songDetailFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            });

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> handleVolleyError(error));

        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Handle Volley errors and display appropriate messages.
     *
     * @param error The VolleyError to handle.
     */
    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            showToast("Network error. Status code: " + statusCode);
        } else if (error.getCause() instanceof TimeoutError) {
            showToast("Request timed out. Please try again.");
        } else {
            showToast("An error occurred. Please try again later.");
        }
    }

    /**
     * Create the options menu in the toolbar.
     *
     * @param menu The menu to create.
     * @return True to display the menu, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hm_menu_help, menu);
        return true;
    }

    /**
     * Handle options item selection in the toolbar.
     *
     * @param item The selected MenuItem.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == android.R.id.home) {
            // Handle the home button press
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                // If there are fragments in the back stack, pop the fragment
                getSupportFragmentManager().popBackStack();
            } else {
                // If the back stack is empty, navigate to MainActivity
                navigateBackToMainActivity();
            }
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            // Correct resource ID for the action help
            showHelpDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Navigate back to the MainActivity.
     */
    private void navigateBackToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Show the help dialog to provide additional information.
     */
    private void showHelpDialog() {
        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.hm_help_dialog_layout, null);

        // Set title and message
        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        titleTextView.setText(R.string.help_dialog_title);

        // Create and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}