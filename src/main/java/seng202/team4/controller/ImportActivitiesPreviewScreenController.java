package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.utilities.FileImporter;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.view.ActivityConfirmationRow;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

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
                // Store all data rows in the database as they have not been stored yet but are in the rawData list
                for (DataRow dataRow : activity.getRawData()) {
                    DataStorer.insertDataRow(dataRow, activity);
                }
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
    public void loadActivities(File csvFile) throws IOException {
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> validActivities = new ArrayList<>();
        ArrayList<Activity> warningActivities = new ArrayList<>();
        ArrayList<Activity> skippedActivities = new ArrayList<>();

        try {
            fileImporter.readFile(csvFile, validActivities, warningActivities, skippedActivities);
        } catch (IOException exception) {
            throw exception;
        }

        System.out.println(warningActivities.size());

        HashSet<String> activityStringKeySet = new HashSet<String>();
        for (Activity activity: applicationStateManager.getCurrentProfile().getActivityList()) {
            activityStringKeySet.add(activity.getName()+activity.getDate().toString());
        }

        int rowNumber = 0;
        for (int i=0; i < validActivities.size(); i++) {
            Activity activity = validActivities.get(i);
            if (!activityStringKeySet.contains(activity.getName()+activity.getDate().toString())) {
                addNewConfirmationRow(activity, rowNumber % 2 == 0);
                rowNumber += 1;
            }
        }

        for (int i=0; i < warningActivities.size(); i++) {
            Activity activity = warningActivities.get(i);
            if (!activityStringKeySet.contains(activity.getName()+activity.getDate().toString())) {
                ActivityConfirmationRowController activityRowController = addNewConfirmationRow(activity, rowNumber % 2 == 0);
                activityRowController.setError("WARNING: One or more activity data rows had to be skipped on import.");
                rowNumber += 1;
            }
        }

    }

    private ActivityConfirmationRowController addNewConfirmationRow(Activity activity, boolean shaded) {
        activity.setCaloriesBurnedValue(DataProcessor.calculateCalories(activity.getAverageSpeed(), activity.getDuration().getSeconds(), activity.getType(), applicationStateManager.getCurrentProfile()));
        ActivityConfirmationRowController activityRowController = new ActivityConfirmationRowController(applicationStateManager);
        ActivityConfirmationRow activityConfirmationRow = new ActivityConfirmationRow(activityRowController, activity);
        activityConfirmationRow.prefWidthProperty().bind(gridPane.widthProperty());
        activityListVbox.getChildren().add(activityConfirmationRow);

        if (shaded) {
            activityConfirmationRow.applyShadedBackground();
        }

        activityConfirmationRow.getController().setSelectedActvityType(activity.getType());

        activityConfirmationRows.add(activityConfirmationRow);

        return activityRowController;
    }

    @FXML
    public void cancel() {
        applicationStateManager.switchToScreen("MainScreen");
        activityConfirmationRows = new ArrayList<ActivityConfirmationRow>();
    }
}
