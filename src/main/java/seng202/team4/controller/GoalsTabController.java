package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.enums.GoalType;


/**
 * Controller for the Goals Tab.
 */
public class GoalsTabController extends Controller {

    @FXML
    private Button calendarViewButton;

    @FXML
    private Text startDateText;

    @FXML
    private Text remainingTimeText;

    @FXML
    private ProgressIndicator goalProgressIndicator;

    @FXML
    private Text descriptionText;

    @FXML
    private VBox goalsListVbox;

    @FXML
    private Text expiryDateText;


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
        Goal goal = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                "PT50M");
        GoalTableRowController goalTableRowController = new GoalTableRowController(applicationStateManager, goal);
        Pane goalPane = GuiUtilities.loadPane("GoalTableRow.fxml", goalTableRowController);
        goalsListVbox.getChildren().add(goalPane);


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
}
