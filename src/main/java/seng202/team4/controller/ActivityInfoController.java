package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ActivityInfoController extends Controller {

    @FXML
    private Text testText;

    @FXML
    private Rectangle popUpRectangle;

    public ActivityInfoController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    public void setTestText(String text) {
        testText.setText(text);
    }

    public boolean isClickOutsideRect(MouseEvent mouseEvent) {
        return !popUpRectangle.getBoundsInParent().contains(mouseEvent.getX(), mouseEvent.getY());
    }


}
