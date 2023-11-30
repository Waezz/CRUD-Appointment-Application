package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentSearchTxt;

    @FXML
    private TableView<?> appointmentTableView;

    @FXML
    private TableColumn<?, ?> apptContactColumn;

    @FXML
    private TableColumn<?, ?> apptCustomerIdColumn;

    @FXML
    private TableColumn<?, ?> apptDescriptionColumn;

    @FXML
    private TableColumn<?, ?> apptEndDateColumn;

    @FXML
    private TableColumn<?, ?> apptEndTimeColumn;

    @FXML
    private TableColumn<?, ?> apptIdColumn;

    @FXML
    private TableColumn<?, ?> apptLocationColumn;

    @FXML
    private TableColumn<?, ?> apptStartDateColumn;

    @FXML
    private TableColumn<?, ?> apptStartTimeColumn;

    @FXML
    private TableColumn<?, ?> apptTitleColumn;

    @FXML
    private TableColumn<?, ?> apptTypeColumn;

    @FXML
    private TableColumn<?, ?> apptUserIdColumn;

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

}
