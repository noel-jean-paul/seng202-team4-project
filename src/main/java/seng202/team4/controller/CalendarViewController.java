package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Math.ceil;

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
    private Text monthYearText;

    /** The current month being displayed. */
    private int currentMonth = 0;

    /** The current year being displayed. */
    private int currentYear = 0;

    public CalendarViewController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the calender view */
    @FXML
    public void initialize() {
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        changeMonth(currentMonth, currentYear);

    }

    /** Change to the previous month. */
    @FXML
    public void prevMonth() {
        currentMonth -= 1;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear -= 1;
        }
        changeMonth(currentMonth, currentYear);
    }

    /** Change to the next month. */
    @FXML
    public void nextMonth() {
        currentMonth += 1;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear += 1;
        }
        changeMonth(currentMonth, currentYear);
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

        monthYearText.setText(String.format("%s %s", calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH), year));

        int dayOffSet = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOffSet < 1) {
            dayOffSet = 7;
        }

        calendarGrid.getChildren().clear();
        int numberOfRows = (int) ceil((dayOffSet + calendar.getActualMaximum(Calendar.DAY_OF_MONTH))/7);
        int boxNum = 1;
        for (int rowNum = 0; rowNum < 6; rowNum++) {
            for (int colNum = 0; colNum < 7; colNum++) {

                int day = boxNum-dayOffSet+1;
                if (day >= 1 && day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    CalendarSquareController calendarSquareController = new CalendarSquareController(applicationStateManager);
                    Pane calendarSquare = GuiUtilities.loadPane("CalendarSquare.fxml", calendarSquareController);

                    calendarSquareController.setDay(day);
                    calendarGrid.add(calendarSquare, colNum, rowNum);
                    calendarSquare.prefWidthProperty().bind(calendarGrid.widthProperty().divide(7));
                    calendarSquare.prefHeightProperty().bind(calendarGrid.heightProperty().divide(numberOfRows));
                    calendarSquare.minWidthProperty().bind(calendarGrid.minWidthProperty().divide(7));
                    calendarSquare.minHeightProperty().bind(calendarGrid.minHeightProperty().divide(numberOfRows));

                    for (Activity activity: applicationStateManager.getCurrentProfile().getActivityList()) {
                        if (activity.getDate().equals(LocalDate.of(year, month+1, day))) {
                            ActivityCalendarItemController activityCalendarItemController = new ActivityCalendarItemController(applicationStateManager, activity);
                            Pane activityItem = GuiUtilities.loadPane("ActivityCalendarItem.fxml", activityCalendarItemController);
                            calendarSquareController.addItem(activityItem);
                        }
                    }
                }
                boxNum += 1;
            }
        }
    }
}
