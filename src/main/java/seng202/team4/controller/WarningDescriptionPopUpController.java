package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seng202.team4.App;
import seng202.team4.model.data.enums.WarningType;

public class WarningDescriptionPopUpController extends Controller{

    /** The pop up warning pane */
    @FXML
    private GridPane popUpWarning;

    /** Displays the average heart rate over the activity */
    @FXML
    private Label averageLabel;

    /** The button used to close the popup */
    @FXML
    private Button cancelButton;

    /** Displays the minimum heart rate over the activity */
    @FXML
    private Label minLabel;

    /** Displays the recommended max heart rate for the activity */
    @FXML
    private Label recommendedLabel;

    /** Displays "Recommended <value>" where value is the heart rate type associated with the warning */
    @FXML
    private Label heartRateRecommendation;

    /** The description of the health warning */
    @FXML
    private TextArea descriptionText;

    /** The displayed title of the popup window */
    @FXML
    private Label popUpTitle;

    /** Displays the max heart rate over the activity */
    @FXML
    private Label maxLabel;

    /** The label which displays the activity name where the warning came from */
    @FXML
    private Label activityNameLabel;

    /** The background of the popup window */
    @FXML
    private Rectangle popUpRectangle;


    /**
     * Creates a new controller with the given ApplicationStateManager
     *
     * @param applicationStateManager the applicationStateManager of the app.
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
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
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

    /**
     * Sets the Activity name label.
     *
     * @param activityName The name of the Activity to set the label to.
     */
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
