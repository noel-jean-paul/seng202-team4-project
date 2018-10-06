package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import seng202.team4.App;
import seng202.team4.controller.GoalTab.CurrentGoalRowController;
import seng202.team4.model.data.Goal;

public class CurrentGoalRowItem extends GoalRowItem {
    /**
     * Constructor for the currentGoalRowItem
     * @param controller The controller of CurrentGoalRowItem
     * @param goal the goal that is being displayed.
     */
    public CurrentGoalRowItem(CurrentGoalRowController controller, Goal goal) {
        super(controller, goal, "Current");
    }
}
