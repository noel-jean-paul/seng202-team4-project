package seng202.team4.model.data;

import java.time.LocalDate;

/** Interface for specifying methods of classes that can be put into calendar items. */
public interface CalendarItem {

    /**
     * Method for getting the date of the CalendarItem.
     *
     * @return The date of the CalendarItem as a LocalDate.
     */
    public LocalDate getDate();

    /**
     * Method for getting the display String of the CalendarItem.
     *
     * @return The String that should be displayed for the CalendarItem.
     */
    public String getDisplayString();
}
