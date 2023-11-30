package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}