package seng202.team4.controller;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.Utilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Observable;

/**
 * Controller for the Activity Tab.
 */
public class ActivityTabController extends Controller {

    @FXML
    private TableView activityTable;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn distanceColumn;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private TableColumn averageSpeedColumn;

    @FXML
    private TableColumn caloriesColumn;

    @FXML
    private TableColumn typeColumn;

    @FXML
    private TableColumn durationColumn;


    private boolean isTableReorderable = true;

    /**
     * Constructor for the ActivityTabController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /** Initializes the activity tab. */
    @FXML
    public void initialize() {
        activityTable.setPlaceholder(new Text("No activates have been added yet."));
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        dateColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        distanceColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        timeColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        averageSpeedColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        caloriesColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));
        typeColumn.prefWidthProperty().bind(activityTable.widthProperty().divide(7));

        activityTable.setRowFactory( table -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    //TODO: Open popup with more information.
//                    ActivityInfoController activityInfoController = new ActivityInfoController(applicationStateManager);
//                    Pane infoPopup = Utilities.loadPane("ActivityInfo.fxml", activityInfoController);
//                    activityInfoController.setTestText(((Activity) row.getItem()).getName());
//
//
//                    applicationStateManager.displayPopUp(infoPopup);
//                    infoPopup.setOnMouseClicked(mouseEvent -> {
//                        if (activityInfoController.isClickOutsideRect(mouseEvent)) {
//                            applicationStateManager.closePopUP(infoPopup);
//                        }
//                    });
                }
            });
            return row;
        });
    }

    public void updateTable() {
        ObservableList<Activity> activitiesList = FXCollections.observableArrayList(applicationStateManager.getCurrentProfile().getActivityList());

        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity,String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDate>("date"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<Activity, Double>("distanceDisplayString"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalTime>("startTime"));
        averageSpeedColumn.setCellValueFactory(new PropertyValueFactory<Activity, Double>("averageSpeedDisplayString"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<Activity, Double>("caloriesDisplayString"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>("type"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>("durationString"));

        activityTable.setItems(activitiesList);

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

        ScrollBar scrollBarHorizontal = (ScrollBar) activityTable.lookup(".scroll-bar:hotizontal");
        scrollBarHorizontal.setVisible(false);

    }

    /**
     * The method that is called when the user clicks the add activity button.
     * Displays a prompt to the user on how they would like to add activities.
     */
    @FXML
    public void addActivities() {
        Pane popUp = Utilities.loadPane("ActivityImportTypePrompt.fxml", new ActivityImportTypePromptController(applicationStateManager, this));
        applicationStateManager.displayPopUp(popUp);
    }


    @FXML
    public void showPopup() {
        Pane activityPopUp = Utilities.loadPane("ActivityPopUpScreen.fxml", new ActivityPopUpScreenController(applicationStateManager, this));
        applicationStateManager.displayPopUp(activityPopUp);
    }
}
