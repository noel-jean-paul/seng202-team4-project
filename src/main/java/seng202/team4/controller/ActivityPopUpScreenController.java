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

    @FXML
    private NumberAxis heartRateYAxis;

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


        List<DataRow> dataRow = activity.getRawData();
        heartRateGraph.setTitle("Heart rate during " + activity.getName()); //sets the title of the
        LocalTime startTime = activity.getStartTime(); //gets the start time of activity

        long timeSeconds = 0;
        double timeSecDouble;
        double timeMinutes; //keeps track of the current minute
        long previousTime = -1; //sets previous time to -1, to avoid clashes with starting time which is always going to be 0
        XYChart.Series set1 = new XYChart.Series<>();

        for (DataRow row : dataRow) {
            timeSeconds = Duration.between(startTime, row.getTime()).getSeconds();
            if (timeSeconds != previousTime) {
                String strSec = Long.toString(timeSeconds);
                timeSecDouble = Double.parseDouble(strSec);
                timeMinutes = timeSecDouble / 60.0;
                timeMinutes = Double.parseDouble(new DecimalFormat("##.###").format(timeMinutes));
                String strLong = Double.toString(timeMinutes);

                set1.getData().add(new XYChart.Data(strLong, row.getHeartRate()));
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
    // TODO: 4/10/18 Rather than showing the distance between each data point, will be better to make it between each minute, more difficult
    @FXML
    void displayDistanceGraph() {
        distanceGraph.getData().clear();
        distanceGraph.layout();
        heartRateButton.setSelected(false);
        heartRateGraph.setVisible(false);
        distanceGraph.setVisible(true);


        distanceGraph.setTitle("Distance Travelled During " + activity.getName());
        List<DataRow> dataRow = activity.getRawData();

        XYChart.Series set2 = new XYChart.Series<>();

        LocalTime startTime = dataRow.get(0).getTime();
        long timeSeconds;
        double timeSecDouble;
        double timeMinutes;

        for (int i = 0; i < dataRow.size(); i += 1) {
            List<DataRow> twoPoints = dataRow.subList(0, i + 1);
            double distance = seng202.team4.model.utilities.DataProcessor.totalDistance(twoPoints);
            timeSeconds = Duration.between(startTime, dataRow.get(i).getTime()).getSeconds();
            String strSec = Long.toString(timeSeconds);
            timeSecDouble = Double.parseDouble(strSec);
            timeMinutes = timeSecDouble / 60.0;
            timeMinutes = Double.parseDouble(new DecimalFormat("##.###").format(timeMinutes));
            String strLong = Double.toString(timeMinutes);

            set2.getData().add(new XYChart.Data(strLong, distance));
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