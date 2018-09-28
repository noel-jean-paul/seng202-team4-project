package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CalendarSquareController extends Controller {

    /** Text that displays the day of the calendar square. */
    @FXML
    private Text dayText;

    /** VBox that holds the items of this calendar day. */
    @FXML
    private VBox itemsVbox;

    /** Day number of this calendar box. */
    private int day = 0;

    /** Creates a new CalendarSquareController with the given application state manager. */
    public CalendarSquareController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the calender view */
    @FXML
    public void initialize() {
        dayText.setText(Integer.toString(day));
    }

    /** Sets the day of the calendar square. */
    public void setDay(int day) {
        this.day = day;
        if (dayText != null) {
            dayText.setText(Integer.toString(day));
        }
    }

    /** Adds an item to the calendar box.
     * In this context an item is javafx pane.
     *
     * @param item The item to be added.
     */
    public void addItem(Pane item) {
        itemsVbox.getChildren().add(item);
    }
}
