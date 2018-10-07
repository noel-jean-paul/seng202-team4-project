package seng202.team4.model.data;

/**
 * Stores a Position as a latitude longitude pair.
 */
public class Position {
    public double lat;
    public double lng;

    /**
     * Creates a new Position.
     *
     * @param lat The latitude component of the Position.
     * @param lng The longitude component of the Position.
     */
    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}