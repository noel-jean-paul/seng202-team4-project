package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.GoalTab.CurrentGoalRowController;
import seng202.team4.controller.GoalTab.GoalsTabController;
import seng202.team4.model.data.Goal;

public class GoalRowItem extends AnchorPane {
    /**
     * Background of a selected profile.
     */
    private final Background selectedBackground = new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * Background of an unselected profile.
     */
    private final Background unselectedBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

    /* The goal which the row presents infomation about */
    private Goal goal;


    /**
     * Constructor for the GoalRowItem
     * @param controller The controller of GoalRowItem
     * @param goal the goal that is being displayed.
     * @param type the type of the goal row desired - either "Current" or "Past"
     */
    public GoalRowItem(CurrentGoalRowController controller, Goal goal, String type) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(String.format("view/%sGoalRow.fxml", type)));
        loader.setControllerFactory(c -> {return controller;});
        loader.setRoot(this);
        try {
            loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
        }
        // Set the goal and initialise the fields of the controller
        setGoal(goal);
        initialiseRow(controller);

        // Set the background of the row to deselected
        deselect();
    }

    public Goal getGoal() {
        return goal;
    }

    /**
     * Set the goal this row describes
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * Select the GoalRow  by applying the selected background.
     */
    public void select() {
        this.setBackground(selectedBackground);
    }

    /**
     * Deselects the GoalRow by applying the deselected background.
     */
    public void deselect() {
        this.setBackground(unselectedBackground);
    }

    /* Initializes the GoalTableRow using goal parameter */
    void initialiseRow(CurrentGoalRowController controller) {
        controller.setDescription(getGoal().getDescription());
        controller.setCreationDate(getGoal().getCreationDate().toString());
        controller.setExpiryDate(getGoal().getExpiryDate().toString());
        controller.setProgress(getGoal().getProgress());
    }
}
