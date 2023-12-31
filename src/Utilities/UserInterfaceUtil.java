package Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Utility class for handling UI alerts.
 *
 * @author William Deutsch
 */
public class UserInterfaceUtil {

    /**
     * Displays an alert dialog to the user.
     * THis method creates and shows an alert of the specified type, with a custom title and content.
     *
     *
     * @param content The message to be displayed in the alert.
     * @param title The title of the alert.
     * @param type The type of the alert.
     */
    public static void displayAlert(String content, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setGraphic(null); //Remove ugly icon
        alert.showAndWait();
    }

}


