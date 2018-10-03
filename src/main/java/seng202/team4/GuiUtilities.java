package seng202.team4;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import seng202.team4.controller.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    /**
     * Displays an error popup to the user.
     * If the error is critical then the App is closed.
     *
     * @param userMessage User friendly message to display.
     * @param detail More detailed error message to display.
     * @param isCritical Whether the error is critical.
     */
    public static void displayErrorMessage(String userMessage, String detail, boolean isCritical) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(userMessage);
        alert.setContentText(detail);
        alert.showAndWait();
        if (isCritical) {
            System.exit(1);
        }
    }

    /**
     * Displays an error popup to the user.
     * Shortcut method that does not require the 'isCritical' flag to be supplied.
     *
     * @param userMessage User friendly message to display.
     * @param detail More detailed error message to display.
     */
    public static void displayErrorMessage(String userMessage, String detail) {
        displayErrorMessage(userMessage, detail, false);
    }

    /** Applies a circular mask to the given image.
     * Used to make profile pictures circular.
     *
     * @param profilePic The profile picture to apply the mask to.
     * @return An Image that is the result of masking the profile picture.
     */
    public static Image maskProfileImage(Image profilePic) {

        BufferedImage image = SwingFXUtils.fromFXImage(profilePic, null);

        BufferedImage mask = null;
        try {
            mask = ImageIO.read(App.class.getResource("images/ProfileMask.png"));
        } catch (IOException e) {
            e.printStackTrace();
            mask = image;   // Will cause the mask to not be applied if it can not be found.
        }

        BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) resultImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.drawImage(mask, 0, 0, image.getWidth(), image.getHeight(),null);

        for (int y = 0; y < resultImage.getHeight(); ++y) {
            for (int x = 0; x < resultImage.getWidth(); ++x) {
                int argb = resultImage.getRGB(x, y);
                if ((argb & 0x00FFFFFF) == 0x00FF00FF)
                {
                    resultImage.setRGB(x, y, 0);
                }
            }
        }

        return SwingFXUtils.toFXImage(resultImage, null);
    }
}
