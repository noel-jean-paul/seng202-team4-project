package seng202.team4.controller.activitytab;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.model.data.Activity;


/** Controller class for the activity deletion confirmation box */
public class ActivityDeletionConfirmationController extends Controller {

    /** The anchor pane of the of the popup */
    @FXML
    private AnchorPane rootPane;

    /** The background of the popup window */
    @FXML
    private Rectangle popUpRectangle;

    /** The activity tab controller of the activities tab */
    private Activity activity;

    /** The activity tab controller of the activities tab */
    private ActivityTabController activityTabController;

    /**
     *
     * @param applicationStateManager of the program
     * @param actityTabController the activity tab controller, containing the table to be updated after deletion
     * @param activity the activity being deleted
     */
    public ActivityDeletionConfirmationController(ApplicationStateManager applicationStateManager,
                                                  ActivityTabController actityTabController, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
        this.activityTabController = actityTabController;
    }

    /** Initialises the value of the rectangle once the popup window has been populated */
    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
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
            activityTabController.updateCalendar();
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
