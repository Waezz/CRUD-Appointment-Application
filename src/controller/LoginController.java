package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private Button exitBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passWordTxt;

    @FXML
    private TextField userNameTxt;

    /**
     * Exits the application when triggered.
     * @param event The event that triggered this method.
     */
    @FXML
    void exitBtnPressed(ActionEvent event) {

        System.exit(0);

    }

    @FXML
    void loginBtnPressed(ActionEvent event) throws IOException {
        String username = userNameTxt.getText();
        String password = passWordTxt.getText();

        boolean isValidUser = UserDAO.validateUser(username, password);

        if (isValidUser) {
            stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            showAlert("Invalid User Name and Password", "Error", Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays alert dialog to user with the specific content, title, and type.
     * @param content The message to display.
     * @param title The title of the alert dialog window.
     * @param type The type of alert such as ERROR, WARNING, INFO.
     */
    public static void showAlert(String content, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setGraphic(null); //Remove ugly icon
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}