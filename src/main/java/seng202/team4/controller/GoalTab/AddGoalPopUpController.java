package seng202.team4.controller.GoalTab;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.enums.GoalType;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Controller class for the add goal pop up.
 */
public class AddGoalPopUpController extends Controller {

    /** String constant for the distance goal type. */
    private static final String DISTANCE_GOAL_TYPE = "Distance";

    /** String constant for the duration goal type. */
    private static final String DURATION_GOAL_TYPE = "Duration";

    /** String constant for the calories goal type. */
    private static final String CALORIES_GOAL_TYPE = "Calories";


    /** Error Text used to display errors to the user. */
    @FXML
    private Text errorText;

    /** ChoiceBox for entering the goal type. */
    @FXML
    private ChoiceBox<String> goalTypeChoiceBox;

    /** Root Pane of the popup. */
    @FXML
    private AnchorPane rootPane;

    /** The TextField for entering the required amount to achieve the goal. */
    @FXML
    private TextField totalAmountTextField;

    /** TextField for entering the duration of the goal. */
    @FXML
    private TextField goalDurationTextField;

    /** ChoiceBox for entering the activity type. */
    @FXML
    private ChoiceBox<GoalType> activityTypeChoiceBox;

    /** Text for the heading of the total amount field. */
    @FXML
    private Text totalAmountText;

    /** Goals tab controller of the app. */
    private GoalsTabController goalsTabController;

    /**
     * Creates a new AddGoalPopUpController with the given application state manager.
     *
     * @param applicationStateManager The application state manager of the app.
     */
    public AddGoalPopUpController(ApplicationStateManager applicationStateManager, GoalsTabController goalsTabController) {
        super(applicationStateManager);
        this.goalsTabController = goalsTabController;
    }

