package seng202.team4.controller;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DisplayMetrics.CalorieDisplayMetric;
import seng202.team4.model.data.DisplayMetrics.DistanceDisplayMetric;
import seng202.team4.model.data.DisplayMetrics.SpeedDisplayMetric;
import seng202.team4.model.data.enums.ActivityType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Controller for the Activity Tab.
 */
public class ActivityTabController extends Controller {

    /** The button that toggles between calendar view. */
    @FXML
    private Button calendarViewButton;

    /** the center pane that displays either an activity table or calendar. */
    @FXML
    private AnchorPane centerContentPane;

    /** The TableView that holds a list of activities. */
    @FXML
    private TableView activityTable;

    /** The TableColumn for Activity names. */
    @FXML
    private TableColumn nameColumn;

    /** The TableColumn for Activity dates. */
    @FXML
    private TableColumn dateColumn;

    /** The TableColumn for Activity distances. */
    @FXML
    private TableColumn distanceColumn;

    /** The TableColumn for Activity times. */
    @FXML
    private TableColumn timeColumn;

    /** The TableColumn for Activity average speeds. */
    @FXML
    private TableColumn averageSpeedColumn;

    /** The TableColumn for Activity calories. */
    @FXML
    private TableColumn caloriesColumn;

    /** The TableColumn for Activity types. */
    @FXML
    private TableColumn typeColumn;

    /** The TableColumn for Activity durations. */
    @FXML
    private TableColumn durationColumn;

    /** The label that displays the distance travelled */
    @FXML
    private Label distanceLabel;

    /** The label that displays the average speed */
    @FXML
    private Label speedLabel;

    /** The label that displays the calories burned */
    @FXML
    private Label caloriesLabel;

    /** The text that displays an error message to the user if there is no data present */
    @FXML
    private Text noDataText;

    /** The text that displays name of the distance metric*/
    @FXML
    private Text distanceText;

    /** The text that displays name of the speed metric*/
    @FXML
    private Text speedText;

    /** The text that displays name of the calories metric*/
    @FXML
    private Text caloriesText;

    /** The text that displays time period of the metrics, whether today, the last 7 days, or the last 30 days*/
    @FXML
    private Text metricsTitleText;

    /** The Button that shows the map of an activity to the user. */
    @FXML
    private Button showMapsButton;

    /** The Button that shows the graphs of an activity to the user. */
    @FXML
    private Button showGraphsButton;

    /** Pane for the CalendarView. */
    private Pane calendarView;

    /** Boolean that stores whether the table is currently reorderable. */
    private boolean isTableReorderable = true;

    /** Controller for the maps popup. */
    private MapsController mapsController;

    /** The maps popup Pane. */
    private Pane mapPane;

    /** Stores whether the calendar view is current being displayed. */
    private boolean isCalendarView = false;


    /** CalendarViewController of the calendar. */
    private CalendarViewController calendarViewController;



