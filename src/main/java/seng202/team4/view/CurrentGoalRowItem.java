package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.CurrentGoalRowController;
import seng202.team4.controller.ProfileListItemController;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.ProfileKey;

public class CurrentGoalRowItem extends AnchorPane {
    /** Background of a selected profile. */
    private final Background selectedBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** Background of an unselected profile. */
    private final Background unselectedBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /* The goal which the row presents infomation about */
    private Goal goal;

    /**
     * Constructor for the currentGoalRowItem
     *
     * @param controller The controller of CurrentGoalRowItem
     * @param goal the goal that is being displayed.
     */
    public CurrentGoalRowItem(CurrentGoalRowController controller, Goal goal) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/CurrentGoalRow.fxml"));
        loader.setControllerFactory(c -> {return controller;});
        loader.setRoot(this);
        try {
            loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
        }
        // Set the goal and initialise the fields of the controller
        this.goal = goal;
        initialiseRow(controller);

        this.setBackground(unselectedBackground);
    }

    /* Initializes the GoalTableRow using goal parameter */
    private void initialiseRow(CurrentGoalRowController controller) {
        controller.setDescription(goal.getDescription());
        controller.setCreationDate(goal.getCreationDate().toString());
        controller.setExpiryDate(goal.getExpiryDate().toString());
        controller.setProgress(goal.getProgress());
    }

    /** Select the GoalRow  by applying the selected background. */
    public void select() {
        this.setBackground(selectedBackground);
    }

    /** Deselects the GoalRow by applying the deselected background. */
    public  void deselect() {
        this.setBackground(unselectedBackground);
    }

    /** Get the goal this row describes
     *
     * @return the goal object this row describes
     */
    public Goal getGoal() {
        return goal;
    }
}
