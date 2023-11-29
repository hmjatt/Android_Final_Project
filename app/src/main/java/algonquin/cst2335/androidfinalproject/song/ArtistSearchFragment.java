package algonquin.cst2335.androidfinalproject.song;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

// ArtistSearchFragment.java
public class ArtistSearchFragment extends Fragment {
    private EditText etSearch;
    private RecyclerView recyclerView;
    private ArtistAdapter adapter;
    private List<Artist> artistList;

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
        adapter = new ArtistAdapter(artistList, new ArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Artist artist) {
                // Handle item click
                // Show details or perform other actions
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // TODO: Implement artist search logic

        return view;
    }

    // Add this method in ArtistSearchFragment.java
    private void searchArtists(String query) {
        String url = "https://api.deezer.com/search/artist/?q=" + query;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response and update the RecyclerView
                        // Update artistList and call adapter.notifyDataSetChanged()
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void parseAndDisplayArtists(JSONArray response) {
        // Parse the JSON response and update the RecyclerView
        artistList.clear();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject artistObject = response.getJSONObject(i);
                String artistId = artistObject.getString("id");
                String artistName = artistObject.getString("name");

                Artist artist = new Artist(artistId, artistName);
                artistList.add(artist);
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}