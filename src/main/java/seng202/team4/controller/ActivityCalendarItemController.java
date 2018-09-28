package seng202.team4.controller;

import seng202.team4.model.data.Activity;

public class ActivityCalendarItemController extends Controller {

    /** The activity of the calendar item. */
    Activity activity;

    public ActivityCalendarItemController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }


}
