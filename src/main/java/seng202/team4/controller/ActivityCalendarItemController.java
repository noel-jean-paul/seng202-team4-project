package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;

/** Controller class for an activity item in a calendar. */
public class ActivityCalendarItemController extends Controller {

    /** Text to display on the item. */
    @FXML
    private Text typeText;

    /** The activity of the calendar item. */
    Activity activity;

    /** Creates a new ActivityCalendarItemController with the given application state manager. */
    public ActivityCalendarItemController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    /** Initializes the activity calendar item. */
    @FXML
    public void initialize() {
        typeText.setText(activity.getType().toString());
    }

}
