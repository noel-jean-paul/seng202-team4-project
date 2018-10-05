package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Controller for the raw data viewer popup
 */
public class RawDataViewerController extends Controller {

    /** The anchor pane of the raw data viewer popup */
    @FXML
    private AnchorPane popupPane;

    /** The table displaying the data rows */
    @FXML
    private TableView<DataRow> dataRowTable;

    /** The column that displays the date the data row was recorded */
    @FXML
    private TableColumn<DataRow, LocalDate> dateColumn;

    /** The column that displays the time the data row was recorded*/
    @FXML
    private TableColumn<DataRow, LocalTime> timeColumn;

    /** The column that displays the heart rate at the time the data row was recorded */
    @FXML
    private TableColumn<DataRow, Integer> heartRateColumn;

    /** The column that displays the latitude at the time the data row was recorded */
    @FXML
    private TableColumn<DataRow, Integer> latitudeColumn;

    /** The column that displays the longitude at the time the data row was recorded */
    @FXML
    private TableColumn<DataRow, Integer> longitudeColumn;

    /** The column that displays the elevation at the time the data row was recorded */
    @FXML
    private TableColumn<DataRow, Integer> elevationColumn;

    /** The title text with the selected activity's name */
    @FXML
    private Text dataTableTitleText;

    /**The date picker which displays the selected date */
    @FXML
    private DatePicker dateDatePicker;

    /**The text field which displays the selected time */
    @FXML
    private TextField timeTextField;

    /**The text field which displays the selected heart rate */
    @FXML
    private TextField heartRateTextField;

    /**The text field which displays the selected latitude */
    @FXML
    private TextField latitudeTextField;

    /**The text field which displays the selected longitude */
    @FXML
    private TextField longitudeTextField;

    /**The text field which displays the selected elevation */
    @FXML
    private TextField elevationTextField;

    /** The button which when clicked applies any edits made to the data */
    @FXML
    private Button applyEditsButton;

    /** The button which when clicked adds a new data row to the raw data */
    @FXML
    private Button addRowButton;

    /** The button which when clicked deletes the selected row */
    @FXML
    private Button deleteButton;

    /** The text which displays the error message if you enter incorrect data */
    @FXML
    private Text errorMessage;

    /** Activity variable, holds the current activity's data */
    private Activity activity;

    /** The activity tab controller of the activities tab */
    private ActivityTabController activityTabController;

    /** The maximum row number currently in the data row list */
    private int maxRowNum = 0;

    /**The strings which store the state of the selected data row */
    private String prevDate;
    private String prevTime;
    private String prevHeartRate;
    private String prevLatitude;
    private String prevLongitude;
    private String prevElevation;


    /** Constructor for the raw data viewer. A new raw data viewer is created everytime view raw data is selected for
     *   an activity.
     *
     * @param applicationStateManager the application state manager of the application
     * @param activity the current selected activity, of which we wish to view the raw data
     */
    public RawDataViewerController(ApplicationStateManager applicationStateManager, Activity activity, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activity = activity;
        this.activityTabController = activityTabController;
    }

    /**
     * The function which initialises the popup
     */
    @FXML
    public void initialize() {
        for (DataRow row : activity.getRawData()) {
            if (row.getNumber() > maxRowNum) {
                maxRowNum = row.getNumber();
            }
        }
        displayPopUp();
    }


    private void displayPopUp() {
        applyEditsButton.setDisable(true);
        addRowButton.setDisable(true);
        dataTableTitleText.setText("Data Rows for " + activity.getName());
        dataRowTable.setPlaceholder(new Text("There are no data points available for this activity"));  //for manually imported activities
        updateDataRows();   //updates the table
        fillEditBoxes();


    }


    /**
     * Updates the current state of the data row list
     */
    public void updateDataRows() {
        dataRowTable.getItems().clear();
        ObservableList<DataRow> dataList = FXCollections.observableArrayList(activity.getRawData());

        dateColumn.setCellValueFactory(new PropertyValueFactory<DataRow, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, LocalTime>("time"));
        heartRateColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("heartRate"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("longitude"));
        elevationColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("elevation"));

