package seng202.team4.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng202.team4.Utilities;
import seng202.team4.model.utilities.FileImporter;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.view.ActivityConfirmationRow;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportActivitiesPreviewScreenController extends Controller {

    private final Background shadedBackground = new Background( new BackgroundFill( Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY ) );

    public ImportActivitiesPreviewScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    private VBox activityListVbox;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {

    }

    @FXML
    public void importActivities() {
        applicationStateManager.switchToScreen("MainScreen");

    }

    public void loadActivities(File csvFile) {
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> activities = new ArrayList<Activity>();
        ArrayList<DataRow> rows = new ArrayList<DataRow>();

        fileImporter.readFile(csvFile, rows, activities);
        for (int i=0; i < activities.size(); i++) {
            ActivityConfirmationRowController activityRowController = new ActivityConfirmationRowController(applicationStateManager);
            ActivityConfirmationRow activityConfirmationRow = new ActivityConfirmationRow(activityRowController, activities.get(i));
            activityConfirmationRow.prefWidthProperty().bind(gridPane.widthProperty());
            activityListVbox.getChildren().add(activityConfirmationRow);


            if (i % 2 == 0) {
                activityConfirmationRow.setBackground(shadedBackground);
            }
        }
    }
}
