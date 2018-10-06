package seng202.team4.controller;


import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


/**
 * Controller for the Activity Pop up screen.
 */
public class ActivityPopUpScreenController extends Controller {

    /** The anchor pane of the graph popup screen */
    @FXML
    private AnchorPane popupPrompt;

    /**The radio button, which when selected displays the heart rate graph */
    @FXML
    private RadioButton heartRateButton;

    /**The radio button, which when selected displays the distance graph */
    @FXML
    private RadioButton distanceButton;

    /**The graph displaying the distance data */
    @FXML
    private LineChart<?, ?> heartRateGraph;

    /** The graph displaying the heart rate data */
    @FXML
    private LineChart<?, ?> distanceGraph;

    /**The activity being displayed in the graphs */
    private Activity activity;

    /**
     *
     * @param applicationStateManager the application state manager of the program
     * @param activity the activity that will be displayed in the graphs
     */
    public ActivityPopUpScreenController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    /**
     * Initialises the popup, setting selected buttons in order to display correct graphs
     */
    @FXML
    public void initialize() {
        int size = activity.getRawData().size();
        if (size == 0) {
            heartRateGraph.setVisible(true);
            distanceGraph.setVisible(false);
            heartRateButton.setSelected(true);
            heartRateGraph.setTitle("There is no data that can be displayed");
            heartRateButton.setDisable(true);
            distanceButton.setDisable(true);

        } else {
            distanceGraph.setVisible(false);
            heartRateGraph.setVisible(true);
            heartRateButton.setSelected(true);
            displayHeartRateGraph();
        }
    }

    /**
     * Loads the heart rate graph with the correct data for the selected activity, by giving the heart rate at each minute
     * during the activity
     */
    @FXML
    public void displayHeartRateGraph() {
        heartRateGraph.getData().clear();
        heartRateGraph.layout();
        distanceButton.setSelected(false);
        distanceGraph.setVisible(false);
        heartRateGraph.setVisible(true);


        List<DataRow> dataRow = activity.getRawData();
        heartRateGraph.setTitle("Heart rate during " + activity.getName()); //sets the title of the graph
        LocalTime startTime = activity.getStartTime(); //gets the start time of activity

        long timeSeconds = 0;
        double timeSecDouble;
        double timeMinutes; //keeps track of the current minute
        long previousTime = -1; //sets previous time to -1, to avoid clashes with starting time which is always going to be 0
        XYChart.Series set1 = new XYChart.Series<>(); //creates the graph

        for (DataRow row : dataRow) {
            timeSeconds = Duration.between(startTime, row.getTime()).getSeconds(); //converts time to seconds
            if (timeSeconds != previousTime) {
                String strSec = Long.toString(timeSeconds);
                timeSecDouble = Double.parseDouble(strSec);
                timeMinutes = timeSecDouble / 60.0; //converts time to minutes
                timeMinutes = Double.parseDouble(new DecimalFormat("##.##").format(timeMinutes));
                String strLong = Double.toString(timeMinutes);

                set1.getData().add(new XYChart.Data(strLong, row.getHeartRate()));  //adds all data to the graph
            }
            previousTime = timeSeconds;
        }
        heartRateGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        heartRateGraph.getYAxis().setAnimated(false);
        heartRateGraph.getData().addAll(set1);
    }

    /**
     * Loads the distance travelled graph, giving the distance travelled between each data point
     *
     */
    @FXML
    void displayDistanceGraph() {
        distanceGraph.getData().clear();
        distanceGraph.layout();
        heartRateButton.setSelected(false);
        heartRateGraph.setVisible(false);
        distanceGraph.setVisible(true);


        distanceGraph.setTitle("Distance Travelled During " + activity.getName());  //sets the title
        List<DataRow> dataRow = activity.getRawData();

        XYChart.Series set2 = new XYChart.Series<>(); //creates the graph

        LocalTime startTime = dataRow.get(0).getTime();
        long timeSeconds;
        double timeSecDouble;
        double timeMinutes;

        for (int i = 0; i < dataRow.size(); i += 1) {
            List<DataRow> twoPoints = dataRow.subList(0, i + 1);
            double distance = seng202.team4.model.utilities.DataProcessor.totalDistance(twoPoints); //calculates the distance between the first point and the current point in the data list
            timeSeconds = Duration.between(startTime, dataRow.get(i).getTime()).getSeconds();   //gets seconds
            String strSec = Long.toString(timeSeconds);
            timeSecDouble = Double.parseDouble(strSec);
            timeMinutes = timeSecDouble / 60.0; //converts to minutes
            timeMinutes = Double.parseDouble(new DecimalFormat("##.##").format(timeMinutes));
            String strLong = Double.toString(timeMinutes);

            set2.getData().add(new XYChart.Data(strLong, distance));
        }

        distanceGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        distanceGraph.getYAxis().setAnimated(false);
        distanceGraph.getData().addAll(set2);
    }

    /**
     * calls the method to close the popup
     */
    @FXML
    public void cancel() {
        closePopup();
    }

    /** Closes the pop up. */
    private void closePopup () {
        applicationStateManager.closePopUP(popupPrompt);
    }


}