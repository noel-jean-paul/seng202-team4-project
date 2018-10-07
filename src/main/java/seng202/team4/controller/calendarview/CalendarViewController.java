package seng202.team4.controller.calendarview;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.model.data.CalendarItem;

import java.time.LocalDate;
import java.util.ArrayList;
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

    /** The currently selected activity. */
    private CalendarItemController selectedCalendarItemController = null;

    /** ArrayList of mouse events to be executed when a calendar item is clicked. */
    private ArrayList<EventHandler<MouseEvent>> mouseActionsList = new ArrayList<>();

    /** ArrayList of CalendarItems to be displayed in the Calendar. */
    private ArrayList<CalendarItem> calendarItems = new ArrayList<>();

    /**
     * Creates a new CalendarViewController with the given ApplicationStateManager.
     * @param applicationStateManager The ApplicationStateManager of the app.
     */
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
     * @param year The year to change to.
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

                    for (CalendarItem calendarItem: calendarItems) {
                        if (calendarItem.getDate().equals(LocalDate.of(year, month+1, day))) {
                            CalendarItemController calendarItemController = new CalendarItemController(applicationStateManager, calendarItem);
                            Pane activityItem = GuiUtilities.loadPane("CalendarItem.fxml", calendarItemController);
                            calendarSquareController.addItem(activityItem);

                            activityItem.prefWidthProperty().bind(calendarSquare.prefWidthProperty());

                            activityItem.setOnMouseClicked(event -> {
                                if (selectedCalendarItemController != null) {
                                    selectedCalendarItemController.deselect();
                                }
                                selectedCalendarItemController = calendarItemController;
                                selectedCalendarItemController.select();
                            });

                            for (EventHandler<MouseEvent> eventHandler: mouseActionsList) {
                                activityItem.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                            }
                        }
                    }
                }
                boxNum += 1;
            }
        }
    }

    /**
     * Gets the selected CalendarItem in the calendar.
     *
     * @return The selected CalendarItem if one is selected, null otherwise.
     */
    public CalendarItem getSelectedItem() {
        CalendarItem item = null;
        if (selectedCalendarItemController != null) {
            item = selectedCalendarItemController.getItem();
        }
        return item;
    }

    /**
     * Adds a Mouse Action listener to the Calendar Item.
     *
     * @param event The MouseEvent EventHandler to add to the CalendarItems.
     */
    public void addMouseClickActionToItems(EventHandler<MouseEvent> event) {
        mouseActionsList.add(event);
    }

    /**
     * Adds a CalendarItem to the calendar.
     *
     * @param calendarItem The calendarItem to add.
     */
    public void addCalendarItem(CalendarItem calendarItem) {
        this.calendarItems.add(calendarItem);
    }

    /** Refreshes the calendar. */
    public void refresh() {
        changeMonth(currentMonth, currentYear);
    }

    /** Clears the calendar. */
    public void clearCalendar() {
        calendarItems.clear();
    }
}
