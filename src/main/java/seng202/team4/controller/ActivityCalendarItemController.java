package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;

/** Controller class for an activity item in a calendar. */
public class ActivityCalendarItemController extends Controller {

    /** Background of a selected profile. */
    private final Background selectedBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** Background of an unselected profile. */
    private final Background unselectedBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** Root pane of the activity calendar item. */
    @FXML
    private AnchorPane rootPane;

    /** Text to display on the item. */
    @FXML
    private Text typeText;

    /** The activity of the calendar item. */
    private Activity activity;

    /** Creates a new ActivityCalendarItemController with the given application state manager. */
    public ActivityCalendarItemController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    /** Initializes the activity calendar item. */
    @FXML
    public void initialize() {
        typeText.setText(activity.getType().toString());
        deselect();
    }

    /** Applies the deselected background to the activity calendar item. */
    public void deselect() {
        rootPane.setBackground(unselectedBackground);
    }

    /** Applies the selected background to the activity calendar item. */
    public void select() {
        rootPane.setBackground(selectedBackground);
    }

    /** Gets the Activity associated with this activity calendar item. */
    public Activity getActivity() {
        return activity;
    }
}
