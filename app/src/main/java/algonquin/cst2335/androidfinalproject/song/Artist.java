package algonquin.cst2335.androidfinalproject.song;

// Artist.java

public class Artist {
    private String id;

    public Artist(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    // You can also include setters if needed

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                '}';
    }
}
