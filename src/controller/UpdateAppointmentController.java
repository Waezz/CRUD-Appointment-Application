package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Utilities.TimeUtil;
import Utilities.UserInterfaceUtil;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    private Appointments selectedAppointment;

    @FXML
    private ChoiceBox<Integer> apptCustBox;

    @FXML
    private ChoiceBox<Integer> apptContBox;

    @FXML
    private TextField apptDescTxt;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<LocalTime> apptEndTime;

    @FXML
    private TextField apptIdTxt;

    @FXML
    private TextField apptLocTxt;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private ComboBox<LocalTime> apptStartTIme;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ChoiceBox<Integer> apptUserBox;

    @FXML
    void onActionApptSave(ActionEvent event) {
        try {
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();
            Integer userId = apptUserBox.getValue();
            Integer customerId = apptCustBox.getValue();
            Integer contactId = apptContBox.getValue();

            LocalDate startDate = apptStartDate.getValue();
            LocalTime startTime = apptStartTIme.getValue();
            LocalDate endDate = apptEndDate.getValue();
            LocalTime endTime = apptEndTime.getValue();

            // Combine date and time into LocalDateTime Objects
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || userId == null || customerId == null
                    || contactId == null) {
                UserInterfaceUtil.displayAlert("Please ensure all fields are filled.", "Missing Information", Alert.AlertType.WARNING);
                return; //Exit without adding an appointment
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
                Appointments updatedAppointment = new Appointments(selectedAppointment.getAppointmentId(), title, description, location, type, start, end, customerId, userId, contactId);
                AppointmentDAO.updateAppointment(updatedAppointment);
                UserInterfaceUtil.displayAlert("The Appointment has been successfully updated.", "Appointment Updated", Alert.AlertType.INFORMATION);
                returnToMain(event);
            } else {
                UserInterfaceUtil.displayAlert("Appointment times must be within business hours. Business hours are between 8:00am - 10:00pm Eastern Time.", "Invalid Time", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            UserInterfaceUtil.displayAlert("Error saving the changes.", "Please ensure all fields are correctly filled.", Alert.AlertType.ERROR);
        }
    }

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
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void initializeAppointmentData(Appointments appointments) {
        this.selectedAppointment = appointments;

        apptIdTxt.setText(String.valueOf(appointments.getAppointmentId()));
        apptTitleTxt.setText(appointments.getTitle());
        apptDescTxt.setText(appointments.getDescription());
        apptLocTxt.setText(appointments.getLocation());
        apptTypeTxt.setText(appointments.getType());

        //Populate the combo and choice boxes
        apptCustBox.setValue(appointments.getCustomerId());
        apptUserBox.setValue(appointments.getUserId());
        apptContBox.setValue(appointments.getContactId());

        //Populate the date pickers and time combo boxes
        apptStartDate.setValue(appointments.getStartDate());
        apptEndDate.setValue(appointments.getEndDate());
        apptStartTIme.setValue(appointments.getStartTime());
        apptEndTime.setValue(appointments.getEndTime());
    }

    private void populateTimeBoxes() {
        LocalTime startTime = LocalTime.of(0,0);
        LocalTime endTime = LocalTime.of(23,00);

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            apptStartTIme.getItems().add(startTime);
            apptEndTime.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }

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
