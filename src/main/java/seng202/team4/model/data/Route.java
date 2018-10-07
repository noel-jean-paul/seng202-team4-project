package seng202.team4.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for holding a route that is displayed in google maps.
 */
public class Route {
    private List<Position> route = new ArrayList<>();

    /**
     * Creates a new Route with the given latitude and longitude positions.
     *
     * @param positions An ArrayList of portions that make up the Route.
     */
    public Route(ArrayList<Position> positions) {
        route.addAll(positions);
    }

    /**
     * Creates a JSON String of the Route.
     *
     * @return JSON String of the Route.
     */
    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
