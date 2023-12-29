package controller;

import DAO.AppointmentDAO;
import Utilities.UserInterfaceUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

/**
 * This controller handles the logic for managing and displaying appointments within the application.
 * It interacts with the appointments fxml view to present a table of appointments,
 * enable navigation to other screens, and provide actions for adding, modifying, deleting, and filtering appointments.
 *
 * LAMBDA EXPRESSIONS used on lines 321-324.
 *
 * @author William Deutsch
 *
 */
public class AppointmentController implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * The table-view for the appointments.
     */
    @FXML
    private TableView<Appointments> appointmentTableView;

    /**
     * The contact column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptContactColumn;

    /**
     * The customer_id column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptCustomerIdColumn;

    /**
     * The description column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, String> apptDescriptionColumn;

    /**
     * The end date column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> apptEndDateColumn;

    /**
     * The end time column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> apptEndTimeColumn;

    /**
     * The appointment_id column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIdColumn;

    /**
     * The location column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, String> apptLocationColumn;

    /**
     * The start date column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> apptStartDateColumn;

    /**
     * The start time column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> apptStartTimeColumn;

    /**
     * The title column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, String> apptTitleColumn;

    /**
     * The type column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, String> apptTypeColumn;

    /**
     * The user_id column for the appointment table-view.
     */
    @FXML
    private TableColumn<Appointments, Integer> apptUserIdColumn;

    /**
     * The label to display the local time zone.
     */
    @FXML
    private Label localTimeZone;

    /**
     * Toggle group for the radio buttons
     */
    @FXML
    private ToggleGroup tgMainView;

    /**
     * Radio button to display all appointments.
     */
    @FXML
    private RadioButton viewAllRadioBtn;

    /**
     * Radio button to display customers.
     */
    @FXML
    private RadioButton viewCustomersRadioBtn;

    /**
     * Radio button to filter appointments by month.
     */
    @FXML
    private RadioButton viewMonthRadioBtn;

    /**
     * Radio button to filter appointments by week.
     */
    @FXML
    private RadioButton viewWeekRadioBtn;

    /**
     * Navigates to the Add Appointment screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Add Appointment screen cannot be loaded.
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Deletes the selected appointment after confirming with the user.
     * @param event the event that triggered this method.
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) {
        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            UserInterfaceUtil.displayAlert("No appointment selected.", "Please select an appointment to delete.", Alert.AlertType.ERROR);
            return;
        }

        // Confirm the appt ID and type before deletion
        int appointmentId = selectedAppointment.getAppointmentId();
        String appointmentType = selectedAppointment.getType();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this appointment? \n\nAppointment ID = " + appointmentId + "\nAppointment Type = " + appointmentType,
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setGraphic(null); // get rid of ugly logo
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.YES) {
            try {
                AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentId());
                appointmentTableView.getItems().remove(selectedAppointment);
                UserInterfaceUtil.displayAlert("Appointment deleted successfully.", "Deleted", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                UserInterfaceUtil.displayAlert("Error occurred while deleting appointment!", "Error", Alert.AlertType.ERROR);
            }
        }

    }

    /**
     * Return to the login page.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the login page cannot be loaded.
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Navigates to the Update Appointment screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Update Appointment screen cannot be loaded.
     */
    @FXML
    void onActionModifyAppt(ActionEvent event) throws IOException {
        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            UserInterfaceUtil.displayAlert("No appointment selected", "Please select an appointment to update.", Alert.AlertType.ERROR);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updateAppointment-view.fxml"));
        Parent updateAppointmentView = loader.load();

        UpdateAppointmentController updateAppointmentController = loader.getController();
        updateAppointmentController.initializeAppointmentData(selectedAppointment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(updateAppointmentView));
        stage.show();

    }

    /**
     * Navigates to the Reports screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Reports screen cannot be loaded.
     */
    @FXML
    void onActionReport(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reports-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionViewAll(ActionEvent event) {

    }

    /**
     * Navigates to the Customers screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Customers page cannot load.
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionViewMonth(ActionEvent event) {

    }


    @FXML
    void onActionViewWeek(ActionEvent event) {

    }

    /**
     * Initializes the controller class. Sets up table columns, loads appointment data,
     * sets event handlers for radio buttons, and displays the current time zone.
     * @param url The location used to resolve relative paths for the root object, or null in unknown.
     * @param resourceBundle The resources used to localize the root object, or null if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        loadAppointmentData();
        updateLocationLabel();


        /**
         * LAMBDA JUSTIFICATION:
         * Lambda expressions are used here to create concise and readable event handlers directly
         * within the initialize method. This approach streamlines the code and avoids the need for separate methods for
         * each handler, improving code organization and maintainability.
         */
        viewMonthRadioBtn.setOnAction(event -> updateAppointmentTableView(LocalDate.now().withDayOfMonth(1), LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())));
        viewWeekRadioBtn.setOnAction(event -> updateAppointmentTableView(LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1), LocalDate.now().with(ChronoField.DAY_OF_WEEK, 7)));
        viewAllRadioBtn.setOnAction(event -> updateAppointmentTableView(LocalDate.MIN, LocalDate.MAX));
    }

    /**
     * Retrieves and displays all appointments from the database.
     */
    private void loadAppointmentData() {
        try {
            ObservableList<Appointments> appointmentData = AppointmentDAO.getAllAppointments();
            appointmentTableView.setItems(appointmentData);
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains the current time zone from the systems default and changes the label.
     */
    private void updateLocationLabel() {
        ZoneId zoneId = ZoneId.systemDefault();
        localTimeZone.setText(zoneId.toString());
    }

    /**
     * Filters appointments to display only those within the current month.
     * Made redundant by  Lambda expressions on lines 240-242.
     */
    private void filterAppointmentsByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        updateAppointmentTableView(startOfMonth, endOfMonth);
    }

    /**
     * Filters appointments to display only those within the specified date range.
     * Made redundant by Lambda expressions on lines 240-242.
     */
    private void filterAppointmentsByWeek() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate endOfWeek = now.with(ChronoField.DAY_OF_WEEK, 7);
        updateAppointmentTableView(startOfWeek, endOfWeek);
    }

    /**
     * Updates the table view within the specified date range.
     * @param start The start of the date range.
     * @param end The end of the date range.
     */
    private void updateAppointmentTableView(LocalDate start, LocalDate end) {
        try {
            ObservableList<Appointments> filteredAppointments = AppointmentDAO.getAppointmentsByDateRange(start, end);
            appointmentTableView.setItems(filteredAppointments);
        }  catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
