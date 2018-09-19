package seng202.team4.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng202.team4.Utilities;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.utilities.FileImporter;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.view.ActivityConfirmationRow;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** Controller for the import activities preview screen. */
public class ImportActivitiesPreviewScreenController extends Controller {

    /** ArrayList of activityConfirmationRows that listed */
    private ArrayList<ActivityConfirmationRow> activityConfirmationRows = new ArrayList<>();

    /** ActivityTabController of the activity tab. */
    private ActivityTabController activityTabController;

    /** VBox that holds the rows of activities. */
    @FXML
    private VBox activityListVbox;

    /** GridPane of the import activities preview screen. */
    @FXML
    private GridPane gridPane;

    /**
     * Constructor of the ImportActivitiesPreviewScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ImportActivitiesPreviewScreenController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
    }


    /**
     * Action performed when the import activities button is pressed.
     * Stores all the activities to be imported in the database.
     */
    @FXML
    public void importActivities() throws SQLException {
        applicationStateManager.switchToScreen("MainScreen");
        for (ActivityConfirmationRow activityConfirmationRow: activityConfirmationRows) {
            Activity activity = activityConfirmationRow.getActivity();
            if (activityConfirmationRow.isSelected()) {
                applicationStateManager.getCurrentProfile().addActivity(activity);
                activity.setType(activityConfirmationRow.getController().getSelectedActvityType());
            }

        }
        activityTabController.updateTable();

    }


    /**
     * Loads all activities from the given csv file.
     *
     * @param csvFile The csv file that contains the data of the activities.
     */
    public void loadActivities(File csvFile) throws SQLException {
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> activities = new ArrayList<>();
        ArrayList<DataRow> rows = new ArrayList<>();

        fileImporter.readFile(csvFile, rows, activities);

        HashSet<String> activityStringKeySet = new HashSet<String>();
        for (Activity activity: applicationStateManager.getCurrentProfile().getActivityList()) {
            activityStringKeySet.add(activity.getName()+activity.getDate().toString());
        }

        for (int i=0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (!activityStringKeySet.contains(activity.getName()+activity.getDate().toString())) {
                activity.setCaloriesBurnedValue(DataProcessor.calculateCalories(activity.getAverageSpeed(), activity.getDuration().getSeconds(), activity.getType(), applicationStateManager.getCurrentProfile()));
                ActivityConfirmationRowController activityRowController = new ActivityConfirmationRowController(applicationStateManager);
                ActivityConfirmationRow activityConfirmationRow = new ActivityConfirmationRow(activityRowController, activity);
                activityConfirmationRow.prefWidthProperty().bind(gridPane.widthProperty());
                activityListVbox.getChildren().add(activityConfirmationRow);

                if (i % 2 == 0) {
                    activityConfirmationRow.applyShadedBackground();
                }

                activityConfirmationRow.getController().setSelectedActvityType(activity.getType());

                activityConfirmationRows.add(activityConfirmationRow);
            }

        }
    }
}
