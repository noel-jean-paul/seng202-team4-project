package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;

import java.util.List;


/**
 * Controller for the Home Tab. Displays a bar chart of the distances of recent activities.
 * Allows toggle between walking and running data
 */
public class HomeTabController extends Controller {

    /** The distance bar graph on the home tab*/
    @FXML
    private BarChart<?, ?> distanceBarGraph;

    /** The text that is displayed when there are no activities to display in the bar chart*/
    @FXML
    private Text noDataText;

    /** The radio button allowing view of all activities in the bar chart*/
    @FXML
    private RadioButton allDataButton;

    /** The radio button allowing view of all walking activities in the bar chart*/
    @FXML
    private RadioButton walkDataButton;

    /** The radio button allowing view of all running activities in the bar chart*/
    @FXML
    private RadioButton runDataButton;



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
     * every time the home tab is clicked, it clears the old bar graph, and loads it again with any new updated data. This function doesn't
     * deal with the behaviour of the graph when the walk or run buttons are clicked (see below for that function)
     */
    public void loadData() {
        runDataButton.setSelected(false);
        walkDataButton.setSelected(false);
        allDataButton.setSelected(true);
        List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();
        distanceBarGraph.getData().clear();
        distanceBarGraph.layout();
        XYChart.Series set1 = new XYChart.Series<>();

        if (activityList.size() == 0) {
            noDataText.setVisible(true); //if there is no data to display, then show this message to the user
        } else {
            noDataText.setVisible(false);
            for (int i = 0; i < activityList.size() && i < 5; i += 1) {
                set1.getData().add(new XYChart.Data(activityList.get(i).getName(), activityList.get(i).getDistance()));
            }
        }
        distanceBarGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last name value in the x axis was loaded
        distanceBarGraph.getYAxis().setAnimated(false);
        distanceBarGraph.getData().addAll(set1);
    }

    /**
     * Displays the graph of the last five activities of all types
     */
    @FXML
    void filterAll() {
        runDataButton.setSelected(false);
        walkDataButton.setSelected(false);
        allDataButton.setSelected(true);
        loadData();
    }

    /**
     * Displays the graph of up to five of the last activities of walk type
     */
    @FXML
    void filterWalk() {
        runDataButton.setSelected(false);
        walkDataButton.setSelected(true);
        allDataButton.setSelected(false);
        displayGraph(ActivityType.Walk);
    }

    /**
     * Displays the graph of up to five of the last activities of run type
     */
    @FXML
    void filterRun() {
        walkDataButton.setSelected(false);
        runDataButton.setSelected(true);
        allDataButton.setSelected(false);
        displayGraph(ActivityType.Run);
    }

    /**
     * The method which populates the graph on the home page with either walk or run data
     * @param type is the type of the activity, whether walk or run
     */
    void displayGraph(ActivityType type) {
        distanceBarGraph.getData().clear();
        distanceBarGraph.layout();
        List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();

        XYChart.Series dataSet = new XYChart.Series<>();

        if (activityList.size() == 0) {
            noDataText.setVisible(true); //if there is no data to display, then show this message to the user
        } else {
            noDataText.setVisible(false);
            int counter = 0;
            for (int i = 0; i < activityList.size() && counter < 5; i += 1) {
                if (activityList.get(i).getType() == type) {
                    dataSet.getData().add(new XYChart.Data(activityList.get(i).getName(), activityList.get(i).getDistance()));
                    counter++;
                }
            }
            if (counter == 0) {
                noDataText.setVisible(true); //if there is no data to display, then show this message to the user
            }
        }
        distanceBarGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last name value in the x axis was loaded
        distanceBarGraph.getYAxis().setAnimated(false);
        distanceBarGraph.getData().addAll(dataSet);
    }
}
