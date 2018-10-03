package seng202.team4.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;

/** Superclass of CurrentGoalRowController and PastGoalRowController
 *  Allows GoalTabController methods to be genearalised to take in either GoalRow version
 */
class GoalRowController extends Controller {

    /** Constructor for the controller
     *
     * @param applicationStateManager the application state manager of the application
     */
    GoalRowController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }
}
