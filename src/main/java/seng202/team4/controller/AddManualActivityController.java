package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.utilities.DataProcessor;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

/** Controller class for the AddManualActivityPopup. */
public class AddManualActivityController extends Controller {

    /** The root Node of the popup. */
    @FXML
    private AnchorPane rootPane;

    /** The ActivityType ChoiceBox. */
    @FXML
    private ChoiceBox<ActivityType> typeChoiceBox;

    /** The TextField for the activity time. */
    @FXML
    private TextField timeField;

    /** The TextField for the activity name. */
    @FXML
    private TextField nameField;

    /** The DatePicker used to select the date of an activity. */
    @FXML
    private DatePicker datePicker;

    /** The TextField for the activity distance. */
    @FXML
    private TextField distanceField;

    /** The TextField for the activity duration. */
    @FXML
    private TextField durationField;

    /** Text for displaying errors to the user. */
    @FXML
    private Text errorText;

    /** The controller for the activity tab. */
    @FXML
    private ActivityTabController activityTabController;


    /** Creates a new AddManualActivityController with the given ApplicationStateManager. */
    public AddManualActivityController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
    }

    /**
     * Initializes the add manual activity popup.
     */
    @FXML
    public void initialize() {
        typeChoiceBox.getItems().addAll(ActivityType.Other, ActivityType.Walk, ActivityType.Run);
        typeChoiceBox.setValue(ActivityType.Other);
    }

    /**
     * Cancels the manual entry of the activity by closing the popup.
     * Is called when the user clicks the 'cancel' button.
     */
    @FXML
    void cancel() {
        applicationStateManager.closePopUP(rootPane);
    }

    /**
     * Adds the manually added activity to the users profile.
     */
    @FXML
    void add() {
        String activityName = nameField.getText();
        String timeString = timeField.getText();
        String distanceString = distanceField.getText();
        String durationString = durationField.getText();

        ActivityType type = typeChoiceBox.getSelectionModel().getSelectedItem();

        // Try to parse the date string to check that it is in a valid format.
        LocalDate date = null;
        boolean isValidDateFormat = false;
        try {
            datePicker.setValue(datePicker.getConverter().fromString(datePicker.getEditor().getText()));
            date = datePicker.getValue();
            isValidDateFormat = true;
        } catch (Exception e) {
            isValidDateFormat = false;
        }

        // Try to parse the time string to check that it is in a valid format.
        LocalTime time = null;
        boolean isValidTimeFormat = false;
        try {
            int hours = Integer.parseInt(timeString.split(":")[0]);
            int minutes = Integer.parseInt(timeString.split(":")[1]);
            time = LocalTime.of(hours, minutes);
            isValidTimeFormat = true;
        } catch (Exception e) {
            isValidTimeFormat = false;
        }

        // Try to parse the duration string to check that it is in a valid format.
        Duration duration = null;
        boolean isValidDurationFormat = false;
        try {
            int hours = Integer.parseInt(durationString.split(":")[0]);
            int minutes = Integer.parseInt(durationString.split(":")[1]);
            duration = Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
            isValidDurationFormat = true;
        } catch (Exception e) {
            isValidDurationFormat = false;
        }

        double distance = 0.0;
        boolean isValidDistance = false;
        try {
            distance = Double.parseDouble(distanceString);
            isValidDistance = true;
        } catch (Exception e) {
            isValidDistance = false;
        }


        // Error check fields
        if (applicationStateManager.getCurrentProfile().activityExists(activityName, date)) {   // Check if the activity already exists in the user's activity list
            errorText.setText("An activity with the name and date entered already exists.");
        } else if (activityName.length() < Activity.MINIMUM_NAME_SIZE_ || activityName.length() > Activity.MAXIMUM_NAME_SIZE) {
            errorText.setText(String.format("Activity name should be between %s and %s characters long.", Activity.MINIMUM_NAME_SIZE_, Activity.MAXIMUM_NAME_SIZE));
        } else if (datePicker.getEditor().getText().length() == 0){
            errorText.setText("You need to enter a date.");
        } else if (!isValidDateFormat || date == null) {
            errorText.setText("Date should be in the format dd/mm/yyyy");
        } else if (((date.compareTo(Activity.MINIMUM_DATE)) < 0)||(date.compareTo(LocalDate.now()) > 0)) {
            errorText.setText(String.format("Date should be between %s and %s", Activity.MINIMUM_DATE, LocalDate.now()));
        } else if (timeString.length() == 0) {
            errorText.setText("You need to enter a time.");
        } else if (!isValidTimeFormat) {
            errorText.setText(String.format("'%s' is not a valid time.", timeString));
        } else if (distanceString.length() == 0){
            errorText.setText("You need to enter a distance.");
        } else if (!isValidDistance) {
            errorText.setText(String.format("'%s' is not a valid distance, should be a number.", distanceString));
        } else if (distance < Activity.MINIMUM_DISTANCE || distance > Activity.MAXIMUM_DISTANCE) {
            errorText.setText(String.format("Distance should be between %s m and %s m.", NumberFormat.getInstance().format(Activity.MINIMUM_DISTANCE), NumberFormat.getInstance().format(Activity.MAXIMUM_DISTANCE)));
        } else if (durationString.length() == 0) {
            errorText.setText("You need to enter a duration.");
        } else if (!isValidDurationFormat) {
            errorText.setText(String.format("'%s' is not a valid duration.", durationString));
        } else if (duration.compareTo(Activity.MINIMUM_DURATION) < 0 || duration.compareTo(Activity.MAXIMUM_DURATION) > 0) {
            errorText.setText(String.format("Duration should be between %02d:%02d and %02d:%02d", Activity.MINIMUM_DURATION.toHours(), Activity.MINIMUM_DURATION.toMinutes()%60, Activity.MAXIMUM_DURATION.toHours(), Activity.MAXIMUM_DURATION.toMinutes()%60));
        } else {
            // Activity is valid. Try to insert it to the user's activities.
            double speed = DataProcessor.calculateAverageSpeed(distance, duration);
            double calories = DataProcessor.calculateCalories(speed, duration.getSeconds(), type, applicationStateManager.getCurrentProfile());
            Activity activity = new Activity(activityName, date.toString(), type, time.toString(), duration.toString(), distance, calories);

            try {
                applicationStateManager.getCurrentProfile().addActivity(activity);
                applicationStateManager.closePopUP(rootPane);
                activityTabController.updateTable();
                activityTabController.updateCalendar();

                // Update the goals with the new activity imported
                applicationStateManager.getCurrentProfile().updateGoalsForProgress(Collections.singletonList(activity));
            } catch (java.sql.SQLException e) {
                GuiUtilities.displayErrorMessage("Failed to add activity.", "The activity could not be inserted into the database.");
                e.printStackTrace();
            }
        }
    }
}



