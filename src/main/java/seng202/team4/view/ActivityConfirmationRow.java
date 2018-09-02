package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import seng202.team4.App;
import seng202.team4.controller.ActivityConfirmationRowController;
import seng202.team4.model.data.Activity;

public class ActivityConfirmationRow extends AnchorPane {

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
        System.out.println(activity.getDuration());
       // controller.setActivityDurationText(activity.getDuration().toString());
        //controller.setActivityTypeText(activity.getType().toString());
    }
}
