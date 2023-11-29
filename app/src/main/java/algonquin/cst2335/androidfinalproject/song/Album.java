package algonquin.cst2335.androidfinalproject.song;


public class Album {
    private String id;
    private String title;
    private String coverUrl;

    public Album(String id, String title, String coverUrl) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
