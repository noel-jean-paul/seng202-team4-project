package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * The controller for an activityConfirmationRow.
 */
public class ActivityConfirmationRowController extends Controller {

    /**
     * Constructor of the ActivityConfirmationRow.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityConfirmationRowController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** The Text that displays the date of the Activity. */
    @FXML
    private Text activityDate;

    /** The Text that displays the distance of the Activity. */
    @FXML
    private Text activityDistance;

    /** The Text that displays the name of the Activity. */
    @FXML
    private Text activityName;

    /** The Text that displays the duration of the Activity. */
    @FXML
    private Text activityDuration;

    /** The Text that displays the type of the Activity, */
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
