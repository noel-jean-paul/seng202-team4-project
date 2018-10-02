package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import seng202.team4.model.data.Goal;

/**
 * The controller for the rows in the goal tables
 */
public class GoalTableRowController extends Controller {

    /* Text that displays the description of the goal */
    @FXML
    private Text description;

    /* Text that displays the creationDate of the goal */
    @FXML
    private Text creationDate;

    /* Text that displays the expiryDate of the goal */
    @FXML
    private Text expiryDate;

    /* Progress bar that displays the progress of the goal */
    @FXML
    private ProgressBar progressBar;

    /* Text that displays the progress of the goal */
    @FXML
    private Text progressValue;

    private Goal goal;


    /**
     * Constructor of the GoalTableRowController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public GoalTableRowController(ApplicationStateManager applicationStateManager, Goal goal) {
        super(applicationStateManager);
        this.goal = goal;
    }

    @FXML
    public void initialize() {
        initialiseRow();
    }

    /* Initializes the GoalTableRow using goal parameter */
    private void initialiseRow() {
        setDescription(goal.getDescription());
        setCreationDate(goal.getCreationDate().toString());
        setExpiryDate(goal.getExpiryDate().toString());
        setProgress(goal.getProgress());
    }

    private void setDescription(String description) {
        this.description.setText(description);
    }

    private void setCreationDate(String creationDate) {
        this.creationDate.setText(creationDate);
    }

    private void setExpiryDate(String expiryDate) {
        this.expiryDate.setText(expiryDate);
    }

    /** Set the values of the progressValue text and the progressBar
     *
     * @param progress the progress to set as an int.
     */
    private void setProgress (double progress) {
        this.progressValue.setText(Double.toString(progress));
        this.progressBar.setProgress(progress / 100);
    }
}
