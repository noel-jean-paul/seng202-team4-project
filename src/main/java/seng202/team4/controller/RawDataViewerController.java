package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller for the raw data viewer popup, which displays the data rows for the selected activity
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

    /** The background for the pop window */
    @FXML
    private Rectangle popUpRectangle;

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

    /** The string which stores the date of the activity */
    private String date;


    /**
     * Constructor for the raw data viewer. A new raw data viewer is created everytime view raw data is selected for
     * an activity.
     * @param applicationStateManager the application state manager of the application
     * @param activity the selected activity to show the data rows of
     * @param activityTabController the controller of the activity tab
     */
    public RawDataViewerController(ApplicationStateManager applicationStateManager, Activity activity, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activity = activity;
        this.activityTabController = activityTabController;
    }

    /**
     * Initialises the popup
     * It also finds the maximum row number, so that new rows can be inserted into the database correctly
     */
    @FXML
    public void initialize() {
        for (DataRow row : activity.getRawData()) { //finds the maximum row number among data rows. Allows new rows to be added to the database by giving them the right row number
            if (row.getNumber() > maxRowNum) {
                maxRowNum = row.getNumber();
            }
        }
        applyEditsButton.setDisable(true);
        addRowButton.setDisable(true);
        deleteButton.setDisable(true);
        dataTableTitleText.setText("Data Rows for " + activity.getName());
        dataRowTable.setPlaceholder(new Text("You cannot edit the data of a manually created activity"));  //for manually imported activities
        updateDataRows();   //updates the table
        fillEditBoxes();
        dataRowTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
    }


    /**
     * Updates the current state of the data row list
     */
    public void updateDataRows() {
        dataRowTable.getItems().clear();    //allows the table to updated immediately rather than having to open the popup again for changes to be displayed
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
                deleteButton.setDisable(false);
                dateDatePicker.setValue(newSelection.getDate());
                timeTextField.setText(newSelection.getTime().toString());
                heartRateTextField.setText(Integer.toString(newSelection.getHeartRate()));
                latitudeTextField.setText((Double.toString(newSelection.getLatitude())));
                longitudeTextField.setText((Double.toString(newSelection.getLongitude())));
                elevationTextField.setText((Double.toString(newSelection.getElevation())));

                String date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //sets the values of all fields initially, to be compared later
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

    /** All fields filled by the user for a new data row are added to the activity after error checking */
    @FXML
    void addNewRow() {
        fieldErrorChecking(1);
    }


    /**
     * Checks each of the fields of the data row to see if they are within the accepted range, before adding them as a data row.
     * @param buttonType The type of button that has been pressed (1 = add row button).
     */
    public void fieldErrorChecking(int buttonType) {
        // Try to parse the date string to check that it is in a valid format.
        boolean isValidDateFormat = false;
        LocalDate dateSet = null;
        try {
            date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
        boolean isValidHeartRate = false;
        if (heartRateTextField.getText().isEmpty()) {
            isValidHeartRate = false;
        } else {
            int heartRate = Integer.parseInt(heartRateTextField.getText().trim());
            if (DataRow.minHeartRate <= heartRate && heartRate <= DataRow.maxHeartRate) {
                isValidHeartRate = true;
            } else {
                isValidHeartRate = false;
            }
        }

        //Check if the latitude is in the accepted range
        boolean isValidLatitude = false;
        if (latitudeTextField.getText().isEmpty()) {
            isValidLatitude = false;
        } else {
            double latitude = Double.parseDouble(latitudeTextField.getText().trim());
            if (DataRow.minLatitude <= latitude && latitude <= DataRow.maxLatitude) {
                isValidLatitude = true;
            } else {
                isValidLatitude = false;
            }
        }

        //Check if the longitude is in the accepted range
        boolean isValidLongitude = false;
        if (longitudeTextField.getText().isEmpty()) {
            isValidLongitude = false;
        } else {
            double longitude = Double.parseDouble(longitudeTextField.getText().trim());
            if (DataRow.minLongitude <= longitude && longitude <= DataRow.maxLongitude) {
                isValidLongitude = true;
            } else {
                isValidLongitude = false;
            }
        }

        //Check if the elevation is in the accepted range
        boolean isValidElevation = false;
        if (elevationTextField.getText().isEmpty()) {
            isValidElevation = false;
        } else {
            double elevation = Double.parseDouble(elevationTextField.getText().trim());
            if (DataRow.minElevation <= elevation && elevation <= DataRow.maxElevation) {
                isValidElevation = true;
            } else {
                isValidElevation = false;
            }
        }

        //check through all errors and see if the data row contains any of them
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
            errorMessage.setText("Elevation must be between " + DataRow.minElevation + " and " + DataRow.maxElevation);
        } else if (!isValidAddition()) {
            if (buttonType == 1) {
                errorMessage.setText("You cannot add a row that already exists");
            }
        } else {
            errorMessage.setText("");
            if (buttonType == 1) {  // User wants to add a row
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
            } else {    // User wants to edit the current row
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


    /**
     * Checks to see if the row to be added is a valid row
     * @return a boolean of whether or not the row can be added
     */
    public boolean isValidAddition() {
        boolean isValidAddition = false;
        if ((date.equals(prevDate) && (timeTextField.getText().trim().equals(prevTime)) && (heartRateTextField.getText().trim().equals(prevHeartRate))
                && (latitudeTextField.getText().trim().equals(prevLatitude))
                && (longitudeTextField.getText().trim().equals(prevLongitude)) && (elevationTextField.getText().trim().equals(prevElevation)))) {
            isValidAddition = false;
        } else {
            isValidAddition = true;
        }
        return isValidAddition;
    }

    /**
     * Deletes the selected rows from the activity, and removes them from the database as well
     * Called when the delete button is clicked.
     * Both multiple rows and a single row can be deleted
     */
    @FXML
    public void deleteRows() {
            List<DataRow> selectedRows = new ArrayList<>(dataRowTable.getSelectionModel().getSelectedItems());
            if (dataRowTable.getItems().size() - selectedRows.size() < 2) { //check that there are at least two rows in the data row list
                errorMessage.setText("You cannot have less than two data rows in an activity");
            } else {
                try {
                    activity.removeDataRows(selectedRows);
                    updateDataRows();
                } catch (java.sql.SQLException e) {
                    GuiUtilities.displayErrorMessage("Failed to remove data row.", "");
                    e.printStackTrace();
                    System.out.println("Could not remove data row from the database.");
                }
            }
    }


    /**
     * The function which closes the popup
     * It updates the activity and then the activity table when closing.
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
        activityTabController.updateCalendar();
        // Close the popup
        applicationStateManager.closePopUP(popupPane);
    }
}
