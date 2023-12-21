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
        private TableColumn<Appointments, Integer> customerIdColumn;

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
                setUpTypesTable();
                setUpTypeChoiceBox();
                setUpCountryTable();
                setUpCountryChoiceBox();
                setUpContactChoiceBox();
                setUpContactTable();
        }

        //Sets up  "Contact Schedule" Tableview
        private void setUpContactTable() {
                apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
                startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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

        //Sets up "Total Customer by Type" Tableview
        private void setUpTypesTable() {
                customerIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn2.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn2.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn2.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn2.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn2.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }

        //Sets up "Total Customer by Country" Tableview

        private void setUpCountryTable() {
                customerIdColumn3.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn3.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn3.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn3.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn3.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn3.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }

        //Populates the choice box in the "Total Customer by Country" tab
        private void setUpCountryChoiceBox() {
                byCountryBox.setItems(FXCollections.observableArrayList("U.S", "UK", "Canada"));
                byCountryBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                try {
                                        loadByCountryData(newSelection);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        private void loadByCountryData(String countryName) throws SQLException {
                ObservableList<Integer> customerIds = AppointmentDAO.getCustomerIdsByCountry(countryName);
                ObservableList<Customer> customers = AppointmentDAO.getCustomersById(customerIds);

                for (Customer customer : customers) {
                        CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(customer.getDivisionId());
                        if (countryDivision != null) {
                                customer.setCountry(countryDivision.getCountry());
                                customer.setDivisionName(countryDivision.getDivision());
                        }
                }
                byCountryTableView.setItems(customers);
        }

        //Populates the choice box in the "Total Customer by Month" tab
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

        //Populates the choice box in the "Total Customers by Type" tab.
        private void setUpTypeChoiceBox() {
                try {
                        ObservableList<String> types = AppointmentDAO.getAllAppointmentTypes();
                        byTypeBox.setItems(types);
                        byTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                                if (newSelection != null) {
                                        try {
                                                loadByTypeData(newSelection);
                                        } catch (SQLException e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        //Loads customer data based on selected type
        private void loadByTypeData(String type) throws SQLException {
                ObservableList<Integer> customerIds = AppointmentDAO.getCustomerIdsByType(type);
                ObservableList<Customer> customers = AppointmentDAO.getCustomersById(customerIds);

                for (Customer customer : customers) {
                        CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(customer.getDivisionId());
                        if (countryDivision != null) {
                                customer.setCountry(countryDivision.getCountry());
                                customer.setDivisionName(countryDivision.getDivision());
                        }
                }
                byTypeTableView.setItems(customers);
        }

        private void setUpContactChoiceBox() {
                byContactBox.setItems(FXCollections.observableArrayList("1 - Anika Costa", "2 - Daniel Garcia", "3 - Li Lee"));
                byContactBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                try {
                                        loadByContactData(newSelection);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        private void loadByContactData(String contactName) throws SQLException {
                int contactId = AppointmentDAO.getContactNumber(contactName);
                ObservableList<Appointments> appointments = AppointmentDAO.getAppointmentsByContact(contactId);

                contactTableView.setItems(appointments);

        }




}


