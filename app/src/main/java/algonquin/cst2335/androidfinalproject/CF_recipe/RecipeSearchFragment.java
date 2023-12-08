package algonquin.cst2335.androidfinalproject.CF_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeSearchFragment extends Fragment {

    private EditText editTextRecipeName;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private List<Recipe> recipes;
    private RecipeApiService apiService;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cf_fragment_recipe_search, container, false);

        // Initialize UI elements and set listeners
        editTextRecipeName = view.findViewById(R.id.editTextRecipeName);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(RecipeApiService.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipes = new ArrayList<>();
        adapter = new RecyclerViewAdapter(recipes);
        recyclerView.setAdapter(adapter);

        buttonSearch.setOnClickListener(v -> searchRecipes());

        return view;
    }

    private void searchRecipes() {
        String recipeName = editTextRecipeName.getText().toString().trim();
        if (!recipeName.isEmpty()) {
            Call<RecipeSearchResponse> call = apiService.searchRecipes(recipeName, "6094350a0074428eaa8e5b351dec96b1");
            call.enqueue(new Callback<RecipeSearchResponse>() {
                @Override
                public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                    if (response.isSuccessful()) {
                        updateRecipeList(response.body().getResults());
                    } else {
                        handleApiError();
                    }
                }

                @Override
                public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                    handleApiFailure(t);
                }
            });
        } else {
            Toast.makeText(getContext(), "Please enter a recipe name", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRecipeList(List<Recipe> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
        adapter.notifyDataSetChanged();
    }

    private void handleApiError() {
        Toast.makeText(getContext(), "Failed to get recipe data", Toast.LENGTH_SHORT).show();
    }

    private void handleApiFailure(Throwable t) {
        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
