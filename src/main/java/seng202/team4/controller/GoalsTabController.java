package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;


/**
 * Controller for the Goals Tab.
 */
public class GoalsTabController extends Controller {

    /** Contains the name of the first goal */
    @FXML
    private Text goal1text;

    /** Contains the name of the second goal */
    @FXML
    private Text goal2text;

    /** Contains the name of the third goal */
    @FXML
    private Text goal3text;

    /** Contains the name of the fourth goal */
    @FXML
    private Text goal4text;

    /** Contains the name of the fifth goal */
    @FXML
    private Text goal5text;

    /** Button to remove the first goal */
    @FXML
    private Button removeGoal1Button;

    /** Button to remove the second goal */
    @FXML
    private Button removeGoal2Button;

    /** Button to remove the third goal */
    @FXML
    private Button removeGoal3Button;

    /** Progress bar of the fourth goal */
    @FXML
    private Button removeGoal4Button;

    /** Progress bar of the fifth goal */
    @FXML
    private Button removeGoal5Button;

    @FXML
    private ProgressBar goal1Bar;

    /** Progress bar of the second goal */
    @FXML
    private ProgressBar goal2Bar;

    /** Progress bar of the third goal */
    @FXML
    private ProgressBar goal3Bar;

    /** Progress bar of the fourth goal */
    @FXML
    private ProgressBar goal4Bar;

    /** Progress bar of the fifth goal */
    @FXML
    private ProgressBar goal5Bar;

    /** Button to view the calendar */
    @FXML
    private Button calenderButton;

    /** Button to set a new goal */
    @FXML
    private Button setNewGoalButton;


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

    }
}
