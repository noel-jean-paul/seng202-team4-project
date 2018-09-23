package seng202.team4;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import seng202.team4.controller.Controller;

import javax.swing.text.TableView;

/**
 * Class for utility functions.
 */
public abstract class GuiUtilities {

    /**
     * Initializes, loads and returns a JavaFX pane from the given arguments.
     *
     * @param fxmlFilename The name of the fxml file that describes the pane.
     * @param controller The controller class for the pane.
     * @return A newly created pane.
     * @throws Exception If the function fails to load the program.
     */
    public static Pane loadPane(String fxmlFilename, Controller controller)  {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/" + fxmlFilename));
        loader.setControllerFactory(c -> {return controller;});

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
            e.printStackTrace();
        }

        return pane;
    }
}
