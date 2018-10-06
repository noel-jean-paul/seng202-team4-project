package seng202.team4.controller.GoalTab;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import seng202.team4.App;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.model.data.Goal;

import java.util.Collection;

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

    /** Constructs a new instance of a GoalNotificationPopupController */
    GoalNotificationPopupController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Closes the pop up.
     * Called when the user clicks the close button.
     */
    @FXML
    void close() {
        applicationStateManager.closePopUP(mainPane);
    }

    @FXML
    void returnToActivityTab() {
        // change screen
        // close the popup without the goals being expired
        //applicationStateManager.change
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
}

