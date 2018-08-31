package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng202.team4.Utilities;

public class ImportActivitiesPreviewScreenController extends Controller {

    private final Background shadedBackground = new Background( new BackgroundFill( Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY ) );

    public ImportActivitiesPreviewScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    private VBox activityListVbox;


    @FXML
    public void initialize() {

    }

    public void loadActivities() {
        for (int i=0; i < 100; i++) {
            Pane activityListItem = Utilities.loadPane("ActivityConfirmationRow.fxml", new Controller(applicationStateManager));
            activityListVbox.getChildren().add(activityListItem);
            if (i % 2 == 0) {
                activityListItem.setBackground(shadedBackground);
            }
        }
    }
}
