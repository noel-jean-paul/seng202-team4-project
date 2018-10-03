package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.model.data.Goal;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.enums.GoalType;

import java.util.List;


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

    /** Text for displaying a message if no goal is selected. */
    @FXML
    private Text noGoalSelectedText;

    /* The scroll pane containing the goals */
    @FXML
    private ScrollPane scrollPane;

    /** The currently selected goal. */
    private Goal selectedGoal = null;

    /* List of the GoalTableRows in the current goal table */
    private List<GoalTableRowController> currentGoalRows;

    /* List of the GoalTableRows in the past goal table */
    private List<GoalTableRowController> pasttGoalRows;

    /**
     * Constructor for the Goals Tab Controller.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    GoalsTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /** Initializes the goals tab. */
    @FXML
    public void initialize() {
        reset();
        Goal goal = new Goal(2, 67, GoalType.Run,"2018-09-28", "2017-01-12",
                "PT50M");
        GoalTableRowController goalTableRowController = new GoalTableRowController(applicationStateManager, goal);
        Pane goalPane = GuiUtilities.loadPane("GoalTableRow.fxml", goalTableRowController); //This is what gets displayed
        goalsListVbox.getChildren().add(goalPane);

        // Make the scrollPane match the width of the GoalTableRow
        goalPane.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    /* Fills the current and past GoalRow lists using the currently loaded profile's goals */
    @FXML
    private void fillGoalRowLists() {

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
        noGoalSelectedText.setText("No Goal Selected");

    }
}
