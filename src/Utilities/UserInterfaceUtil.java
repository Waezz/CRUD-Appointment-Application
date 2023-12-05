package Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class UserInterfaceUtil {



    public static void displayAlert(String content, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setGraphic(null); //Remove ugly icon
        alert.showAndWait();
    }

}


