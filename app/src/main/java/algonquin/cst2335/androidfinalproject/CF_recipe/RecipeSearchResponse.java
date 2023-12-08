package algonquin.cst2335.androidfinalproject.CF_recipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("results")
    private List<Recipe> results;


    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