    /**
     * Constructor for the ActivityTabController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);

        mapsController = new MapsController(applicationStateManager);
        mapPane = GuiUtilities.loadPane("Maps.fxml", mapsController);
    }

    /**
     * Initializes the activity tab, creating the table as well as the context menu containing the options
     * to delete the an activity or view the raw data of that activity
     */
    @FXML
    public void initialize() {
        activityTable.setPlaceholder(new Text("No activities have been added yet."));
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Binds the width of the columns to be evenly distributed.
        nameColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        dateColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        distanceColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        timeColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        averageSpeedColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        caloriesColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        typeColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));
        durationColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(8));

        // Sets visibility of summary statistics labels.
        distanceLabel.setVisible(false);
        speedLabel.setVisible(false);
        caloriesLabel.setVisible(false);
        metricsTitleText.setVisible(false);

        ContextMenu tableRowMenu = new ContextMenu();

        //adds the option to delete the selected activity to the context menu
        MenuItem deleteActivityItem = new MenuItem("Delete");
        deleteActivityItem.setOnAction(event -> {
            Activity activity = (Activity) activityTable.getSelectionModel().getSelectedItem(); //gets the activity to be deleted

            Pane activityDeletionPopup = GuiUtilities.loadPane("ActivityDeletionConfirmation.fxml",
                    new ActivityDeletionConfirmationController(applicationStateManager, this, activity));   //calls the pane which allows the user to confirm deletion selection
            applicationStateManager.displayPopUp(activityDeletionPopup);
        });

        //adds the option to view the raw data of the selected activity to the context menu
        MenuItem displayRawData = new MenuItem("View Raw Data Rows");
        displayRawData.setOnAction(event -> {
            Activity selectedActivity = (Activity) activityTable.getSelectionModel().getSelectedItem();
            if (selectedActivity != null) {
                //opens the pane displaying the raw data
                Pane rawDataViewerPopup = GuiUtilities.loadPane("RawDataViewer.fxml", new RawDataViewerController(applicationStateManager, selectedActivity, this));
                applicationStateManager.displayPopUp(rawDataViewerPopup);
            }
        });

        //adds the options defined above to the context menu
        tableRowMenu.getItems().add(deleteActivityItem);
        tableRowMenu.getItems().add(displayRawData);

        activityTable.setRowFactory( tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                showMapsButton.setDisable(false);
                showGraphsButton.setDisable(false);
                if (event.getButton() == MouseButton.PRIMARY || activityTable.getItems().size() <= row.getIndex()) {
                    tableRowMenu.hide();
                }
            });

            row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    if (activityTable.getItems().get(row.getIndex()) != null) {
                        tableRowMenu.show(activityTable, event.getScreenX(), event.getScreenY());
                    }
                }
            });
            return row ;
        });

        tableRowMenu.setAutoHide(true);

        // Initialise Calendar view.
        calendarViewController = new CalendarViewController(applicationStateManager);
        calendarView = GuiUtilities.loadPane("CalendarView.fxml", calendarViewController);


    }

    /** Updates the contents of the activity Table. */
    public void updateTable() {
        // Clear the items from the table
        activityTable.getItems().clear();

        ObservableList<Activity> activitiesList = FXCollections.observableArrayList(applicationStateManager.getCurrentProfile().getActivityList());

        //Sets where the columns should get their values from.
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity,String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDate>("date"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<Activity, DistanceDisplayMetric>("distanceDisplayMetric"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalTime>("startTime"));
        averageSpeedColumn.setCellValueFactory(new PropertyValueFactory<Activity, SpeedDisplayMetric>("averageSpeedDisplayMetric"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<Activity, CalorieDisplayMetric>("caloriesDisplayMetric"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>("type"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>("durationString"));

        activityTable.setItems(activitiesList);

        // Makes sure the table columns can not be reordered/
        if (isTableReorderable) {
            TableHeaderRow headerRow = (TableHeaderRow) activityTable.lookup("TableHeaderRow");
            headerRow.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    headerRow.setReordering(false);
                }
            });
            isTableReorderable = false;
        }

        // Disables scroll bar.
        ScrollBar scrollBarHorizontal = (ScrollBar) activityTable.lookup(".scroll-bar:hotizontal");
        scrollBarHorizontal.setVisible(false);

    }

    /**
     * The method that is called when the user clicks the add activity button.
     * Displays a prompt to the user on how they would like to add activities.
     */
    @FXML
    public void addActivities() {
        Pane popUp = GuiUtilities.loadPane("ActivityImportTypePrompt.fxml", new ActivityImportTypePromptController(applicationStateManager, this));
        applicationStateManager.displayPopUp(popUp);
    }

    /**
     * Switches to calendar view.
     */
    @FXML
    public void toggleCalendarView() {
        if (!isCalendarView) {
            updateCalendar();

            // Add event to enable buttons if an item in the calendar is clicked.
            calendarViewController.addMouseClickActionToItems(event -> {
                showMapsButton.setDisable(false);
                showGraphsButton.setDisable(false);
            });

            calendarViewController.refresh();

            centerContentPane.getChildren().setAll(calendarView);
            calendarViewButton.setText("Table View");
            isCalendarView = true;
        } else {
            centerContentPane.getChildren().setAll(activityTable);
            calendarViewButton.setText("Calendar View");
            isCalendarView = false;
        }


    }

    /** Updates the calendar view. */
    public void updateCalendar() {
        calendarViewController.clearCalendar();

        // Add all the activities to the calendar.
        for (Activity activity: applicationStateManager.getCurrentProfile().getActivityList()) {
            calendarViewController.addCalendarItem(activity);
        }
    }

    /**
     * Displays the graphs popup for the selected activity in the table.
     * Is called when the user clicks the 'show graphs button'.
     */
    @FXML
    public void showGraphsPopup() {
        Activity activity = getSelectedActivity();
        if (activity != null) {
            Pane activityPopUp = GuiUtilities.loadPane("ActivityPopUpScreen.fxml", new ActivityPopUpScreenController(applicationStateManager, activity));
            applicationStateManager.displayPopUp(activityPopUp);
        }

    }

    /**
     * Displays the maps popup for the selected activity in the table.
     * Is called when the user clicks the 'show maps button'.
     */
    @FXML
    public void showMaps() {
        Activity activity = getSelectedActivity();
        if (activity != null) {
            if (activity.getRawData().size() == 0) {
                GuiUtilities.displayErrorMessage("No data found.", String.format("'%s' seems to have no gps data.", activity.getName()));
            } else {
                try {
                    mapsController.initMap(activity);   //Trys to display route, will raise and exception if it fails.
                    Task<Void> sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {

                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            mapsController.initMap(activity);
                        }
                    });
                    new Thread(sleeper).start();
                    applicationStateManager.displayPopUp(mapPane);

                } catch (Exception e) {
                    GuiUtilities.displayErrorMessage("Failed to load map.", "Try checking your internet connection.");

                    // Tries to reload map.
                    // Todo: Reload map 6/10/2018
//                    mapsController = new MapsController(applicationStateManager);
//                    mapPane = GuiUtilities.loadPane("Maps.fxml", mapsController);
                    showMaps();

                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * gets the daily metrics.
     */
    @FXML
    void getDailyMetrics() {
        metricsTitleText.setVisible(true);
        metricsTitleText.setText("Today's DisplayMetrics");
        int request = 1;
        LocalDate startDate = LocalDate.now();
        setLabels(request, startDate);
    }

    /**
     * gets the weekly metrics.
     */
    @FXML
    void getWeeklyMetrics() {
        metricsTitleText.setVisible(true);
        metricsTitleText.setText("Weekly DisplayMetrics");
        int request = 2;
        LocalDate startDate = LocalDate.now();
        setLabels(request, startDate);
    }

    /**
     * gets the monthly metrics.
     */
    @FXML
    void getMonthlyMetrics() {
        metricsTitleText.setVisible(true);
        metricsTitleText.setText("Monthly DisplayMetrics");
        int request = 3;
        LocalDate startDate = LocalDate.now();
        setLabels(request, startDate);
    }

    /**
     * sets the correct labels for the activity metrics.
     * @param request is the type of data the user wishes to see if request == 1, then
     *                looking for daily metrics, 2 for monthly, 3 for yearly.
     * @param startDate is the current date when the user is using the app.
     */
    void setLabels(int request, LocalDate startDate) {
        List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();
        long timeDifference = 0;
        double totalDistance = 0;
        double averageSpeed = 0;
        long totalTime = 0;
        double totalCalories = 0;
        for (Activity currentActivity : activityList) {
            timeDifference = ChronoUnit.DAYS.between(currentActivity.getDate(), startDate);
            if (request == 1) { //we want daily data
                if (timeDifference == 0) {
                    totalDistance += currentActivity.getDistance();
                    totalTime += currentActivity.getDuration().toMinutes();
                    totalCalories += currentActivity.getCaloriesBurned();
                }
            } else if (request == 2) { //we want weekly data
                if (timeDifference >= 0 && timeDifference <= 7) {
                    totalDistance += currentActivity.getDistance();
                    totalTime += currentActivity.getDuration().toMinutes();
                    totalCalories += currentActivity.getCaloriesBurned();
                }
            } else if (request == 3) { //we want monthly data
                if (timeDifference >= 0 && timeDifference <= 31) {
                    totalDistance += currentActivity.getDistance();
                    totalTime += currentActivity.getDuration().toMinutes();
                    totalCalories += currentActivity.getCaloriesBurned();
                }
            }
        }
        if (totalDistance == 0) {
            distanceLabel.setVisible(false);
            distanceText.setVisible(false);
            speedLabel.setVisible(false);
            speedText.setVisible(false);
            caloriesLabel.setVisible(false);
            caloriesText.setVisible(false);
            if (request == 1) {
                noDataText.setText("You have no activities recorded today");
            }
            if (request == 2) {
                noDataText.setText("You have no activities in the last week");
            }
            if (request == 3) {
                noDataText.setText("You have no activities in the last month");
            }
            noDataText.setVisible(true);
        } else {
            noDataText.setVisible(false);
            distanceLabel.setVisible(true);
            distanceText.setVisible(true);
            speedLabel.setVisible(true);
            speedText.setVisible(true);
            caloriesLabel.setVisible(true);
            caloriesText.setVisible(true);
            distanceLabel.setVisible(true);
            double speedKm = totalDistance / 1000.0;
            averageSpeed = (speedKm) / (totalTime / 60.0);
            String formattedDistance = String.format("%.01f", speedKm);
            String formattedSpeed = String.format("%.01f", averageSpeed);
            String formattedCalories = String.format("%.01f", totalCalories);
            distanceLabel.setText(formattedDistance + " km");
            speedLabel.setText(formattedSpeed + " km/h");
            caloriesLabel.setText(formattedCalories);
        }
    }

    /** Gets the selected activity from either the Table or calendar.
     *
     * @return The selected activity if there is one, null otherwise.
     */
    private Activity getSelectedActivity() {
        Activity activity;
        if (isCalendarView) {
            activity = (Activity) calendarViewController.getSelectedItem();
        } else {
            activity = (Activity) activityTable.getSelectionModel().getSelectedItem();
        }
        return activity;
    }

    /** Resets the ActivityTab. */
    public void reset() {
        showGraphsButton.setDisable(true);
        showMapsButton.setDisable(true);

        isCalendarView = true;
        toggleCalendarView();
    }
}
