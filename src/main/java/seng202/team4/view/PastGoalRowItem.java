package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import seng202.team4.App;
import seng202.team4.controller.GoalTab.CurrentGoalRowController;
import seng202.team4.controller.GoalTab.GoalsTabController;
import seng202.team4.model.data.Goal;

public class PastGoalRowItem extends GoalRowItem {
    /**
     * Constructor for PastGoalRowItem
     * @param controller The controller of PastGoalRowItem
     * @param goal the goal that is being displayed.
     */
    public PastGoalRowItem(CurrentGoalRowController controller, Goal goal) {
        super(controller, goal);
    }

    /** Initializes the GoalTableRow using the parameters of the goal it wraps */
    @Override
    void initialiseRow(CurrentGoalRowController controller) {
        super.initialiseRow(controller);
        controller.setExpiryDate(getGoal().getCompletionDate().toString());
    }
}
