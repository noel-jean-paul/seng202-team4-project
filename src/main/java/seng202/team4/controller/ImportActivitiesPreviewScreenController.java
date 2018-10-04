package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.CorruptActivity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.utilities.FileParser;
import seng202.team4.view.ActivityConfirmationRow;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** Controller for the import activities preview screen. */
public class ImportActivitiesPreviewScreenController extends Controller {

    /** VBox that holds the rows of activities. */
    @FXML
    private VBox activityListVbox;

    /** GridPane of the import activities preview screen. */
    @FXML
    private GridPane gridPane;

    /** Button for importing activites. */
    @FXML
    private Button importActivitiesButton;

    /** ScrollPane that has the VBox of activities. */
    @FXML
    private ScrollPane scrollPane;

    /** HBox that keeps content centered in the scroll pane. */
    @FXML
    private HBox scrollHbox;

    /** ArrayList of activityConfirmationRows that listed */
    private ArrayList<ActivityConfirmationRow> activityConfirmationRows = new ArrayList<>();

    /** ActivityTabController of the activity tab. */
    private ActivityTabController activityTabController;


    /**
     * Constructor of the ImportActivitiesPreviewScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ImportActivitiesPreviewScreenController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
    }

    @FXML
    public void initialize() {
        scrollHbox.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    /**
     * Action performed when the import activities button is pressed.
     * Stores all the activities to be imported in the database.
     */
    @FXML
    public void importActivities() throws SQLException {
        boolean warningFound = false;
        List<Activity> importedActivities = new ArrayList<>();  // Stores imported activities to pass to goal update  progress method
        applicationStateManager.switchToScreen("MainScreen");
        for (ActivityConfirmationRow activityConfirmationRow: activityConfirmationRows) {
            Activity activity = activityConfirmationRow.getActivity();
            if (activityConfirmationRow.isSelected()) {
                // Add the activity to the profile's activities and to the list of activities which have been imported
                applicationStateManager.getCurrentProfile().addActivity(activity);
                importedActivities.add(activity);

                // Check the activity for health warnings
                // TODO: 4/10/18 Matt_T There doesn't seem to be any health warning checking happening here (It might happen elsewhere but the comment above was here)
                // Store all data rows in the database as they have not been stored yet but are in the rawData list
                for (DataRow dataRow : activity.getRawData()) {
                    // Set the owner manually as addDataRow has not been called
                    dataRow.setOwner(activity);
                }
                try {
                    // Insert the datarows at once using a transaction
                    DataStorer.insertDataRowTransaction(activity.getRawData());
                } catch (SQLException e) {
                    // TODO: 22/09/18 Currently displays error message for every activity failed. Want one for all activities
                    GuiUtilities.displayErrorMessage("Failed to import one or more activities.", "");
                }
                activity.setType(activityConfirmationRow.getController().getSelectedActvityType());
                if (activity.addWarnings(true)) {
                    warningFound = true;
                }
            }
        }
        activityTabController.updateTable();
        if (warningFound) {
            HealthWarningDetectedPopup detectedPopup = new HealthWarningDetectedPopup(applicationStateManager);
            Pane popUp = GuiUtilities.loadPane("WarningDetectedPopup.fxml", detectedPopup);
            applicationStateManager.displayPopUp(popUp);
        }

        // Update the current goals with all activities imported
        applicationStateManager.getCurrentProfile().updateGoalsForProgress(importedActivities);
    }

