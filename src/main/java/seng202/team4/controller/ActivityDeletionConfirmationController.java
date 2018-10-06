package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

/** Controller class for the activity deletion confirmation box */
public class ActivityDeletionConfirmationController extends Controller {

    @FXML
    private AnchorPane rootPane;

    /** The activity tab controller of the activities tab */
    private Activity activity;

    /** The activity tab controller of the activities tab */
    private ActivityTabController activityTabController;

    /** Creates a new activity deletion controller with the given ApplicationStateManager. */
    public ActivityDeletionConfirmationController(ApplicationStateManager applicationStateManager,
                                                  ActivityTabController actityTabController, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
        this.activityTabController = actityTabController;
    }

    /**
     * Called if user clicks the 'yes' button.
     * Deletes the activity, then closes the popup.
     */
    @FXML
    void yesClicked() {
        //System.out.println(activityTable.getSelectionModel().getSelectedItem());
        try {
            applicationStateManager.getCurrentProfile().removeActivity(activity);
            activityTabController.updateTable();
            //applicationStateManager.switchToScreen("ActivityTab");
            applicationStateManager.closePopUP(rootPane);
        } catch (java.sql.SQLException e){
            GuiUtilities.displayErrorMessage("Failed to remove Activity.", "");
            e.printStackTrace();
            System.out.println("Could not remove activity from the data base.");
        }
        applicationStateManager.closePopUP(rootPane);

    }

    /**
     * Called if user clicks the 'no' button.
     * closes the popup.
     */
    @FXML
    void noClicked() {
        applicationStateManager.closePopUP(rootPane);
    }

}
