package controller;

import DAO.AppointmentDAO;
import Utilities.CountryDivision;
import Utilities.DivisionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

        Stage stage;
        Parent scene;

        @FXML
        private TableColumn<Customer, String> addressColumn1;

        @FXML
        private TableColumn<Customer, String> addressColumn2;

        @FXML
        private TableColumn<Customer, String> addressColumn3;

        @FXML
        private TableColumn<Appointments, Integer> apptIdColumn;

        @FXML
        private ChoiceBox<String> byContactBox;

        @FXML
        private Tab byContactTab;

        @FXML
        private ChoiceBox<String> byCountryBox;

        @FXML
        private Tab byCountryTab;

        @FXML
        private TableView<Customer> byCountryTableView;

        @FXML
        private ChoiceBox<String> byMonthBox;

        @FXML
        private Tab byMonthTab;

        @FXML
        private TableView<Customer> byMonthTableView;

        @FXML
        private ChoiceBox<String> byTypeBox;

        @FXML
        private Tab byTypeTab;

        @FXML
        private TableView<Customer> byTypeTableView;

        @FXML
        private TableView<Appointments> contactTableView;

        @FXML
        private TableColumn<Customer, String> countryColumn1;

        @FXML
        private TableColumn<Customer, String> countryColumn2;

        @FXML
        private TableColumn<Customer, String> countryColumn3;

        @FXML
        private TableColumn<Customer, Integer> customerIdColumn;

        @FXML
        private TableColumn<Customer, Integer> customerIdColumn1;

        @FXML
        private TableColumn<Customer, Integer> customerIdColumn2;

        @FXML
        private TableColumn<Customer, Integer> customerIdColumn3;

        @FXML
        private TableColumn<Appointments, String> descriptionColumn;

        @FXML
        private TableColumn<Appointments, LocalDate> endDateColumn;

        @FXML
        private TableColumn<Appointments, LocalTime> endTimeColumn;

        @FXML
        private TableColumn<Customer, String> nameColumn1;

        @FXML
        private TableColumn<Customer, String> nameColumn2;

        @FXML
        private TableColumn<Customer, String> nameColumn3;

        @FXML
        private TableColumn<Customer, String> phoneColumn1;

        @FXML
        private TableColumn<Customer, String> phoneColumn2;

        @FXML
        private TableColumn<Customer, String> phoneColumn3;

        @FXML
        private TableColumn<Customer, String> postalCodeColumn1;

        @FXML
        private TableColumn<Customer, String> postalCodeColumn2;

        @FXML
        private TableColumn<Customer, String> postalCodeColumn3;

        @FXML
        private TableColumn<Appointments, LocalDate> startDateColumn;

        @FXML
        private TableColumn<Appointments, LocalTime> startTimeColumn;

        @FXML
        private TableColumn<Customer, String> stateColumn1;

        @FXML
        private TableColumn<Customer, String> stateColumn2;

        @FXML
        private TableColumn<Customer, String> stateColumn3;

        @FXML
        private TableColumn<Appointments, String> titleColumn;

        @FXML
        private TableColumn<Appointments, String> typeColumn;

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

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                setUpMonthsTable();
                setUpMonthsChoiceBox();
        }

        //Sets up "Total Customer by Month" Tableview
        private void setUpMonthsTable() {
                customerIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn1.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn1.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn1.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn1.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }

        //Populates the choice box in the "Total Customer by Moth" tab
        private void setUpMonthsChoiceBox() {
                byMonthBox.setItems(FXCollections.observableArrayList("January", "February", "March","April", "May", "June", "July",
                        "August", "September", "October", "November", "December"));
                byMonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                try {
                                        loadByMonthData(newSelection);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        //Loads customers based on the selected month
        private void loadByMonthData(String monthName) throws SQLException {
                int monthNumber = AppointmentDAO.getMonthNumber(monthName);
                ObservableList<Integer> customerIds = AppointmentDAO.getCustomerIdsByMonth(monthNumber);
                ObservableList<Customer> customers = AppointmentDAO.getCustomersById(customerIds);

                for (Customer customer : customers) {
                        CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(customer.getDivisionId());
                        if (countryDivision != null) {
                                customer.setCountry(countryDivision.getCountry());
                                customer.setDivisionName(countryDivision.getDivision());
                        }
                }
                byMonthTableView.setItems(customers);
        }


}


