package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ProgressBar;


/**
 * Controller for the Home Tab.
 */
public class HomeTabController extends Controller {

    /** The distance bar graph on the home tab*/
    @FXML
    private BarChart<?, ?> distanceBarGraph;

    /**The goal progress bar of the home tab*/
    @FXML
    private ProgressBar goalProgressBar;

    /**
     * Constructor for the HomeTabController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public HomeTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /** Initializes the home tab. */
    @FXML
    public void initialize() {

    }
}
