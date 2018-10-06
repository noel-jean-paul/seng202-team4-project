package seng202.team4.controller.GoalTab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.GoalListPair;
import seng202.team4.view.CurrentGoalRowItem;
import seng202.team4.view.GoalRowItem;
import seng202.team4.view.PastGoalRowItem;

import java.sql.SQLException;


/**
 * Controller for the Goals Tab.
 */
public class GoalsTabController extends Controller {

    /** Button for switching between past and current goal lists */
    @FXML
    private Button toggleGoalListButton;

    /** Button for toggling calendar view. */
    @FXML
    private Button calendarViewButton;

    /** Button for editing the selected goal. */
    @FXML
    private Button editButton;

    /** Button for deleting the selected goal. */
    @FXML
    private Button deleteButton;

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

    /** VBox that holds the total field Text/TextField. */
    @FXML
    private VBox totalVbox;

    /** VBox that holds the expiry date field Text/TextField. */
    @FXML
    private VBox expiryDateVbox;

    /** TextField for entering the amount that is need to complete a goal. */
    private TextField totalAmountTextField;

    /** TextField for entering the expiry date of a goal. */
    private TextField expiryDateTextField;

    /** The currently selected goal. */
    private GoalRowItem selectedGoalRow = null;

    /** Boolean representing if the current goals or the past goals are currently populating the goal tab table */
    private boolean currentGoalTableDisplayed = true;

    /** Boolean representing if the user is currently editing a goal. */
    private boolean isEditing = false;

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
        // Reset the top goal bar to have no goal information displayed
        reset();

