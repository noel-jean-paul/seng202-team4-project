package seng202.team4.model.data;

import java.util.List;

/**
 * Class that stores completed and expired goals.
 */
public class GoalListPair {
    private List<Goal> expiredGoals;    // List of expired goals
    private List<Goal> completedGoals;  // List of completed goals

    /** Main Constructor for GoalListPair
     *
     * @param expiredGoals a list of goals which have expired
     * @param completedGoals a list of goals which have been completed
     */
    public GoalListPair(List<Goal> expiredGoals, List<Goal> completedGoals) {
        this.expiredGoals = expiredGoals;
        this.completedGoals = completedGoals;
    }

    /** Getter for expiredGoals
     *
     * @return the list of expired goals
     */
    public List<Goal> getExpiredGoals() {
        return expiredGoals;
    }

    /** Getter for completedGoals
     *
     * @return the list of completed goals
     */
    public List<Goal> getCompletedGoals() {
        return completedGoals;
    }

    /** Check if this object contains at least 1 completed goal or at least 1 expired goal
     *
     * @return true if at least one of the lists contain a goal, false otherwise
     */
    public boolean containsGoals() {
        return (expiredGoals.size() != 0 || completedGoals.size() != 0);
    }
}
