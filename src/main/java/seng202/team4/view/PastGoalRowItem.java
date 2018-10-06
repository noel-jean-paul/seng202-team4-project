package seng202.team4.view;

import seng202.team4.controller.GoalTab.GoalRowController;
import seng202.team4.model.data.Goal;

public class PastGoalRowItem extends GoalRowItem {
    /**
     * Constructor for PastGoalRowItem
     * @param controller The controller of PastGoalRowItem
     * @param goal the goal that is being displayed.
     */
    public PastGoalRowItem(GoalRowController controller, Goal goal) {
        super(controller, goal);
    }

    /** Initializes the GoalTableRow using the parameters of the goal it wraps */
    @Override
    void initialiseRow(GoalRowController controller) {
        super.initialiseRow(controller);
        // Set the completion date in the expiryDate field as this is now used to display completion date
        if (getGoal().isComplete()) {
            controller.setExpiryDate(getGoal().getCompletionDate().toString());
        } else {    // Goal is expired
            controller.setExpiryDate(String.format("Expired %s", getGoal().getExpiryDate().toString()));
        }
    }
}
