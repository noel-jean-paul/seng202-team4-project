package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import seng202.team4.model.data.enums.WarningType;

public class WarningDescriptionPopUpController extends Controller{

    @FXML
    private GridPane popUpWarning;

    @FXML
    private Label averageLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Label minLabel;

    @FXML
    private Label recommendedLabel;

    @FXML
    private Label heartRateRecommendation;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Label popUpTitle;

    @FXML
    private Label maxLabel;

    @FXML
    private Label activityNameLabel;


    /**
     * Creates a new controller with the given ApplicationStateManager
     *
     * @param applicationStateManager the statemangager of the application
     */
    public WarningDescriptionPopUpController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Sets the textArea properties.
     */
    @FXML
    public void initialize() {
        descriptionText.setWrapText(true);
        descriptionText.setEditable(false);
    }

    /**
     * Sets the text for the average heart rate label.
     * @param heartRate the average heart rate the user had over their activity.
     */
    public void setAverageLabel(int heartRate) {
        averageLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    /**
     * Sets the text for the minimum heart rate label.
     * @param heartRate the minimum heart rate the user had over their activity.
     */
    public void setMinLabel(int heartRate) {
        minLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    /**
     * Sets the text for the maximum heart rate label.
     * @param heartRate the maximum heart rate the user had over their activity.
     */
    public void setMaxLabel(int heartRate) {
        maxLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    /**
     * Sets the text for the recommendation explanation label.
     * @param heartRateType the boundary recommendation being violated.
     */
    public void setHeartRateRecommendation(String heartRateType) {
        heartRateRecommendation.setText("Recommended " + heartRateType + ":");

    }

    /**
     * Sets the text for the PopUp "Recommended [Max|Min|Avg] label.
     * @param heartRate the heart rate recommendation being violated.
     */
    public void setRecommendedLabel(int heartRate) {
        recommendedLabel.setText(heartRate + " bpm");
    }

    /**
     * Sets the text for the popup window title.
     * @param title the String being used to populate the title.
     */
    public void setPopUpTitle(String title) {
        popUpTitle.setText(title);
    }

    /**
     * Sets the description of the textArea based on the health warning given.
     * @param type the health warning used to see what message is needed to populate the textArea.
     */
    public void setDescriptionText(WarningType type) {
        String text;
        if (type == WarningType.Tachy) {
            text = "Your heart rate was over your recommended maximum heart rate";
        } else if (type == WarningType.Brady) {
            text = "Your heart rate was under your recommended minimum heart rate.";
        } else {
            text = "Your resting heart rate was over your recommended resting rate";
        }
        descriptionText.setText(text);
    }

    public void setActivityNameLabel(String activityName) {
        activityNameLabel.setText(activityName);
    }

    /**
     * Closes the popup window when the cancel button is selected.
     */
    @FXML
    void cancelPressed() {
        applicationStateManager.closePopUP(popUpWarning);
    }
}
