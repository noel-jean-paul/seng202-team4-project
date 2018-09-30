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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


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

    /** Activity variable, holds the current activity's data */
    private Activity activity;

    /**
     *
     * @param applicationStateManager the application state manager of the application
     * @param activity the current selected activity, of which we wish to view the raw data
     */
    public RawDataViewerController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    /**
     * The function which initialises the popup
     */
    @FXML
    public void initialize() {
        dataTableTitleText.setText("Data Rows for " + activity.getName());
        dataRowTable.setPlaceholder(new Text("There are no data points available for this activity"));  //for manually imported activities
        updateDataRows();   //updates the table
        ContextMenu dataTableRowMenu = new ContextMenu();

        MenuItem deleteDataRowItem = new MenuItem("Delete Row");
        deleteDataRowItem.setOnAction(event -> {
            try {
                activity.removeDataRow((DataRow) dataRowTable.getSelectionModel().getSelectedItem());
                updateDataRows();
            } catch (java.sql.SQLException e){
                GuiUtilities.displayErrorMessage("Failed to remove data row.", "");
                e.printStackTrace();
                System.out.println("Could not remove data row from the data base.");
            }
        });


        dataTableRowMenu.getItems().add(deleteDataRowItem);

        dataRowTable.setRowFactory( tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY || dataRowTable.getItems().size() <= row.getIndex()) {
                    dataTableRowMenu.hide();
                }
            });

            row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    if (dataRowTable.getItems().get(row.getIndex()) != null) {
                        dataTableRowMenu.show(dataRowTable, event.getScreenX(), event.getScreenY());
                    }
                }
            });
            return row ;
        });

        dataTableRowMenu.setAutoHide(true);
    }


    /**
     * Updates the current state of the data row list
     */
    public void updateDataRows() {
        ObservableList<DataRow> dataList = FXCollections.observableArrayList(activity.getRawData());
        Collections.reverse(dataList); //reverses the list to ensure the data is displayed in the correct order in the table

        dateColumn.setCellValueFactory(new PropertyValueFactory<DataRow, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, LocalTime>("time"));
        heartRateColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("heartRate"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("longitude"));
        elevationColumn.setCellValueFactory(new PropertyValueFactory<DataRow, Integer>("elevation"));

        dataRowTable.setItems(dataList);
    }


    /**
     * The function which closes the popup
     */

    @FXML
    void closePopUp() {
        applicationStateManager.closePopUP(popupPane);
    }


}
