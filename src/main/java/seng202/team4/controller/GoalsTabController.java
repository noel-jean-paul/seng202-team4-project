package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


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
