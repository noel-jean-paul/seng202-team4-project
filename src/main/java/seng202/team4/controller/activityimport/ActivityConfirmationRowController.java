package seng202.team4.controller.activityimport;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
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

    /** The RowConstraints of the row in the GridPane. */
    @FXML
    private RowConstraints errorRow;

    /** The error Text that is used to display warnings/errors */
    @FXML
    private Text errorText;

    /** The AnchorPane that is the parent Node of the ActivityConfirmationRow. */
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

    /**
     * Initializes the ActivityConfirmationRow.
     */
    @FXML
    public void initialize() {
        activityTypeChoiceBox.getItems().addAll(ActivityType.Other, ActivityType.Walk, ActivityType.Run);
        activityTypeChoiceBox.setValue(ActivityType.Other);
        selectedCheckBox.setSelected(true);
    }

    /**
     * Sets the text of the the activity Text in the row.
     *
     * @param text String that the Text will be set to.
     */
    public void setActivityDateText(String text) {
        activityDate.setText(text);
    }

    /**
     * Sets the text of the the distance Text in the row.
     *
     * @param text String that the Text will be set to.
     */
    public void setActivityDistanceText(String text) {
        activityDistance.setText(text);
    }

    /**
     * Sets the text of the the activity name Text in the row.
     *
     * @param text String that the Text will be set to.
     */
    public void setActivityNameText(String text) {
        activityName.setText(text);
    }

    /**
     * Sets the text of the the activity duration in the row.
     *
     * @param text String that the Text will be set to.
     */
    public void setActivityDurationText(String text) {
        activityDuration.setText(text);
    }

    /**
     * Sets an error for the row. The row is expanded and the error text is set to some error String.
     *
     * @param errorString The String of the error to be set.
     */
    public void setError(String errorString) {
        confirmationRowPane.setPrefHeight(confirmationRowPane.getPrefHeight()+35);

        errorRow.setMaxHeight(35);
        errorRow.setMinHeight(35);
        errorRow.setPercentHeight(35);

        errorText.setText(errorString);
    }

    /**
     * Gets whether the ActivityConfirmationRow is selected.
     *
     * @return true if the row is selected. false otherwise.
     */
    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    /**
     * Gets the ActivityType that is selected in the activityTypeChoiceBox.
     *
     * @return The ActivityType that is selected.
     */
    public ActivityType getSelectedActvityType() {
        return (ActivityType) activityTypeChoiceBox.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the selected ActivityType of the activityTypeChoiceBox.
     *
     * @param actvityType The ActivityType to set the activityTypeChoiceBox to.
     */
    public void setSelectedActvityType(ActivityType actvityType) {
        activityTypeChoiceBox.setValue(actvityType);
    }
}
