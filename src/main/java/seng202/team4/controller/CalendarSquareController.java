package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CalendarSquareController extends Controller {

    /** Text that displays the day of the calendar square. */
    @FXML
    private Text dayText;

    private int day = 0;

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
}
