package algonquin.cst2335.androidfinalproject.hmsong.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.HmFragmentArtistSearchBinding;
import algonquin.cst2335.androidfinalproject.hmsong.data.database.FavoriteSongDatabase;
import algonquin.cst2335.androidfinalproject.hmsong.model.Album;
import algonquin.cst2335.androidfinalproject.hmsong.model.Artist;
import algonquin.cst2335.androidfinalproject.hmsong.model.FavoriteSong;
import algonquin.cst2335.androidfinalproject.hmsong.model.Song;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.AlbumAdapter;
import algonquin.cst2335.androidfinalproject.hmsong.ui.adapters.SongAdapter;

public class ArtistSearchFragment extends Fragment {

    private static final String ARG_SHOW_SEARCH = "arg_show_search";
    private boolean showSearch;

    private EditText etSearch;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private SongAdapter songAdapter;
    private List<Artist> artistList;
    private List<Album> albumList;
    private List<Song> songList;
    private FavoriteSongDatabase database;

    public ArtistSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HmFragmentArtistSearchBinding binding = HmFragmentArtistSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        etSearch = binding.etSearch;
        recyclerView = binding.recyclerView;

        artistList = new ArrayList<>();
        albumList = new ArrayList<>();
        songList = new ArrayList<>();

        albumAdapter = new AlbumAdapter(albumList, album -> searchTracks(album.getId(), album.getTitle(), album.getCoverUrl()));

        songAdapter = new SongAdapter(songList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(albumAdapter);


        // Set up the search button click listener
        binding.btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();

            try {
                query = URLEncoder.encode(query, "UTF-8");

                if (!query.isEmpty()) {
                    searchArtists(query);
                }

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        // Set up the "View Favorites" button
        Button viewFavoritesButton = view.findViewById(R.id.btnViewFavorites);
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
        database = FavoriteSongDatabase.getInstance(requireContext());

        return view;
    }



    private void navigateToFavoriteSongsFragment() {
        FavoriteSongsFragment favoriteSongsFragment = new FavoriteSongsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_SEARCH, showSearch);
        favoriteSongsFragment.setArguments(args);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSf, favoriteSongsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void searchArtists(String query) {
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

                                    // search for albums using the artist id
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

        Volley.newRequestQueue(requireContext()).add(request);
    }

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

        Volley.newRequestQueue(requireContext()).add(request);
    }

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

                                // Use the CREATOR to create a new instance of Song from a Parcel
                                Song song = Song.CREATOR.createFromParcel(null); // You might need to obtain a Parcel instance or use null depending on your needs
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
                                SongDetailFragment songDetailFragment = SongDetailFragment.newInstance(song, showSearch);

                                // Save the selected song to favorites before navigating to the detail fragment
                                saveSongToFavorites(song);

                                // Use FragmentTransaction to replace the current fragment with the song detail fragment
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
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

        Volley.newRequestQueue(requireContext()).add(request);
    }

    // Inside saveSongToFavorites method
    private void saveSongToFavorites(Song song) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // Create FavoriteSong object from Song
                FavoriteSong favoriteSong = new FavoriteSong(
                        song.getTitle(),
                        song.getDuration(),
                        song.getAlbumName(),
                        song.getAlbumCoverUrl()
                );

                // Save to database
                long result = database.favoriteSongDao().saveFavoriteSong(favoriteSong);

                if (result != -1) {
                    // Call showToast on the main thread
                    requireActivity().runOnUiThread(() -> showToast("Song saved to favorites"));
                } else {
                    // Call showToast on the main thread
                    requireActivity().runOnUiThread(() -> showToast("Failed to save song to favorites"));
                }

                return null;
            }
        }.execute();
    }

// Additional modifications as needed







    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            showToast("Network error. Status code: " + statusCode);
        } else if (error.getCause() instanceof TimeoutError) {
            showToast("Request timed out. Please try again.");
        } else {
            Log.e("artistError", "Error: " + error.toString());
            showToast("An error occurred. Please try again later.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
