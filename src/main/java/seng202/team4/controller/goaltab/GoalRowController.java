package seng202.team4.controller.goaltab;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;

/**
 * The controller for the rows in the goal tables
 */
public class GoalRowController extends Controller {

    /**Text that displays the description of the goal */
    @FXML
    private Text description;

    /**Text that displays the creationDate of the goal */
    @FXML
    private Text creationDate;

    /**Text that displays the expiryDate of the goal */
    @FXML
    private Text expiryDate;

    /**Progress bar that displays the progress of the goal */
    @FXML
    private ProgressBar progressBar;

    /**Text that displays the progress of the goal */
    @FXML
    private Text progressValue;

    /**Top level pane of the GoalTableRow */
    @FXML
    private AnchorPane rowPane;


    /**
     * Constructor of the GoalRowController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    GoalRowController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Sets the description of the GoalRow.
     *
     * @param description The new description of the GoalRow.
     */
    public void setDescription(String description) {
        this.description.setText(description);
    }

    /**
     * Sets the creation date of the GoalRow creation date Text.
     * @param creationDate The new creation date of the GoalRow.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate.setText(creationDate);
    }

    /**
     * Sets the expiry date of the GoalRow expiry date Text.
     * @param expiryDate The new expiry date of the GoalRow.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate.setText(expiryDate);
    }

    /** Set the values of the progressValue text and the progressBar
     *
     * @param progress the progress to set as an int.
     */
    public void setProgress (double progress) {
        String percentSign = "%";
        this.progressValue.setText(String.format("%.0f%s", progress, percentSign));
        this.progressBar.setProgress(progress / 100);
    }

    /** Get the rowPane
     *
     * @return the rowPane as an AnchorPane
     */
    public AnchorPane getRowPane() {
        return rowPane;
    }
}

