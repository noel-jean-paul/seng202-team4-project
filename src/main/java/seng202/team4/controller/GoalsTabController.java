package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.model.data.Goal;


/**
 * Controller for the Goals Tab.
 */
public class GoalsTabController extends Controller {

    /** Button for toggling calendar view. */
    @FXML
    private Button calendarViewButton;

    /** Button for editing the selected goal. */
    @FXML
    private Button editButton;

    /** Text for displaying the start date of the goal. */
    @FXML
    private Text startDateText;

    /** Text for displaying the time remaining for the goal. */
    @FXML
    private Text remainingTimeText;

    /** The ProgressIndicator that displays the progress of the selected goal. */
    @FXML
    private ProgressIndicator goalProgressIndicator;

    /** Text for displaying the description of the goal. */
    @FXML
    private Text descriptionText;

    /** Text for displaying the current amount of the goal that is complete. */
    @FXML
    private Text currentAmountText;

    /** Text for displaying the total amount needed for the goal to be completed. */
    @FXML Text totalAmountText;

    /** The VBost that holds a list of the users goals. */
    @FXML
    private VBox goalsListVbox;

    /** Text for displaying the expiry date of the goal. */
    @FXML
    private Text expiryDateText;

    /** The currently selected goal. */
    private Goal selectedGoal = null;


    /**
     * Constructor for the Goals Tab Controller.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public GoalsTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /** Initializes the goals tab. */
    @FXML
    public void initialize() {
        reset();
    }

    @FXML
    void addGoal() {

    }

    @FXML
    void toggleCalendarView() {

    }

    @FXML
    void edit() {

    }

    public void reset() {
        selectedGoal = null;
        goalProgressIndicator.setProgress(0);
        goalProgressIndicator.setDisable(true);
        editButton.setDisable(true);
        descriptionText.setText("");
        startDateText.setText("");
        expiryDateText.setText("");
        remainingTimeText.setText("");
        currentAmountText.setText("");
        totalAmountText.setText("");

    }
}
