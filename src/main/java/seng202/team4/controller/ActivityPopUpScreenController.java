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

import java.util.ArrayList;
import java.util.List;


/**
 * Controller for the Activity Pop up screen.
 */
public class ActivityPopUpScreenController extends Controller {

    private ActivityTabController activityTabController;

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
    public void initialize() {
        distanceGraph.setVisible(false);
        heartRateGraph.setVisible(false);
    }

    @FXML
    public void displayHeartRateGraph() {
        heartRateGraph.getData().clear();
        heartRateGraph.layout();
        distanceButton.setSelected(false);
        distanceGraph.setVisible(false);
        heartRateGraph.setVisible(true);
//        //@todo Fix this stuff up, not displaying graph correctly
//        List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();
//        List<DataRow> dataRow = activityList.get(0).getRawData();
//        System.out.println(dataRow.get(0).getHeartRate());
        XYChart.Series set1 = new XYChart.Series<>();
//        for (DataRow row : dataRow) {
//            set1.getData().add(new XYChart.Data(row.getTime(), row.getHeartRate()));
//        }
        set1.getData().add(new XYChart.Data("1", 23));
        set1.getData().add(new XYChart.Data("2", 36));
        set1.getData().add(new XYChart.Data("3", 21));
        heartRateGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        heartRateGraph.getYAxis().setAnimated(false);
        heartRateGraph.getData().addAll(set1);
    }

    @FXML
    void displayDistanceGraph() {
        distanceGraph.getData().clear();
        distanceGraph.layout();
        heartRateButton.setSelected(false);
        heartRateGraph.setVisible(false);
        distanceGraph.setVisible(true);
        XYChart.Series set2 = new XYChart.Series<>();
        set2.getData().add(new XYChart.Data("1", 31));
        set2.getData().add(new XYChart.Data("2", 18));
        set2.getData().add(new XYChart.Data("3", 22));
        distanceGraph.getXAxis().setAnimated(false); //these two lines avoid errors where only the last value in the x axis was loaded
        distanceGraph.getYAxis().setAnimated(false);
        distanceGraph.getData().addAll(set2);
    }


    /**
     * Constructor for the ActivityPopUpScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityPopUpScreenController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
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