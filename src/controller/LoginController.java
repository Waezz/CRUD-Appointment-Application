package controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Utilities.UserInterfaceUtil;
import javafx.collections.ObservableList;
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
import model.Appointments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    void loginBtnPressed(ActionEvent event) throws IOException, SQLException {
        String username = userNameTxt.getText();
        String password = passWordTxt.getText();


        if (username.isEmpty() || password.isEmpty()) {
            showAlert("emptyCredentialsError", bundle, Alert.AlertType.ERROR);
            logLogin(username, false); //Log failed attempt due to empty credentials
        } else {

            //Proceed with validation only if both fields are filled
            boolean isValidUser = UserDAO.validateUser(username, password);

            if (isValidUser) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                checkForUpcoming();
                logLogin(username, true); //Log successful login

            } else {
                //Validation failed, show invalid credentials alert
                showAlert("invalidCredentialsError", bundle, Alert.AlertType.ERROR);
                logLogin(username, false); //Log failed attempt due to invalid credentials
            }
        }
    }

    public void checkForUpcoming() throws SQLException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime currentTimePlus15 = currentTime.plusMinutes(15);

        boolean appointmentWithin15 = false;
        int upcomingAppointmentId= -1;
        LocalDateTime upcomingAppointmentTime = null;

        for (Appointments appointment : AppointmentDAO.getAllAppointments()) {
            LocalDateTime appointmentStartTime = appointment.getStart();
            if((appointmentStartTime.isAfter(currentTime) || appointmentStartTime.isEqual(currentTime)) && appointmentStartTime.isBefore(currentTimePlus15)) {
                upcomingAppointmentId = appointment.getAppointmentId();
                upcomingAppointmentTime = appointmentStartTime;
                appointmentWithin15 = true;
                break;
            }
        }


        if (appointmentWithin15) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'At' HH:mm");
            String formattedTime = upcomingAppointmentTime.format(formatter);
            UserInterfaceUtil.displayAlert("You have an upcoming appointment within 15 minutes: Appointment ID: " + upcomingAppointmentId +
                    "\nStart Time: " + formattedTime, "Upcoming Appointments", Alert.AlertType.INFORMATION);
        } else {
            UserInterfaceUtil.displayAlert("You have no upcoming appointments within the next 15 minutes.", "Upcoming Appointments", Alert.AlertType.INFORMATION);
        }
    }

    //Method to write to the File. Uses filewriter and bufferedwriter to append information to login_activity
    public void logLogin(String username, boolean isSuccess) {
        try(FileWriter fw = new FileWriter("login_activiy.txt", true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss"));
            String logEntry = String.format("User: %s, Timestamp: %s, Success: %s\n", username, timeStamp, isSuccess ? "Successful" : "Failed");
            bw.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
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