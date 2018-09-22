package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class WarningDescriptionPopUpController extends Controller{

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
        setDescriptionText();
        descriptionText.setWrapText(true);
        descriptionText.setEditable(false);
    }

    public void setAverageLabel(int heartRate) {
        averageLabel.setText(Integer.toString(heartRate));
    }

    public void setMinLabel(int heartRate) {
        minLabel.setText(Integer.toString(heartRate));
    }

    public void setMaxLabel(int heartRate) {
        maxLabel.setText(Integer.toString(heartRate));
    }

    public void setHeartRateRecommendation() {

    }

    public void setPopUpTitle(String title) {
        popUpTitle.setText(title);
    }

    public void setDescriptionText() {

    }

    public void setRecommendedLabel() {

    }

    @FXML
    void closePopup() {

    }
}
