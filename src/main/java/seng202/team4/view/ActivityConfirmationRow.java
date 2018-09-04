package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.ActivityConfirmationRowController;
import seng202.team4.model.data.Activity;

/** The confirmation row that is displayed when activities are imported */
public class ActivityConfirmationRow extends AnchorPane {

    /** Background colour of a row when it is slightly shaeded. */
    private final Background shadedBackground = new Background( new BackgroundFill( Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY ) );

    /**
     * Constructor for the ActivityConfirmationRow.
     *
     * @param controller The controller of ActivityConfirmationRow.
     * @param activity The activity that the row is displaying.
     */
    public ActivityConfirmationRow(ActivityConfirmationRowController controller, Activity activity) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ActivityConfirmationRow.fxml"));
        loader.setControllerFactory(c -> {return controller;});
        loader.setRoot(this);
        try {
            loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
            e.printStackTrace();
        }

        controller.setActivityNameText(activity.getName());
        controller.setActivityDateText(activity.getDate().toString());
        controller.setActivityDistanceText("TODO");
       // controller.setActivityDurationText(activity.getDuration().toString());
        //controller.setActivityTypeText(activity.getType().toString());
    }

    /** Shades the background of the row. */
    public void applyShadedBackground() {
        this.setBackground(shadedBackground);
    }
}
