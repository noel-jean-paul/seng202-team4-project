package seng202.team4.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng202.team4.Utilities;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.utilities.FileImporter;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.view.ActivityConfirmationRow;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
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
            activity.setType(activityConfirmationRow.getController().getSelectedActvityType());
            if (activityConfirmationRow.isSelected()) {
                applicationStateManager.getCurrentProfile().addActivity(activity);
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
        for (int i=0; i < activities.size(); i++) {
            ActivityConfirmationRowController activityRowController = new ActivityConfirmationRowController(applicationStateManager);
            ActivityConfirmationRow activityConfirmationRow = new ActivityConfirmationRow(activityRowController, activities.get(i));
            activityConfirmationRow.prefWidthProperty().bind(gridPane.widthProperty());
            activityListVbox.getChildren().add(activityConfirmationRow);

            if (i % 2 == 0) {
                activityConfirmationRow.applyShadedBackground();
            }

            activityConfirmationRows.add(activityConfirmationRow);
        }
    }
}
