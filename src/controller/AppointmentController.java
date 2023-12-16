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

public class AppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentSearchTxt;

    @FXML
    private TableView<Appointments> appointmentTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptContactColumn;

    @FXML
    private TableColumn<Appointments, Integer> apptCustomerIdColumn;

    @FXML
    private TableColumn<Appointments, String> apptDescriptionColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> apptEndDateColumn;

    @FXML
    private TableColumn<Appointments, LocalTime> apptEndTimeColumn;

    @FXML
    private TableColumn<Appointments, Integer> apptIdColumn;

    @FXML
    private TableColumn<Appointments, String> apptLocationColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> apptStartDateColumn;

    @FXML
    private TableColumn<Appointments, LocalTime> apptStartTimeColumn;

    @FXML
    private TableColumn<Appointments, String> apptTitleColumn;

    @FXML
    private TableColumn<Appointments, String> apptTypeColumn;

    @FXML
    private TableColumn<Appointments, Integer> apptUserIdColumn;

    @FXML
    private Label localTimeZone;

    @FXML
    private ToggleGroup tgMainView;

    @FXML
    private RadioButton viewAllRadioBtn;

    @FXML
    private RadioButton viewCustomersRadioBtn;

    @FXML
    private RadioButton viewMonthRadioBtn;

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
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/updateAppointment-view.fxml"));
        stage.setScene(new Scene(scene));
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

        viewMonthRadioBtn.setOnAction(event -> filterAppointmentsByMonth());
        viewWeekRadioBtn.setOnAction(event -> filterAppointmentsByWeek());
        viewAllRadioBtn.setOnAction(event -> loadAppointmentData());
    }

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

    private void filterAppointmentsByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        updateAppointmentTableView(startOfMonth, endOfMonth);
    }

    private void filterAppointmentsByWeek() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate endOfWeek = now.with(ChronoField.DAY_OF_WEEK, 7);
        updateAppointmentTableView(startOfWeek, endOfWeek);
    }

    private void updateAppointmentTableView(LocalDate start, LocalDate end) {
        try {
            ObservableList<Appointments> filteredAppointments = AppointmentDAO.getAppointmentsByDateRange(start, end);
            appointmentTableView.setItems(filteredAppointments);
        }  catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