        dataRowTable.setItems(dataList);
    }


    /**
     * Auto fills all the edit boxes, which the user can then edit if they wish
     */
    public void fillEditBoxes() {
        dataRowTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                errorMessage.setText("");
                applyEditsButton.setDisable(false);
                addRowButton.setDisable(false);
                dateDatePicker.setValue(newSelection.getDate());
                timeTextField.setText(newSelection.getTime().toString());
                heartRateTextField.setText(Integer.toString(newSelection.getHeartRate()));
                latitudeTextField.setText((Double.toString(newSelection.getLatitude())));
                longitudeTextField.setText((Double.toString(newSelection.getLongitude())));
                elevationTextField.setText((Double.toString(newSelection.getElevation())));

                String date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                prevDate = date;
                prevTime = timeTextField.getText();
                prevHeartRate = heartRateTextField.getText();
                prevLatitude = latitudeTextField.getText();
                prevLongitude = longitudeTextField.getText();
                prevElevation = elevationTextField.getText();

            }
        });
    }


    /**
     * All edits made by the user are applied to the raw data row
     */
    @FXML
    public void applyEdits() {
        fieldErrorChecking(0);
    }

    @FXML
    void addNewRow() {
        fieldErrorChecking(1);
    }


    /**
     * checks each of the fields of data row to see if they are within the accepted range, before adding them as a data row
     */
    public void fieldErrorChecking(int buttonType) {
        // TODO: 4/10/18 Matt_M these should be refactored into functions in DataRow - Noel
        // Try to parse the date string to check that it is in a valid format.
        String date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateSet = null;
        boolean isValidDateFormat = false;
        try {
            dateSet = LocalDate.parse(date);
            isValidDateFormat = true;
        } catch (Exception e) {
            isValidDateFormat = false;
        }

        //Try to parse the time string to check that it is in a valid format.
        String time = timeTextField.getText().trim();
        LocalTime timeSet = null;
        boolean isValidTimeFormat = false;
        try {
            timeSet = LocalTime.parse(time);
            isValidTimeFormat = true;
        } catch (Exception e) {
            isValidTimeFormat = false;
        }

        //Check if the heart rate is in the accepted range
        int heartRate = Integer.parseInt(heartRateTextField.getText().trim());
        boolean isValidHeartRate = false;
        if (DataRow.minHeartRate <= heartRate && heartRate <= DataRow.maxHeartRate) {
            isValidHeartRate = true;
        } else {
            isValidHeartRate = false;
        }

        //Check if the latitude is in the accepted range
        double latitude = Double.parseDouble(latitudeTextField.getText().trim());
        boolean isValidLatitude = false;
        if (DataRow.minLatitude <= latitude && latitude <= DataRow.maxLatitude) {
            isValidLatitude = true;
        } else {
            isValidLatitude = false;
        }

        //Check if the longitude is in the accepted range
        double longitude = Double.parseDouble(longitudeTextField.getText().trim());
        boolean isValidLongitude = false;
        if (DataRow.minLongitude <= longitude && longitude <= DataRow.maxLongitude) {
            isValidLongitude = true;
        } else {
            isValidLongitude = false;
        }

        //Check if the elevation is in the accepted range
        double elevation = Double.parseDouble(elevationTextField.getText().trim());
        boolean isValidElevation = false;
        if (DataRow.minElevation <= elevation && elevation <= DataRow.maxElevation) {
            isValidElevation = true;
        } else {
            isValidElevation = false;
        }

        //Check if the row already exists
        boolean isValidAddition = false;
        if ((date.equals(prevDate) && (timeTextField.getText().trim().equals(prevTime)) && (heartRateTextField.getText().trim().equals(prevHeartRate)) && (latitudeTextField.getText().trim().equals(prevLatitude))
                && (longitudeTextField.getText().trim().equals(prevLongitude)) && (elevationTextField.getText().trim().equals(prevElevation)))) {
            isValidAddition = false;
        } else {
            isValidAddition = true;
        }



        if (!isValidDateFormat) {
            errorMessage.setText("Date should be in the form dd/mm/yyyy");
        } else if (!isValidTimeFormat) {
            errorMessage.setText("Time should be in the form hh:mm:ss");
        } else if (!isValidHeartRate) {
            errorMessage.setText("Heart rate must be between " + DataRow.minHeartRate + " and " + DataRow.maxHeartRate);
        } else if (!isValidLatitude) {
            errorMessage.setText("Latitude must be between " + DataRow.minLatitude + " and " + DataRow.maxLatitude);
        } else if (!isValidLongitude) {
            errorMessage.setText("Longitude must be between " + DataRow.minLongitude + " and " + DataRow.maxLongitude);
        } else if (!isValidElevation) {
            errorMessage.setText("Longitude must be between " + DataRow.minElevation + " and " + DataRow.maxElevation);
        } else if (!isValidAddition) {
            if (buttonType == 1) {
                errorMessage.setText("You cannot add a row that already exists");
            }
        } else {
            errorMessage.setText("");
            if (buttonType == 1) {  // Add row case
                try {
                    int rowNum = maxRowNum + 1;
                    DataRow newRow = new DataRow(rowNum, date, timeTextField.getText(), Integer.parseInt(heartRateTextField.getText()),
                            Double.parseDouble(latitudeTextField.getText()), Double.parseDouble(longitudeTextField.getText()), Double.parseDouble(elevationTextField.getText()));
                    activity.addDataRow(newRow);
                    maxRowNum++;
                    updateDataRows();
                } catch (java.sql.SQLException e) {
                    GuiUtilities.displayErrorMessage("An SQL exception was raised", e.getMessage());
                }
            } else {    // Edit row case
                try {
                    dataRowTable.getSelectionModel().getSelectedItem().setDate(date);
                    dataRowTable.getSelectionModel().getSelectedItem().setTime(timeTextField.getText());
                    dataRowTable.getSelectionModel().getSelectedItem().setHeartRate(Integer.parseInt(heartRateTextField.getText()));
                    dataRowTable.getSelectionModel().getSelectedItem().setLatitude(Double.parseDouble(latitudeTextField.getText()));
                    dataRowTable.getSelectionModel().getSelectedItem().setLongitude(Double.parseDouble(longitudeTextField.getText()));
                    dataRowTable.getSelectionModel().getSelectedItem().setElevation(Double.parseDouble(elevationTextField.getText()));
                    updateDataRows();
                } catch (java.sql.SQLException e) {
                    GuiUtilities.displayErrorMessage("An SQL exception was raised.", e.getMessage());
                }
            }
        }
    }


    @FXML
    public void deleteRows() {
        try {
            activity.removeDataRow((DataRow) dataRowTable.getSelectionModel().getSelectedItem());
            updateDataRows();
        } catch (java.sql.SQLException e){
            GuiUtilities.displayErrorMessage("Failed to remove data row.", "");
            e.printStackTrace();
            System.out.println("Could not remove data row from the database.");
        }
    }


    /**
     * The function which closes the popup
     */
    @FXML
    void closePopUp() {
        try {
            // Update the activity in case it's raw data has been changed
            activity.updateActivity();
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("A problem occurred with the database.", e.getMessage());
            System.out.println("A database error occurred when updating the activity.");
            e.printStackTrace();
        }
        // Update the table holding the activities to display the new values
        activityTabController.updateTable();
        // Close the popup
        applicationStateManager.closePopUP(popupPane);
    }
}
