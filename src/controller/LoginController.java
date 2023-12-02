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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    private ResourceBundle bundle;

    @FXML
    private Button exitBtn;

    @FXML
    private Label userLocationLabel;

    @FXML
    private Label userLanguageLabel;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passWordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Label mainAppLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginBtnLabel;

    @FXML
    private Button exitBtnLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label zoneLabel;

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


        if (username.isEmpty() || password.isEmpty()) {
            showAlert("emptyCredentialsError", bundle, Alert.AlertType.ERROR);
        } else {

            //Proceed with validation only if both fields are filled
            boolean isValidUser = UserDAO.validateUser(username, password);

            if (isValidUser) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                //Validation failed, show invalid credentials alert
                showAlert("invalidCredentialsError", bundle, Alert.AlertType.ERROR);
            }
        }
    }
    /**
     * Displays alert dialog to user with the specific content, title, and type.
     * @param type The type of alert such as ERROR, WARNING, INFO.
     */
    public static void showAlert(String key, ResourceBundle bundle, Alert.AlertType type) {
        Alert alert = new Alert(type);
        String message = bundle.getString(key);
        alert.setHeaderText(null);
        alert.setGraphic(null); //Remove ugly icon
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String language = Locale.getDefault().getLanguage();

        if (language.equals("fr")) {
            this.bundle =ResourceBundle.getBundle("Lang_fr");
        } else {
            this.bundle = ResourceBundle.getBundle("Lang_en");
        }
        updateTexts(this.bundle);
        updateLocationLabel();

    }
    /**
     * Obtains the current time zone from the systems default and changes the label.
     */
    private void updateLocationLabel() {
        ZoneId zoneId = ZoneId.systemDefault();
        userLocationLabel.setText(zoneId.toString());
         }

    private void updateTexts(ResourceBundle bundle) {
        mainAppLabel.setText(bundle.getString("mainAppLabel"));
        userNameLabel.setText(bundle.getString("userNameLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        loginBtnLabel.setText(bundle.getString("loginBtnLabel"));
        exitBtnLabel.setText(bundle.getString("exitBtnLabel"));
        languageLabel.setText(bundle.getString("languageLabel"));
        zoneLabel.setText(bundle.getString("zoneLabel"));
        userLanguageLabel.setText(bundle.getString("userLanguageLabel"));
    }
}