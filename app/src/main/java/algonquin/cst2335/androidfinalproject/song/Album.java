package algonquin.cst2335.androidfinalproject.song;


public class Album {
    private String id;
    private String title;
    private String coverUrl;
    private String trackList;

    public Album(String id, String title, String coverUrl, String trackList) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.trackList = trackList;
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

    public String getTrackList() {
        return trackList;
    }

}
