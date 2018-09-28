package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;

public class ActivityCalendarItemController extends Controller {

    /** Text to display on the item. */
    @FXML
    private Text typeText;

    /** The activity of the calendar item. */
    Activity activity;

    public ActivityCalendarItemController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    @FXML
    public void initialize() {
        typeText.setText(activity.getType().toString());
    }

}
