package seng202.team4.controller;


import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.utilities.DataProcessor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;


/**
 * Controller for the Activity Pop up screen.
 */
public class ActivityPopUpScreenController extends Controller {

    @FXML
    private AnchorPane popupPrompt;

    @FXML
    private RadioButton heartRateButton;

    @FXML
    private RadioButton distanceButton;

    @FXML
    private LineChart<?, ?> heartRateGraph;

    @FXML
    private LineChart<?, ?> distanceGraph;

    private Activity activity;

    /**
     * Constructor for the ActivityPopUpScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityPopUpScreenController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    @FXML
    public void initialize() {
        int size = activity.getRawData().size();
        if (size == 0) {
            heartRateGraph.setVisible(true);
            distanceGraph.setVisible(false);
            heartRateButton.setSelected(true);
            heartRateGraph.setTitle("There is no data that can be displayed");
            //distanceGraph.setTitle("There is no data that can be displayed");
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
     * //@ToDo could be improved by taking care of the case when the activity lasts less than a minute, currently just gives a single point
     * //@ToDo could also make it so heart rate y axis doesn't start from zero but from the minimum heart rate in the list
     */
    @FXML
    public void displayHeartRateGraph() {
        heartRateGraph.getData().clear();
        heartRateGraph.layout();
        distanceButton.setSelected(false);
        distanceGraph.setVisible(false);
        heartRateGraph.setVisible(true);


        //The next three lines will have to have the correct activity selected
        List<DataRow> dataRow = activity.getRawData();
        heartRateGraph.setTitle("Heart rate during " + activity.getName()); //sets the title of the
        LocalTime startTime = activity.getStartTime(); //gets the start time of activity

        long timeMinutes = 0;   //keeps track of the current minute
        long previousTime = -1; //sets previous time to -1, to avoid clashes with starting time which is always going to be 0
        XYChart.Series set1 = new XYChart.Series<>();

        for (DataRow row : dataRow) {
            timeMinutes = Duration.between(startTime, row.getTime()).toMinutes();
            if (timeMinutes != previousTime) {
                String strLong = Long.toString(timeMinutes);
                set1.getData().add(new XYChart.Data(strLong, row.getHeartRate()));
            }
            previousTime = timeMinutes;
        }
        heartRateGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        heartRateGraph.getYAxis().setAnimated(false);
        heartRateGraph.getData().addAll(set1);
    }

    /**
     * Loads the distance travelled graph, giving the distance travelled between each data point
     * @ToDo Rather than showing the distance between each data point, will be better to make it between each minute, more difficult
     */
    @FXML
    void displayDistanceGraph() {
        distanceGraph.getData().clear();
        distanceGraph.layout();
        heartRateButton.setSelected(false);
        heartRateGraph.setVisible(false);
        distanceGraph.setVisible(true);

        //Next two lines will need the correct activity parsed in
        distanceGraph.setTitle("Distance Travelled During " + activity.getName());
        List<DataRow> dataRow = activity.getRawData();

        XYChart.Series set2 = new XYChart.Series<>();
        if (dataRow.size() > 50) {
            for (int i = 0; i < dataRow.size(); i += 10) {
                List<DataRow> twoPoints = dataRow.subList(0, i + 1);
                double distance = seng202.team4.model.utilities.DataProcessor.totalDistance(twoPoints);
                String strInt = Integer.toString(i);
                set2.getData().add(new XYChart.Data(strInt, distance));
            }
        } else {
            for (int i = 0; i < dataRow.size(); i += 1) {
                List<DataRow> twoPoints = dataRow.subList(0, i + 1);
                double distance = seng202.team4.model.utilities.DataProcessor.totalDistance(twoPoints);
                String strInt = Integer.toString(i);
                set2.getData().add(new XYChart.Data(strInt, distance));
            }
        }
        distanceGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        distanceGraph.getYAxis().setAnimated(false);
        distanceGraph.getData().addAll(set2);
    }


    @FXML
    public void cancel() {
        closePopup();
    }

    /** Closes the prompt pop up. */
    private void closePopup () {
        applicationStateManager.closePopUP(popupPrompt);
    }


}