    /** Initializes the AddGoalPopUp. */
    @FXML
    public void initialize() {
        activityTypeChoiceBox.getItems().addAll(GoalType.Walk, GoalType.Run);
        activityTypeChoiceBox.setValue(GoalType.Walk);

        goalTypeChoiceBox.getItems().addAll(DISTANCE_GOAL_TYPE, DURATION_GOAL_TYPE, CALORIES_GOAL_TYPE);
        goalTypeChoiceBox.setValue(DISTANCE_GOAL_TYPE);

        changeGoalType(DISTANCE_GOAL_TYPE);

        goalTypeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeGoalType(goalTypeChoiceBox.getItems().get((int) newValue));
            }
        });
    }

    /** Updates the fields based on what goal type is selected. */
    private void changeGoalType(String goalType) {
        switch (goalType) {
            case DISTANCE_GOAL_TYPE:
                totalAmountText.setText("Distance (km)");
                break;
            case DURATION_GOAL_TYPE:
                totalAmountText.setText("Duration (hours:min)");
                break;
            case CALORIES_GOAL_TYPE:
                totalAmountText.setText("Calories (cal)");
                break;
            default:
                totalAmountText.setText("Error");
                break;
        }
    }

    /**
     * Called when the user clicks the cancel button.
     *
     * Closes the popup.
     */
    @FXML
    public void cancel() {
        applicationStateManager.closePopUP(rootPane);
    }

    /**
     * Called when hte user clicks the add button.
     *
     * Tries create and add the goal to the users profile.
     * If it fails then the user is displayed a error message.
     */
    @FXML
    public void add() {

        String goalDurationString = goalDurationTextField.getText();
        String totalAmountString = totalAmountTextField.getText();

        int goalDuraiton = 0;
        boolean isValidGoalDuration = false;
        try {
            goalDuraiton = Integer.parseInt(goalDurationString);
            isValidGoalDuration = true;
        } catch (Exception e) {
            isValidGoalDuration = false;
        }

        if (!isValidGoalDuration) {
            errorText.setText(String.format("Goal expiry should be an integer, got %s", goalDurationString));
        } else if (goalDuraiton < Goal.MIN_GOAL_PERIOD || goalDuraiton > Goal.MAX_GOAL_PERIOD) {
            errorText.setText(String.format("Goal should expire between %s and %s from now.", Goal.MIN_GOAL_PERIOD, Goal.MAX_GOAL_PERIOD));
        } else if (goalTypeChoiceBox.getValue().equals(DISTANCE_GOAL_TYPE)) {
            double distance = 0.0;
            boolean isValidDistance = false;
            try {
                distance = Double.parseDouble(totalAmountString);
                isValidDistance = true;
            } catch (Exception e) {
                isValidDistance = false;
            }

            if (!isValidDistance) {
                errorText.setText(String.format("Distance should be a number, got '%s'", totalAmountString));
            } else if (distance < Goal.MIN_GOAL_DISTANCE || distance > Goal.MAX_GOAL_DISTANCE) {
                errorText.setText(String.format("Distance goal must be between %.2f km and %.2f km", Goal.MIN_GOAL_DISTANCE, Goal.MAX_GOAL_DISTANCE));
            } else {
                int goalNumber = applicationStateManager.getCurrentProfile().getNextGoalNumber();
                LocalDate creationDate = LocalDate.now();
                LocalDate expiryDate = creationDate.plusDays(goalDuraiton);
                try {
                    applicationStateManager.getCurrentProfile().addCurrentGoal(new Goal(goalNumber, 0, activityTypeChoiceBox.getValue(), creationDate.toString(), expiryDate.toString(), distance));
                    goalsTabController.refreshGoalTable();
                    applicationStateManager.closePopUP(rootPane);
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    GuiUtilities.displayErrorMessage("Failed to Create goal.", "Goal could not be added to the database");
                }

            }
        } else if (goalTypeChoiceBox.getValue().equals(DURATION_GOAL_TYPE)) {
            // Try to parse the duration string to check that it is in a valid format.
            Duration duration = null;
            boolean isValidDurationFormat = false;
            try {
                int hours = Integer.parseInt(totalAmountString.split(":")[0]);
                int minutes = Integer.parseInt(totalAmountString.split(":")[1]);
                duration = Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
                isValidDurationFormat = true;
            } catch (Exception e) {
                isValidDurationFormat = false;
            }

            if (!isValidDurationFormat) {
                errorText.setText(String.format("Duration should be hour:min, got '%s'", totalAmountString));
            } else if (duration.compareTo(Goal.MIN_GOAL_DURATION) < 0 || duration.compareTo(Goal.MAX_GOAL_DURATION) > 0) {
                errorText.setText(String.format("Duration should be between %02d:%02d and %02d:%02d", Goal.MIN_GOAL_DURATION.toHours(), Goal.MIN_GOAL_DURATION.toMinutes()%60, Goal.MAX_GOAL_DURATION.toHours(), Goal.MAX_GOAL_DURATION.toMinutes()%60));
            } else {
                int goalNumber = applicationStateManager.getCurrentProfile().getNextGoalNumber();
                LocalDate creationDate = LocalDate.now();
                LocalDate expiryDate = creationDate.plusDays(goalDuraiton);
                try {
                    applicationStateManager.getCurrentProfile().addCurrentGoal(new Goal(goalNumber, 0, activityTypeChoiceBox.getValue(), creationDate.toString(), expiryDate.toString(), duration.toString()));
                    goalsTabController.refreshGoalTable();
                    applicationStateManager.closePopUP(rootPane);
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    GuiUtilities.displayErrorMessage("Failed to Create goal.", "Goal could not be added to the database");
                }
            }
        } else if (goalTypeChoiceBox.getValue().equals(CALORIES_GOAL_TYPE)) {
            int calories = 0;
            boolean isValidCalories = false;
            try {
                calories = Integer.parseInt(totalAmountString);
                isValidCalories = true;
            } catch (Exception e) {
                isValidCalories = false;
            }

            if (!isValidCalories) {
                errorText.setText(String.format("Calories should be an integer, got '%s'", totalAmountTextField.getText()));
            } else if (calories < Goal.MIN_GOAL_CALORIES || calories > Goal.MAX_GOAL_CALORIES) {
                errorText.setText(String.format("Calories goal must be between %s and %s.", Goal.MIN_GOAL_CALORIES, Goal.MAX_GOAL_CALORIES));
            } else {
                int goalNumber = applicationStateManager.getCurrentProfile().getNextGoalNumber();
                LocalDate creationDate = LocalDate.now();
                LocalDate expiryDate = creationDate.plusDays(goalDuraiton);
                try {
                    applicationStateManager.getCurrentProfile().addCurrentGoal(new Goal(goalNumber, 0, activityTypeChoiceBox.getValue(), creationDate.toString(), expiryDate.toString(), calories));
                    goalsTabController.refreshGoalTable();
                    applicationStateManager.closePopUP(rootPane);
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    GuiUtilities.displayErrorMessage("Failed to Create goal.", "Goal could not be added to the database");
                }
            }
        }
    }
}
