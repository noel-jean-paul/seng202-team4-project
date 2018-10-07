package seng202.team4.controller.GoalTab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.controller.MainScreenController;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.GoalListPair;
import seng202.team4.view.CurrentGoalRowItem;
import seng202.team4.view.GoalRowItem;
import seng202.team4.view.PastGoalRowItem;

import java.sql.SQLException;
import java.time.Duration;


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

    /** Text for displaying any error messages when a goal is edited. */
    @FXML
    private Text errorText;

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

    /** DatePicker for entering the expiry date of a goal. */
    private DatePicker expiryDatePicker;

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
        expiryDatePicker = new DatePicker();
    }

    /** Display notications of the goals which were completed or expired
     *
     * @param goalLists a GoalListPair object contains the completed and expired goals to display
     */
    private void displayGoalNotifications(GoalListPair goalLists) {
        // Create a new GoalNotification Popup
        GoalNotificationPopupController goalNotificationPopupController =
                new GoalNotificationPopupController(applicationStateManager, this);
        Pane goalNotificationPopup = GuiUtilities.loadPane("GoalsNotificationPopup.fxml", goalNotificationPopupController);

        // Display notification of the goals which were completed
        goalNotificationPopupController.addNotifications(goalLists.getCompletedGoals());

        // Display notifaction of the goals that have expired
        goalNotificationPopupController.addNotifications(goalLists.getExpiredGoals());

        // Display the popup
        openNotificationPopup(goalNotificationPopup);
    }

    /** Check for any updates to the current goals due to activities being imported and display a notification about any
     *   goals which were completed or expired
     */
    private void updateGoals() {
        try {
            // Update the currentGoals of the currently loaded profile - Do not remove completed/expired goals from the current goals
            GoalListPair goalListPair = applicationStateManager.getCurrentProfile().updateCurrentGoals(false);
            // Display notications of which goals were completed and which expired if any completed/expired goals were found
            if (goalListPair.containsGoals()) {
                displayGoalNotifications(goalListPair);
            }
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
            expiryDatePicker.setValue(selectedGoalRow.getGoal().getExpiryDate());
            if (selectedGoalRow.getGoal().isCaloriesGoal()) {
                totalAmountTextField.setText(String.format("%s", selectedGoalRow.getGoal().getCaloriesBurned()));
            } else if (selectedGoalRow.getGoal().isDistanceGoal()) {
                if (selectedGoalRow.getGoal().getGoalDistance() < 1.0) {
                    totalAmountTextField.setText(String.format("%.1f", selectedGoalRow.getGoal().getGoalDistance()*1000));
                } else {
                    totalAmountTextField.setText(String.format("%.1f", selectedGoalRow.getGoal().getGoalDistance()));
                }

            } else if (selectedGoalRow.getGoal().isDurationGoal()) {
                totalAmountTextField.setText(String.format("%02d:%02d", selectedGoalRow.getGoal().getGoalDuration().toHours(), selectedGoalRow.getGoal().getGoalDuration().toMinutes()%60));
            }

            // Changes the Text to TextFields.
            totalVbox.getChildren().setAll(totalAmountTextField);
            expiryDateVbox.getChildren().setAll(expiryDatePicker);

            editButton.setText("Done");
            deleteButton.setText("Cancel");
            isEditing = true;

            // Set the button text to 'Done'
            //editButton.setText("Done");
        } else {
            boolean updateSuccessful = false;

            if (selectedGoalRow.getGoal().isCaloriesGoal() ) {
                int calories = 0;
                boolean isValidCalories = false;
                try {
                    calories = Integer.parseInt(totalAmountTextField.getText());
                    isValidCalories = true;
                } catch (Exception e) {
                    isValidCalories = false;
                }

                if (!isValidCalories) {
                    errorText.setText(String.format("Calories should be an integer, got '%s'", totalAmountTextField.getText()));
                } else if (calories < Goal.MIN_GOAL_CALORIES || calories > Goal.MAX_GOAL_CALORIES) {
                    errorText.setText(String.format("Calories goal must be between %s and %s.", Goal.MIN_GOAL_CALORIES, Goal.MAX_GOAL_CALORIES));
                } else {
                    try {
                        selectedGoalRow.getGoal().setCaloriesBurned(calories);
                        updateSuccessful = true;
                    } catch (java.sql.SQLException e) {
                        GuiUtilities.displayErrorMessage("Failed to update goal.", "");
                        e.printStackTrace();
                    }
                }
            } else if (selectedGoalRow.getGoal().isDistanceGoal()) {
                double distance = 0.0;
                boolean isValidDistance = false;
                try {
                    distance = Double.parseDouble(totalAmountTextField.getText());
                    isValidDistance = true;
                } catch (Exception e) {
                    isValidDistance = false;
                }

                if (!isValidDistance) {
                    errorText.setText(String.format("Distance should be a number, got '%s'", totalAmountTextField.getText()));
                } else if (distance < Goal.MIN_GOAL_DISTANCE || distance > Goal.MAX_GOAL_DISTANCE) {
                    errorText.setText(String.format("Distance goal must be between %s km and %s km", Goal.MIN_GOAL_DISTANCE, Goal.MAX_GOAL_DISTANCE));
                } else {
                    try {
                        selectedGoalRow.getGoal().setGoalDistance(distance);
                        updateSuccessful = true;
                    } catch (java.sql.SQLException e) {
                        GuiUtilities.displayErrorMessage("Failed to update goal.", "");
                        e.printStackTrace();
                    }
                }
            } else if (selectedGoalRow.getGoal().isDurationGoal()) {
                // Try to parse the duration string to check that it is in a valid format.
                Duration duration = null;
                boolean isValidDurationFormat = false;
                try {
                    int hours = Integer.parseInt(totalAmountTextField.getText().split(":")[0]);
                    int minutes = Integer.parseInt(totalAmountTextField.getText().split(":")[1]);
                    duration = Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
                    isValidDurationFormat = true;
                } catch (Exception e) {
                    isValidDurationFormat = false;
                }

                if (!isValidDurationFormat) {
                    errorText.setText(String.format("Duration should be hh:mm, got '%s'", totalAmountTextField.getText()));
                } else if (duration.compareTo(Goal.MIN_GOAL_DURATION) < 0 || duration.compareTo(Goal.MAX_GOAL_DURATION) > 0) {
                    errorText.setText(String.format("Duration should be between %02d:%02d and %02d:%02d", Goal.MIN_GOAL_DURATION.toHours(), Goal.MIN_GOAL_DURATION.toMinutes()%60, Goal.MAX_GOAL_DURATION.toHours(), Goal.MAX_GOAL_DURATION.toMinutes()%60));
                } else {
                    try {
                        selectedGoalRow.getGoal().setGoalDuration(duration);
                        updateSuccessful = true;
                    } catch (java.sql.SQLException e) {
                        GuiUtilities.displayErrorMessage("Failed to update goal.", "");
                        e.printStackTrace();
                    }
                }
            }

            if (updateSuccessful) {
                try {
                    selectedGoalRow.getGoal().setExpiryDate(expiryDatePicker.getValue().toString());
                    turnEditingOff();
                } catch (java.sql.SQLException e) {
                    GuiUtilities.displayErrorMessage("Failed to update goal.", "");
                    e.printStackTrace();
                }

            }
        }

    }

    /** Method that is called when the user clicks the delete/cancel button. */
    @FXML
    public void deleteCancelClick() {
        if (!isEditing) {
            deleteGoal();
        } else {
            turnEditingOff();
            errorText.setText("");
        }
    }

    /** Turns the editing of the selected goal off. */
    private void turnEditingOff() {
        displayGoalInformation();

        // Changes the TextFields back to Text.
        totalVbox.getChildren().setAll(totalAmountText);
        expiryDateVbox.getChildren().setAll(expiryDateText);

        errorText.setText("");
        editButton.setText("Edit");
        deleteButton.setText("Delete");
        isEditing = false;
    }

    /** Delete the currently selected goal from the profile's goals and redisplay the goal table it is contained in */
    private void deleteGoal() {
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
    public void toggleGoalList() {
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

    /**
     * Update the goals for completion and expiry and display whichever table was last displayed in the goal tab.
     *  Called when clicking on the goal tab.
     */
    public void displayGoalTable() {
        // Check for any updates to the goals due to activities being imported and display notifications about any changes
        updateGoals();
        refreshGoalTable();
    }

    /** Refresh the goal table currently being displayed. Does not update the goals for completion
     *   and expiry. */
    public void refreshGoalTable() {
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
        errorText.setText("");

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

    /** Displays the notification popup passed in if the goalsTab is currently being displayed.
     *
     * @param goalNotificationPopup the popup to display
     */
    private void openNotificationPopup(Pane goalNotificationPopup) {
        if (((MainScreenController) applicationStateManager.getScreenController("MainScreen")).isOnGoalsTab()) {
            applicationStateManager.displayPopUp(goalNotificationPopup);
        }
    }
}
