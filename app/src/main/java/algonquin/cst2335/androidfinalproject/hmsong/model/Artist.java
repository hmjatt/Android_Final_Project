// Artist.java
package algonquin.cst2335.androidfinalproject.hmsong.model;

/**
 * Represents an Artist with an ID.
 *
 * @version 1.0
 * @author Harmeet Matharoo
 */
public class Artist {

    private String id;

    /**
     * Constructs an Artist with the specified ID.
     *
     * @param id The ID of the artist.
     */
    public Artist(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the artist.
     *
     * @return The ID of the artist.
     */
    public String getId() {
        return id;
    }
}
