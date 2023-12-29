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

/**
 * Controller class for managing the login functionality of the application.
 * This class handles user authentication, login and exit actions, and alerts for upcoming appointments.
 *
 * @author William Deutsch
 */
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    private ResourceBundle bundle;

    /**
     * The exit button to terminate the application.
     */
    @FXML
    private Button exitBtn;

    /**
     * The user location label.
     */
    @FXML
    private Label userLocationLabel;

    /**
     * The user language label.
     */
    @FXML
    private Label userLanguageLabel;

    /**
     * The button to login.
     */
    @FXML
    private Button loginBtn;

    /**
     * The text field for user's password.
     */
    @FXML
    private TextField passWordTxt;

    /**
     * The text field for user's name.
     */
    @FXML
    private TextField userNameTxt;

    /**
     * The label for the main application.
     */
    @FXML
    private Label mainAppLabel;

    /**
     * The label for the username.
     */
    @FXML
    private Label userNameLabel;

    /**
     * The label for the password.
     */
    @FXML
    private Label passwordLabel;

    /**
     * The label for the log in button.
     */
    @FXML
    private Button loginBtnLabel;

    /**
     * The label for the exit button.
     */
    @FXML
    private Button exitBtnLabel;

    /**
     * The label for the current language.
     */
    @FXML
    private Label languageLabel;

    /**
     * The label for the user's current time zone.
     */
    @FXML
    private Label zoneLabel;

    /**
     * Terminates the application when triggered.
     * @param event The event that triggered this method.
     */
    @FXML
    void exitBtnPressed(ActionEvent event) {

        System.exit(0);

    }

    /**
     * Handles the login process when the login button is pressed.
     * It checks for empty credentials , validates the user, and logs in if valid.
     * Also logs the login attempt and checks for upcoming appointments.
     *
     * @param event The event that triggered this method.
     * @throws IOException If there is an issue loading the next view.
     * @throws SQLException If there is an issue accessing the database.
     */
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

    /**
     * Checks for upcoming appointments within the next fifteen minutes.
     * Alerts the user if an appointment is upcoming or if there are no upcoming appointments.
     *
     * @throws SQLException If there is an issue accessing the database.
     */
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

    /**
     * Logs the result of a login attempt to a file.
     * Records the username, timestamp, and whether the login was successful or not.
     *
     * @param username The username of the person attempting to log in.
     * @param isSuccess A boolean representing whether the login attempt was successful.
     */
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
     * Displays an alert dialog to the user.
     * The content and type are based on the provided key and type.
     * @param key The key used to retrive the alert message from the resource bundle.
     * @param bundle The resource bundle containing the localized messages.
     * @param type The type of alert (e.g, ERROR, INFO, WARNING).
     */
    public static void showAlert(String key, ResourceBundle bundle, Alert.AlertType type) {
        Alert alert = new Alert(type);
        String message = bundle.getString(key);
        alert.setHeaderText(null);
        alert.setGraphic(null); //Remove ugly icon
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if none.
     */
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
     * Updates the location label with the current system's default time zone.
     */
    private void updateLocationLabel() {
        ZoneId zoneId = ZoneId.systemDefault();
        userLocationLabel.setText(zoneId.toString());
         }

    /**
     * Updates the UI based on the provided resource bundle.
     * Sets up texts for labels and buttons according to the current language.
     *
     * @param bundle The resource bundle containing the texts.
     */
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