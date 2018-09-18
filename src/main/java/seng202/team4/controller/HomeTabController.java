package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataLoader;

import java.util.ArrayList;
import java.util.List;


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

    @FXML
    private Text noDataText;


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

    /**
     * each time the home tab is clicked, it clears the old bar graph, and loads it again with any new updated data
     */
    public void loadData() {
        List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();
        distanceBarGraph.getData().clear();
        distanceBarGraph.layout();
        XYChart.Series set1 = new XYChart.Series<>();

        if (activityList.size() == 0) {
            noDataText.setVisible(true); //if there is no data to display, then show this message to the user
        } else {
            noDataText.setVisible(false);
            for (int i = activityList.size() - 1; i >= 0 && i > (activityList.size() - 1 - 5); i -= 1) {
                set1.getData().add(new XYChart.Data(activityList.get(i).getName(), activityList.get(i).getDistance()));
            }
        }
        distanceBarGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last name value in the x axis was loaded
        distanceBarGraph.getYAxis().setAnimated(false);
        distanceBarGraph.getData().addAll(set1);
    }
}
