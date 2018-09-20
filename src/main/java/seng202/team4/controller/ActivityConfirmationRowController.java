package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import seng202.team4.model.data.enums.ActivityType;

/**
 * The controller for an activityConfirmationRow.
 */
public class ActivityConfirmationRowController extends Controller {

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

    /** The ChoiceBox that lets the user choose the type of the Activity. */
    @FXML
    private ChoiceBox activityTypeChoiceBox;

    /** The CheckBox that allows the user to pick which activities they want to import. */
    @FXML
    private CheckBox selectedCheckBox;

    @FXML
    private RowConstraints errorRow;

    @FXML
    private Text errorText;

    @FXML
    private AnchorPane confirmationRowPane;

    /**
     * Constructor of the ActivityConfirmationRowController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityConfirmationRowController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        activityTypeChoiceBox.getItems().addAll(ActivityType.Other, ActivityType.Walk, ActivityType.Run);
        activityTypeChoiceBox.setValue(ActivityType.Other);
        selectedCheckBox.setSelected(true);
    }

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

    public void setError(String errorString) {
        confirmationRowPane.setPrefHeight(confirmationRowPane.getPrefHeight()+35);

        errorRow.setMaxHeight(35);
        errorRow.setMinHeight(35);
        errorRow.setPercentHeight(35);

        errorText.setText(errorString);
    }

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    public ActivityType getSelectedActvityType() {
        return (ActivityType) activityTypeChoiceBox.getSelectionModel().getSelectedItem();
    }

    public void setSelectedActvityType(ActivityType actvityType) {
        activityTypeChoiceBox.setValue(actvityType);
    }
}
