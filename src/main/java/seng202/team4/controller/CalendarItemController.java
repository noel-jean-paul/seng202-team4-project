package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng202.team4.model.data.CalendarItem;

/** Controller class for an activity item in a calendar. */
public class CalendarItemController extends Controller {

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

    /** The CalendarItem being stored calendar item view. */
    private CalendarItem item;

    /** String to be displayed in the calendar item. */
    private String displayString;

    /**
     * Creates a new CalendarItemController with the given application state manager.
     * @param applicationStateManager The ApplicationStateManager of the app.
     * @param item The CalendarItem of the CalendarItemView.
     */
    public CalendarItemController(ApplicationStateManager applicationStateManager, CalendarItem item) {
        super(applicationStateManager);
        this.item = item;
        this.displayString = item.getDisplayString();
    }

    /** Initializes the activity calendar item. */
    @FXML
    public void initialize() {
        typeText.setText(displayString);
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

    /**
     * Gets the Activity associated with this activity calendar item.
     *
     * @return The CalendarItem being displayed in the CalendarItem view.
     */
    public CalendarItem getItem() {
        return item;
    }
}
