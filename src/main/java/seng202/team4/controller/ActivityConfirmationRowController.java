package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ActivityConfirmationRowController extends Controller {

    public ActivityConfirmationRowController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    private Text activityDate;

    @FXML
    private Text activityDistance;

    @FXML
    private Text activityName;

    @FXML
    private Text activityDuration;

    @FXML
    private Text activityType;


    public void setActivityDateText(String text) {
        activityDate.setText(text);
    }

    public void setActivityDistanceText(String text) {
        activityDistance.setText(text);
    }

    public void setActivityNameText(String text) {
        activityName.setText(text);
    }

    public void setActivityDurationText(String text) {
        activityDuration.setText(text);
    }

    public void setActivityTypeText(String text) {
        activityType.setText(text);
    }
}
