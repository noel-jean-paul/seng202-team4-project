package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import seng202.team4.Utilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataLoader;


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
        //The values below are just test values
        XYChart.Series set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("Walk in woods", 1.5));
        set1.getData().add(new XYChart.Data("fun run", 2));
        set1.getData().add(new XYChart.Data("Run through town", 12));
        set1.getData().add(new XYChart.Data("Marathon", 42));
        distanceBarGraph.getData().addAll(set1);
    }
}