        // Create new text field for when the user chooses to edit a goal.
        totalAmountTextField = new TextField();
        expiryDateTextField = new TextField();
    }

    /** Display notications of the goals which were completed or expired
     *
     * @param goalLists a GoalListPair object contains the completed and expired goals to display
     */
    private void displayGoalNotifications(GoalListPair goalLists) {
        // Create a new GoalNotification Popup
        GoalNotificationPopupController goalNotificationPopupController = new GoalNotificationPopupController(applicationStateManager);
        Pane goalNotificationPopup = GuiUtilities.loadPane("GoalsNotificationPopup.fxml", goalNotificationPopupController);

        // Display notification of the goals which were completed
        goalNotificationPopupController.addNotifications(goalLists.getCompletedGoals());

        // Display notifaction of the goals that have expired
        goalNotificationPopupController.addNotifications(goalLists.getExpiredGoals());

        // Display the popup
        applicationStateManager.displayPopUp(goalNotificationPopup);
    }

    /** Check for any updates to the current goals due to activities being imported and display a notification about any
     *  goals which were completed or expired
     */
    private void updateGoals() {
        try {
            // Update the currentGoals of the currently loaded profile
            GoalListPair goalListPair = applicationStateManager.getCurrentProfile().updateCurrentGoals();
            // Display notications of which goals were completed and which expired
            displayGoalNotifications(goalListPair);
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("An error occurred regarding the database",
                    "Goal updates could not be completed successfully");
            e.printStackTrace();
        }
    }

    /** Updates the current profile's goals then fills the current GoalRow vbox using them */
    private void updateCurrentGoalRowTable() {
        // Clear the current table (vbox) of goals
        goalsListVbox.getChildren().clear();

        // Add each current Goal to the vbox children
        for (Goal goal: applicationStateManager.getCurrentProfile().getCurrentGoals()) {
            // Create a new pair of GoalRow controller and item
            GoalRowController controller = new GoalRowController(applicationStateManager);
            CurrentGoalRowItem currentGoalRowItem = new CurrentGoalRowItem(controller, goal);

            // When an item is selected, set the selected row to be that item
            currentGoalRowItem.setOnMouseClicked(event -> {changeSelectedGoalRow(currentGoalRowItem);});

            // Add the goalRowItem to the vbox which holds the current goals
            goalsListVbox.getChildren().add(currentGoalRowItem);

            // Make the scrollPane match the width of the GoalTableRow
            currentGoalRowItem.prefWidthProperty().bind(scrollPane.widthProperty());

            // Select the row which was selected when the goal tab was last selected and set it as the selectedGoalRow
            // Compare on the goal as this is not reinstantiated each time the goal tab is selected while the GoalRow is
            // Have to first check that the goalRow is not null to prevent nullPointerExceptions in the main comparison
            if (selectedGoalRow != null && goal.equals(selectedGoalRow.getGoal())) {
                currentGoalRowItem.select();
                selectedGoalRow = currentGoalRowItem;
            }
        }
    }

    /** Clears the goal table and populates it with the pat goals of the currently loaded profile */
    private void displayPastGoalRowTable() {
        // Clear the table (vbox) of goals
        goalsListVbox.getChildren().clear();

        // Add each current Goal to the vbox children
        for (Goal goal: applicationStateManager.getCurrentProfile().getPastGoals()) {
            // Create a new pair of GoalRow controller and item
            GoalRowController controller = new GoalRowController(applicationStateManager);
            PastGoalRowItem pastGoalRowItem = new PastGoalRowItem(controller, goal);

            // When an item is selected, set the selected row to be that item
            pastGoalRowItem.setOnMouseClicked(event -> {changeSelectedGoalRow(pastGoalRowItem);});

            // Add the goalRowItem to the vbox which holds the past goals
            goalsListVbox.getChildren().add(pastGoalRowItem);

            // Make the scrollPane match the width of the GoalTableRow
            pastGoalRowItem.prefWidthProperty().bind(scrollPane.widthProperty());

            // Select the row which was selected when the goal tab was last selected and set it as the selectedGoalRow
            // Compare on the goal as this is not reinstantiated each time the goal tab is selected while the GoalRow is
            // Have to first check that the goalRow is not null to prevent nullPointerExceptions in the main comparison
            if (selectedGoalRow != null && goal.equals(selectedGoalRow.getGoal())) {
                pastGoalRowItem.select();
                selectedGoalRow = pastGoalRowItem;
            }
        }
    }

    /**
     * Changes the selected goalRow
     *
     * @param goalRow the goalRow to select
     */
    private void changeSelectedGoalRow(GoalRowItem goalRow) {
        // close editable fields
        // TODO: 7/10/18 NB/MT close editable fields

        // If there is a goal row selected, deselect it
        if (selectedGoalRow != null) {
            selectedGoalRow.deselect();
        }
        // Select the new row
        selectedGoalRow = goalRow;
        displayGoalInformation();
        selectedGoalRow.select();
    }

    /** Fill the goal header with information about the goal which currently selected goal row wraps */
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

        // Allow editing (for current goals) and deleting.
        if (currentGoalTableDisplayed) {
            editButton.setDisable(false);
        } else {
            editButton.setDisable(true);    // do not allow editing of past goals
        }
        deleteButton.setDisable(false); // both current and past goals can be deleted

        // Fill the text fields with the goal information
        descriptionText.setText(selectedGoal.getDescription());
        startDateText.setText(selectedGoal.getCreationDate().toString());
        expiryDateText.setText(selectedGoal.getExpiryDate().toString());
        remainingTimeText.setText(selectedGoal.getRemainingTimeDescription());
        currentAmountText.setText(selectedGoal.getAmountDescription("current"));
        totalAmountText.setText(selectedGoal.getAmountDescription("total"));
    }

    @FXML
    void addGoal() {

    }

    @FXML
    void toggleCalendarView() {

    }

    @FXML
    void edit() {
        if (!isEditing) {
            // Sets the text of the text field to whats being displayed in the text.
            totalAmountTextField.setText(totalAmountText.getText());
            expiryDateTextField.setText(expiryDateText.getText());

            // Changes the Text to TextFields.
            totalVbox.getChildren().setAll(totalAmountTextField);
            expiryDateVbox.getChildren().setAll(expiryDateTextField);
            isEditing = true;

            // Set the button text to 'Done'
            //editButton.setText("Done");
        } else {
            // Changes the TextFields back to Text.
            totalVbox.getChildren().setAll(totalAmountText);
            expiryDateVbox.getChildren().setAll(expiryDateText);
            isEditing = false;
            // Set the button text back to 'Edit'
            //editButton.setText("Edit");
        }
    }

    /** Delete the currently selected goal from the profile's goals and redisplay the goal table it is contained in */
    @FXML
    void deleteGoal() {
        Goal selected = selectedGoalRow.getGoal();
        try {
            if (selected.isCurrent()) {
                applicationStateManager.getCurrentProfile().removeCurrentGoal(selected);
                updateCurrentGoalRowTable();
            } else {
                applicationStateManager.getCurrentProfile().removePastGoal(selected);
                displayPastGoalRowTable();
            }
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("An error occurred regarding the database while deleting.", "");
            e.printStackTrace();
        }
    }

    @FXML
    void toggleGoalList() {
        // Switch whether past or current goals are displayed and flip the text on the toggle list button
        if (currentGoalTableDisplayed) {
            displayPastGoalRowTable();
            toggleGoalListButton.setText("Current Goals");
            currentGoalTableDisplayed = false;
            // Change the expiry date column to be titled completion date
            expiryCompletionDate.setText("Completion Date");

        } else {
            updateCurrentGoalRowTable();    // Display the current goal table
            toggleGoalListButton.setText("Past Goals");
            currentGoalTableDisplayed = true;
            // Change the expiry date column to be titled expiry date
            expiryCompletionDate.setText("Expiry Date");
        }
    }

    /** Display whichever table was last displayed in the goal tab.
     *  Called when clicking on the goal tab.
     */
    public void displayGoalTable() {
        // Check for any updates to the goals due to activities being imported and display notifications about any changes
        updateGoals();
        // Display the table which was being displayed when the tab was unselected
        if (currentGoalTableDisplayed) {
            updateCurrentGoalRowTable();
        } else {
            displayPastGoalRowTable();
        }
    }

    /**
     * Resets the Goals tab by clearing all information on the selected activity.
     */
    public void reset() {
        selectedGoalRow = null;
        // Reset the progress indicator
        goalProgressIndicator.setProgress(0);
        goalProgressIndicator.setDisable(true);
        // Prevent the edit and delete buttons from being clicked
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        //Blank the text fields
        descriptionText.setText("");
        startDateText.setText("");
        expiryDateText.setText("");
        remainingTimeText.setText("");
        currentAmountText.setText("");
        totalAmountText.setText("");
        // Inform the user that there is no goal selected
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

    /** Getter for the SelectedGoalRow
     *
     * @return the currently selected goal row
     */
    public GoalRowItem getSelectedGoalRow() {
        return selectedGoalRow;
    }
}