    /**
     * Loads all activities from the given csv file.
     *
     * @param csvFile The csv file that contains the data of the activities.
     */
    public void loadActivities(File csvFile) throws IOException {
        FileParser fileParser = new FileParser();
        ArrayList<Activity> validActivities = new ArrayList<>();
        ArrayList<CorruptActivity> warningActivities = new ArrayList<>();
        ArrayList<Activity> skippedActivities = new ArrayList<>();

        try {
            fileParser.parseFileToActivites(csvFile, validActivities, warningActivities, skippedActivities);
        } catch (IOException exception) {
            throw exception;
        }

        System.out.println(warningActivities.size());

        HashSet<String> activityStringKeySet = new HashSet<String>();
        for (Activity activity: applicationStateManager.getCurrentProfile().getActivityList()) {
            activityStringKeySet.add(activity.getName()+activity.getDate().toString());
        }

        ArrayList<Activity> duplicateActivities = new ArrayList<>();

        int rowNumber = 0;
        for (int i=0; i < validActivities.size(); i++) {
            Activity activity = validActivities.get(i);
            if (!activityStringKeySet.contains(activity.getName()+activity.getDate().toString())) {
                ActivityConfirmationRowController activityRowController = addNewConfirmationRow(activity, rowNumber % 2 == 0);
                if (activity.getDate().compareTo(LocalDate.now()) > 0) {
                    activityRowController.setError("WARNING: Activity occurs in the future.");
                }
                activityStringKeySet.add(activity.getName()+activity.getDate().toString());
                rowNumber += 1;
            } else {
                duplicateActivities.add(activity);
            }
        }

        for (int i=0; i < warningActivities.size(); i++) {
            CorruptActivity corruptActivity = warningActivities.get(i);
            if (!activityStringKeySet.contains(corruptActivity.getName()+corruptActivity.getDate().toString())) {
                ActivityConfirmationRowController activityRowController = addNewConfirmationRow(corruptActivity, rowNumber % 2 == 0);
                activityRowController.setError(String.format("WARNING: %.2f%% of activity data rows have to be skipped on import.", corruptActivity.getPercentageCorrupt()));
                activityStringKeySet.add(corruptActivity.getName()+corruptActivity.getDate().toString());
                rowNumber += 1;
            } else {
                duplicateActivities.add(corruptActivity);
            }
        }


        if (skippedActivities.size() > 0 || duplicateActivities.size() > 0) {
            ActivityImportWarningPopUpController activityImportWarningPopUpController = new ActivityImportWarningPopUpController(applicationStateManager);
            Pane activityImportWarningPop = GuiUtilities.loadPane("ActivityImportWarningPopUp.fxml", activityImportWarningPopUpController);

            for (int i=0; i < skippedActivities.size(); i++) {
                Activity skippedActivity = warningActivities.get(i);
                activityImportWarningPopUpController.addWarning(String.format("Not enough valid data to import '%s'.", skippedActivity.getName()));
            }

            for (int i=0; i < duplicateActivities.size(); i++) {
                Activity duplicateActivity = duplicateActivities.get(i);
                activityImportWarningPopUpController.addWarning(String.format("%s on %s already exists, so it has been skipped.", duplicateActivity.getName(), duplicateActivity.getDate()));
            }

            applicationStateManager.displayPopUp(activityImportWarningPop);
        }

        if (activityConfirmationRows.size() == 0) {
            Label label = new Label("No new valid activity data found.");
            label.setTextFill(Color.RED);
            label.setFont(Font.font(24));
            activityListVbox.getChildren().add(label);
            importActivitiesButton.setDisable(true);
        }

    }

    /**
     * Adds an activity confirmation row to the screen.
     *
     * @param activity The activity to be added as a row to the screen.
     * @param shaded Whether the row should be shaded.
     * @return The controller of the ActivityConfirmationRow.
     */
    private ActivityConfirmationRowController addNewConfirmationRow(Activity activity, boolean shaded) {
        activity.setCaloriesBurnedValue(DataProcessor.calculateCalories(activity.getAverageSpeed(), activity.getDuration().getSeconds(), activity.getType(), applicationStateManager.getCurrentProfile()));
        ActivityConfirmationRowController activityRowController = new ActivityConfirmationRowController(applicationStateManager);
        ActivityConfirmationRow activityConfirmationRow = new ActivityConfirmationRow(activityRowController, activity);
        activityConfirmationRow.prefWidthProperty().bind(gridPane.widthProperty());
        activityListVbox.getChildren().add(activityConfirmationRow);

        if (shaded) {   // If the row needs to be shaded then a shaded background is applied.
            activityConfirmationRow.applyShadedBackground();
        }

        activityConfirmationRow.getController().setSelectedActvityType(activity.getType());

        activityConfirmationRows.add(activityConfirmationRow);

        return activityRowController;
    }

    /**
     * Cancels the import and returns to the main screen of the app.
     */
    @FXML
    public void cancel() {
        applicationStateManager.switchToScreen("MainScreen");
        activityConfirmationRows = new ArrayList<ActivityConfirmationRow>();
    }
}
