package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team4.App;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Position;
import seng202.team4.model.data.Route;

import java.util.ArrayList;

/** Controller for the maps popup. */
public class MapsController extends Controller {

    /** The root Node of the maps popup. */
    @FXML
    private AnchorPane mainPane;

    /** WebView that contains google maps */
    @FXML
    private WebView activityMap;

    /** Heading text of the popup. */
    @FXML
    private Text headingText;

    /** WebEngine for google maps */
    private WebEngine webEngine;



    /** Creates a new MapsController with the given ApplicationStateManager. */
    public MapsController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the MapsController by initializing the WebEngine and loads google maps. */
    @FXML
    public void initialize() {
        webEngine = activityMap.getEngine();
        webEngine.load(App.class.getResource("maps/map.html").toExternalForm());
    }

    /**
     * Generates a path/route
     * @param activity the activity for which a polyline is to be calculate on the map
     * @return a route(list of positions with lat/long as doubles)
     */
    private Route generateRoute(Activity activity) {
        ArrayList<Position> positionList = new ArrayList<Position>();
        for (DataRow data : activity.getRawData()) {
            positionList.add(new Position((data.getLatitude()), (data.getLongitude())));
        }
        Route newRoute = new Route((positionList));
        return newRoute;
    }

    /**
     * Initializes the map to display a route of the given Activity.
     *
     * @param activity The Activity to display on the map.
     */
    public void initMap(Activity activity) {
        headingText.setText(String.format("Map of '%s'", activity.getName()));
        Route newRoute = generateRoute(activity);
        displayRoute(newRoute);
    }

    /**
     * Displays a route on the map.
     *
     * @param newRoute The route of the map.
     */
    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    /**
     * Closes the popup.
     * Is called when the users clicks the close 'button'.
     */
    @FXML
    public void close() {
        applicationStateManager.closePopUP(mainPane);
    }
}
