package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.GoalListPair;
import seng202.team4.view.CurrentGoalRowItem;

import java.sql.SQLException;
import java.time.Clock;


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

    /** The scroll pane containing the goals */
    @FXML
    private ScrollPane scrollPane;

    /** The start date heading for the goal information. */
    @FXML
    private Text startDateHeading;

    /** The expiry date heading for the goal information. */
    @FXML
    private Text expiryDateHeading;

    /** The remaining time heading for the goal information. */
    @FXML
    private Text remainingTimeHeading;

    /** The current amount heading for the goal information. */
    @FXML
    private Text currentAmountHeading;

    /** The required amount heading for the goal information. */
    @FXML
    private Text requiredAmountHeading;

    /** The header for the expiry/completion field of the table. */
    @FXML
    private Text expiryCompletionDate;

    /** The currently selected goal. */
    private CurrentGoalRowItem selectedGoalRow = null;

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
        // reset the top goal bar to have no goal information displayed
        reset();
    }

    /** Display notications of the goals which were expired and completed
     *
     * @param goalLists a GoalListPair object contains the completed and expired goals to display
     */
    private void displayGoalNotifications(GoalListPair goalLists) {
        // Display notification of the goals which were completed
        for (Goal completedGoal: goalLists.getCompletedGoals()) {
            System.out.println(String.format("Goal '%s' has been completed!", completedGoal.getDescription()));
        }

        // Display notifaction of the goals that have expired
        for (Goal expiredGoal: goalLists.getExpiredGoals()) {
            System.out.println(String.format("Goal '%s' has expired.", expiredGoal.getDescription()));
        }
    }

    /* Updates the current profile's goals then fills the current GoalRow vbox using them */
    private void updateCurrentGoalRowTable() {
        try {
            // Update the currentGoals of the currently loaded profile
            GoalListPair goalListPair = applicationStateManager.getCurrentProfile().updateCurrentGoals();
            // Display notications of which goals were completed and which expired
            displayGoalNotifications(goalListPair);
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("An error occurred regarding the database",
                    "Goal updates could not be completed successfully");
            System.out.println("Database error occurred while updating goals");
        }

        // Clear the current table (vbox) of goals
        goalsListVbox.getChildren().clear();

        // Add each current Goal to the vbox children
        for (Goal goal: applicationStateManager.getCurrentProfile().getCurrentGoals()) {
            // Create a new pair of GoalRow controller and item
            CurrentGoalRowController controller = new CurrentGoalRowController(applicationStateManager);
            CurrentGoalRowItem currentGoalRowItem = new CurrentGoalRowItem(controller, goal);

            // When an item is selected, set the selected row to be that item
            currentGoalRowItem.setOnMouseClicked(event -> {changeSelectedGoalRow(currentGoalRowItem);});

            // Add the goalRowItem to the vbox which holds the current goals
            goalsListVbox.getChildren().add(currentGoalRowItem);

            // Make the scrollPane match the width of the GoalTableRow
            currentGoalRowItem.prefWidthProperty().bind(scrollPane.widthProperty());
        }
    }

    /* Query the goal lists of the currentProfile and update the goal tables to display their contents */
    public void updateTables() {
        updateCurrentGoalRowTable();
    }

    /**
     * Changes the selected goalRow
     *
     * @param goalRow the goalRow to select
     */
    private void changeSelectedGoalRow(CurrentGoalRowItem goalRow) {
        if (selectedGoalRow != null) {
            selectedGoalRow.deselect();
        }
        selectedGoalRow = goalRow;
        displayGoalInformation();
        selectedGoalRow.select();
    }

    /* Fill the goal header with information about the goal which currently selected goal row wraps */
    private void displayGoalInformation() {
        // Hide the no goal selected text
        noGoalSelectedText.setText("");

        // Show goal information headings.
        showHeadings();

        // Get the goal which the selectedGoalRow wraps
        Goal selectedGoal = selectedGoalRow.getGoal();

        // Fill the goal progress indicatior
        goalProgressIndicator.setProgress(selectedGoal.getProgress() / 100);    // Takes a value between 0 and 1
        goalProgressIndicator.setDisable(false);

        // Allow editing
        editButton.setDisable(false);

        // Fill the text fields with the goal information
        descriptionText.setText(selectedGoal.getDescription());
        startDateText.setText(selectedGoal.getCreationDate().toString());
        expiryDateText.setText(selectedGoal.getExpiryDate().toString());
        remainingTimeText.setText(selectedGoal.getRemainingTimeDescription());
        currentAmountText.setText(selectedGoal.getAmountDescription("current"));
        totalAmountText.setText(selectedGoal.getAmountDescription("total"));
    }

    /** Remove a goalRow from the display and the GoalRow list it is contained in
     *
     * @param goalRow the goalRow to be removed
     */
    private void removeGoalRow(CurrentGoalRowController goalRow) {
        // vbox.getChildren().remove(goalRow);
        // remove from a list based on whether the goal it wraps is current or past
        // TODO: 3/10/18 Noel Bisson - implement
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

    /**
     * Resets the Goals tab by clearing all information on the selected activity.
     */
    private void reset() {
        selectedGoalRow = null;
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

        hideHeadings();
    }

    /**
     * Hides all the headings for the goal information table.
     */
    private void hideHeadings() {
        startDateHeading.setVisible(false);
        expiryDateHeading.setVisible(false);
        remainingTimeHeading.setVisible(false);
        currentAmountHeading.setVisible(false);
        requiredAmountHeading.setVisible(false);
    }

    /**
     * Shows all the headings for the goal information table.
     */
    private void showHeadings() {
        startDateHeading.setVisible(true);
        expiryDateHeading.setVisible(true);
        remainingTimeHeading.setVisible(true);
        currentAmountHeading.setVisible(true);
        requiredAmountHeading.setVisible(true);
    }
}
