package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;

import java.util.Calendar;
import java.util.Locale;

/** Controller class for the calender view */
public class CalendarViewController extends Controller {

    /** The root Node of the calender view */
    @FXML
    private AnchorPane mainPane;

    /** The grid of the calendar. */
    @FXML
    private GridPane calendarGrid;

    /** The Text that displays the current month. */
    @FXML
    private Text monthText;

    /** The current month being displayed. */
    private int currentMonth = 0;

    public CalendarViewController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the calender view */
    @FXML
    public void initialize() {
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        changeMonth(currentMonth, 2018);

    }

    /** Change to the previous month. */
    @FXML
    public void prevMonth() {
        currentMonth -= 1;
        changeMonth(currentMonth, 2018);
    }

    /** Change to the next month. */
    @FXML
    public void nextMonth() {
        currentMonth += 1;
        changeMonth(currentMonth, 2018);
    }

    /**
     * Changes the calender view to a particular month.
     * @param month The month to change to, indexed from 0.
     */
    private void changeMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        monthText.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));

        int dayOffSet = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOffSet < 1) {
            dayOffSet = 7;
        }

        calendarGrid.getChildren().clear();
        int boxNum = 1;
        for (int rowNum = 0; rowNum < 6; rowNum++) {
            for (int colNum = 0; colNum < 7; colNum++) {
                CalendarSquareController calendarSquareController = new CalendarSquareController(applicationStateManager);
                Pane calendarSquare = GuiUtilities.loadPane("CalendarSquare.fxml", calendarSquareController);

                int day = boxNum-dayOffSet+1;
                if (day >= 1 && day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    calendarSquareController.setDay(day);
                    calendarGrid.add(calendarSquare, colNum, rowNum);
                }
                boxNum += 1;
            }
        }
    }
}
