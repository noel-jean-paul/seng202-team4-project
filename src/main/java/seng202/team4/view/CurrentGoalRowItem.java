package seng202.team4.view;

import seng202.team4.controller.GoalTab.GoalRowController;
import seng202.team4.model.data.Goal;

public class CurrentGoalRowItem extends GoalRowItem {
    /**
     * Constructor for the currentGoalRowItem
     * @param controller The controller of CurrentGoalRowItem
     * @param goal the goal that is being displayed.
     */
    public CurrentGoalRowItem(GoalRowController controller, Goal goal) {
        super(controller, goal);
    }

    /** Initializes the GoalTableRow using the parameters of the goal it wraps */
    @Override
    void initialiseRow(GoalRowController controller) {
        super.initialiseRow(controller);
        controller.setExpiryDate(getGoal().getExpiryDate().toString());
    }
}
