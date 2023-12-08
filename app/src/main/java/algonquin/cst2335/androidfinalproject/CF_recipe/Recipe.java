package algonquin.cst2335.androidfinalproject.CF_recipe;

public class Recipe {

    // Existing fields

    private String id;
    private String imageType;
    private String Title;


    public String getTitle(){
        return Title;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
