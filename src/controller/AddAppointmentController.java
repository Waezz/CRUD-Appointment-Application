package controller;

import DAO.CustomerDAO;
import DAO.AppointmentDAO;
import Utilities.UserInterfaceUtil;
import Utilities.TimeUtil;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This controller handles the logic for adding new appointments to the application.
 * It interacts with the addAppointment fxml view and manages user input, data validation,
 * and communication with the database.
 *
 * @author William Deutsch
 */
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * The choice-box for the current customers.
     */
    @FXML
    private ChoiceBox<Integer> apptCustBox;

    /**
     * The choice-box for the contacts.
     */
    @FXML
    private ChoiceBox<Integer> apptContBox;

    /**
     * The text field for the appointment description.
     */
    @FXML
    private TextField apptDescTxt;

    /**
     * The date picker for the appointment end date.
     */
    @FXML
    private DatePicker apptEndDate;

    /**
     * The combo-box for appointment end times.
     */
    @FXML
    private ComboBox<LocalTime> apptEndTime;

    /**
     * The text field for appointment id's (disabled).
     */
    @FXML
    private TextField apptIdTxt;

    /**
     * The text field for the appointment location.
     */
    @FXML
    private TextField apptLocTxt;

    /**
     * The date picker for the appointment start date.
     */
    @FXML
    private DatePicker apptStartDate;

    /**
     * The combo-box for the appointment start time.
     */
    @FXML
    private ComboBox<LocalTime> apptStartTIme;

    /**
     * The text field for the appointment title.
     */
    @FXML
    private TextField apptTitleTxt;

    /**
     * The text field for the appointment type.
     */
    @FXML
    private TextField apptTypeTxt;

    /**
     * The choice-box for the users.
     */
    @FXML
    private ChoiceBox<Integer> apptUserBox;

    /**
     * This Method takes necessary parameters and return a boolean indicating whether an overlap occurs.
     * @param customerId
     * @param start
     * @param end
     * @return False if no overlap is found.
     */
    private boolean isAppointmentOverlapping(int customerId, LocalDateTime start, LocalDateTime end) {
        try {
            ObservableList<Appointments> existingAppointments = AppointmentDAO.getAppointmentsByCustomer(customerId);
            for (Appointments existingAppointment : existingAppointments) {
                if (start.isBefore(existingAppointment.getEnd()) && end.isAfter(existingAppointment.getStart())) {
                    return true; //Overlap found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; //No overlap found
    }


    /**
     * Validates and saves the appointment information entered by the user.
     * Performs checks for:
     * -Empty fields
     * -Overlapping appointments
     * -Invalid start and end time
     * -Appointments outside business hours
     * If all validations pass, the appointment is saved to the database, and the user is
     * returned to the main appointment screen.
     * @param event The event that triggered this method.
     */
    @FXML
    void onActionApptSave(ActionEvent event) {

        String title = apptTitleTxt.getText();
        String description = apptDescTxt.getText();
        String location = apptLocTxt.getText();
        String type = apptTypeTxt.getText();
        Integer userId = apptUserBox.getValue();
        Integer customerId = apptCustBox.getValue();
        Integer contactId = apptContBox.getValue();

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || userId == null || customerId == null
            || contactId == null) {
            UserInterfaceUtil.displayAlert("Please ensure all fields are filled.", "Missing Information", Alert.AlertType.WARNING );
            return; //Exit without adding an appointment
        }

        try {
            LocalDate startDate = apptStartDate.getValue();
            LocalTime startTime = apptStartTIme.getValue();
            LocalDate endDate = apptEndDate.getValue();
            LocalTime endTime = apptEndTime.getValue();

            // Combine date and time into LocalDateTime Objects
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            //Overlap check
            if (isAppointmentOverlapping(customerId, start, end)) {
                UserInterfaceUtil.displayAlert("Appointment times overlap with another appointment.", "Overlap Error", Alert.AlertType.ERROR);
                return;
            }

            // Check if the start time is before the end time
            if (start.isAfter(end)) {
                UserInterfaceUtil.displayAlert("Your Appointment start time must be before the end time.", "Invalid Time", Alert.AlertType.ERROR);
                return; // Exit without adding an appointment
            }

            // Check if the appointment is set in the past
            if (start.isBefore(LocalDateTime.now())) {
                UserInterfaceUtil.displayAlert("You cannot schedule an Appointment in the past.", "Invalid Date", Alert.AlertType.ERROR);
                return; // Exit
            }

            // Validate both start and end times
            if (TimeUtil.isValidBuisnessHour(start) && TimeUtil.isValidBuisnessHour(end)) {
                Appointments newAppointment = new Appointments(0, title, description, location, type, start, end, customerId, userId, contactId);
                AppointmentDAO.addAppointment(newAppointment);
                UserInterfaceUtil.displayAlert("The new Appointment has been successfully added.", "Appointment Added", Alert.AlertType.INFORMATION);
                returnToMain(event);
            } else {
                UserInterfaceUtil.displayAlert("Appointment times must be within business hours. Business hours are between 8:00am - 10:00pm Eastern Time.", "Invalid Time", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            UserInterfaceUtil.displayAlert("Error saving the appointment.", "Please ensure all fields are correctly filled.", Alert.AlertType.ERROR);
        }

    }

    /**
     * Navigates back to the main appointment screen.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the main appointment screen cannot be loaded.
     */
    private void returnToMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Navigates back to the Appointment screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Appointment screen cannot be loaded.
     */
    @FXML
    void onActionMenuReturn(ActionEvent event) throws IOException {
        returnToMain(event);

    }

    /**
     * Populates the appointment start and end time combo boxes with available time options.
     * Times are displayed in 30 minute increments, starting from 00:00.
     */
    private void populateTimeBoxes() {
        LocalTime startTime = LocalTime.of(0,0);
        LocalTime endTime = LocalTime.of(23,00);

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            apptStartTIme.getItems().add(startTime);
            apptEndTime.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }

    /**
     * Sets up listeners for the appointment start and end date pickers to ensure consistency.
     * The end date is automatically set to match the start date, and the user is prevented
     * from selecting an end date that occurs on a different day than the start date.
     */
    private void setUpDatePickerListener() {
        apptStartDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            apptEndDate.setValue(newValue); //Set End Date to match Start Date
        });

        apptEndDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(apptStartDate.getValue())) {
                UserInterfaceUtil.displayAlert("Your Appointment must end on the day that it starts",
                                                        "Invalid End Date", Alert.AlertType.ERROR);
                apptEndDate.setValue(apptStartDate.getValue()); //Reset to start date
            }
        });
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * @param url the location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle the resources used to localize the root object,  or null if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTimeBoxes();
        setUpDatePickerListener();

        // Populate choice boxes
        apptContBox.setItems(FXCollections.observableArrayList(1, 2, 3));
        apptUserBox.setItems(FXCollections.observableArrayList(1, 2));

        // Populate Customer ChoiceBox with current customers
        try {
            ObservableList<Integer> customerIds = CustomerDAO.getAllCustomerIDs();
            apptCustBox.setItems(customerIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
