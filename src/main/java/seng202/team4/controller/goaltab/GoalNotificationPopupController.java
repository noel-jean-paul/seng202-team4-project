package seng202.team4.controller.goaltab;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.controller.MainScreenController;
import seng202.team4.model.data.Goal;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Controller class for the GoalNotificationPopup.
 */
public class GoalNotificationPopupController extends Controller {

    /**  The root pane of the popup. */
    @FXML
    private AnchorPane mainPane;

    /** VBox that will contain any goal notificiation text. */
    @FXML
    private VBox notificationsVbox;

    /** Scroll pane used to scroll through goal notificiations. */
    @FXML
    private ScrollPane scrollPane;

    /** The background of the popup window */
    @FXML
    private Rectangle popUpRectangle;

    /** The goal tab controller of the application */
    private GoalsTabController goalsTabController;

    /**
     * Constructs a new instance of a GoalNotificationPopupController
     * @param applicationStateManager The ApplicationStateManager of the app.
     * @param goalsTabController The GoalsTabController of the app.
     */
    public GoalNotificationPopupController(ApplicationStateManager applicationStateManager, GoalsTabController goalsTabController) {
        super(applicationStateManager);
        this.goalsTabController = goalsTabController;
    }

    /** Includes any initialisations needed after all the window elements have been generated */
    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
    }

    /** Closes the pop up.
     * Called when the user clicks the close button.
     */
    @FXML
    void close() {
        // Remove the expired and completed goals from the current goal list
        try {
            applicationStateManager.getCurrentProfile().updateCurrentGoals(true);
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("An Error occurred regarding the database", "See the error log" +
                    "for details");
            e.printStackTrace();
        }
        // Close the popup and refresh the table
        goalsTabController.refreshGoalTable();
        closePopup();
    }

    /** Changes the the screen to the Activity tab and closes the pop up. */
    @FXML
    void returnToActivityTab() {
        // Switch to the activity tab
        ((MainScreenController) applicationStateManager.getScreenController("MainScreen")).switchToActivityTab();
        // close the popup without the goals expiring from the current goals
        closePopup();
    }

    /** Add multiple notifications to the popup.
     *
     * @param goals a collection of goals to add notifications about
     */
    public void addNotifications(Collection<Goal> goals) {
        for (Goal goal: goals) {
            addNotification(goal);
        }
    }

    /** Add a notification to the popup, notification different for completed and expired goals
     *
     * @param goal the goal the notification is about
     */
    private void addNotification(Goal goal) {
        Label label;
        if (goal.isComplete()) {
            label = new Label(String.format("%s has been completed!", goal.getDescription()));
        } else {
            // Goal has expired
            label = new Label(String.format("%s has expired.", goal.getDescription()));
        }
        //label.setTextFill(Color.RED);
        label.setFont(Font.font(15));
        label.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        notificationsVbox.getChildren().add(label);
    }

    /** Close this popup */
    private void closePopup() {
        applicationStateManager.closePopUP(mainPane);
    }
}

