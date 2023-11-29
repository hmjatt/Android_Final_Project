package algonquin.cst2335.androidfinalproject.song;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class ArtistSearchFragment extends Fragment {
    private EditText etSearch;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private List<Artist> artistList;
    private List<Album> albumList;

    public ArtistSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        artistList = new ArrayList<>();
        albumList = new ArrayList<>();

//        artistAdapter = new ArtistAdapter(artistList, artist -> searchAlbums(artist.getId()));
        albumAdapter = new AlbumAdapter(albumList, album -> searchTracks(album.getId()));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(artistAdapter);
        recyclerView.setAdapter(albumAdapter);

        // Set up the search button click listener
        view.findViewById(R.id.btnSearch).setOnClickListener(v -> {
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

        return view;
    }

    private void searchArtists(String query) {
        String url = "https://api.deezer.com/search/artist/?q=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Parse the JSON response and update the RecyclerView with the first artist
                    artistList.clear();

                    if (response.length() > 0) {
                        try {
                            // Extract the "data" array from the response
                            JSONArray dataArray = response.getJSONArray("data");

                            // Make sure the "data" array is not empty
                            if (dataArray.length() > 0) {
                                // Extract the first artist from the "data" array
                                JSONObject firstArtist = dataArray.getJSONObject(0);

                                // Extract required fields from the artist object
                                String artistId = firstArtist.optString("id");
//                                String artistName = firstArtist.optString("name");

                                // Validate if the required fields are present
                                if (!artistId.isEmpty()) {
                                    Artist artist = new Artist(artistId);
                                    artistList.add(artist);

                                    // search for albums using the artist id
                                    searchAlbums(artistId);

                                    // Update the RecyclerView with artists
//                                    recyclerView.setAdapter(artistAdapter);
//                                    artistAdapter.notifyDataSetChanged();

                                    // Update the RecyclerView with albums
//                                    recyclerView.setAdapter(albumAdapter);
//                                    albumAdapter.notifyDataSetChanged();

                                } else {
                                    // Handle the case where the expected fields are not present in the JSON
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

        // Add the request to the RequestQueue
        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void searchAlbums(String artistId) {
        String url = "https://api.deezer.com/artist/" + artistId + "/albums";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Parse the JSON response and update the RecyclerView with albums

                    // Clear the existing list of albums
                    albumList.clear();

                    if (response.length() > 0) {

                        // Extract the "data" array from the response
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                try {
                                    JSONObject albumObject = dataArray.getJSONObject(i); // Corrected line
                                    String albumId = albumObject.getString("id");
                                    String albumTitle = albumObject.getString("title");
                                    String albumCoverUrl = albumObject.getString("cover_medium");
                                    Album album = new Album(albumId, albumTitle, albumCoverUrl);

                                    // Check if the album already exists in the list
                                    if (!albumList.contains(album)) {
                                        // Add the new album to the list
                                        albumList.add(album);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

        // Add the request to the RequestQueue
        Volley.newRequestQueue(requireContext()).add(request);
    }




    private void searchTracks(String albumId) {
        // Implement the logic to search for tracks based on the albumId
        // This will involve making another API call to get the tracklist
        // and displaying the list of tracks in a new fragment or activity
    }

    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null) {
            // Network-related error
            int statusCode = error.networkResponse.statusCode;
            showToast("Network error. Status code: " + statusCode);
        } else if (error.getCause() instanceof TimeoutError) {
            // Timeout error
            showToast("Request timed out. Please try again.");
        } else {
            Log.e("artistError", "Error: " + error.toString());

            // Other types of errors
            showToast("An error occurred. Please try again later.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
