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


    /**
     * Creates a new controller with the given ApplicationStateManager
     *
     * @param applicationStateManager
     */
    public WarningDescriptionPopUpController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        descriptionText.setWrapText(true);
        descriptionText.setEditable(false);
    }

    public void setAverageLabel(int heartRate) {
        averageLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    public void setMinLabel(int heartRate) {
        minLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    public void setMaxLabel(int heartRate) {
        maxLabel.setText(Integer.toString(heartRate) + " bpm");
    }

    public void setHeartRateRecommendation(String heartRateType) {
        heartRateRecommendation.setText("Recommended " + heartRateType + ":");

    }

    public void setRecommendedLabel(int heartRate) {
        recommendedLabel.setText(heartRate + " bpm");
    }

    public void setPopUpTitle(String title) {
        popUpTitle.setText(title);
    }

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

    @FXML
    void cancelPressed() {
        closePopup();
    }

    @FXML
    void closePopup() {
        applicationStateManager.closePopUP(popUpWarning);
    }
}
