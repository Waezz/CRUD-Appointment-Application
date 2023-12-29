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

/**
 * Controller class for managing the reports functionality of the application.
 * This class handles the display and filtering of reports based on various criteria such as month, type, country and contact.
 *
 * LAMBDA EXPRESSIONS used on lines 401-411.
 *
 * @author William Deutsch
 */
public class ReportsController implements Initializable {

        Stage stage;
        Parent scene;

        /**
         * The address column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> addressColumn1;

        /**
         * The address column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, String> addressColumn2;

        /**
         * The address column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> addressColumn3;

        /**
         * The appointment_id column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, Integer> apptIdColumn;

        /**
         * The choice-box for the Contact Schedule tab.
         */
        @FXML
        private ChoiceBox<String> byContactBox;

        /**
         * The Contact tab to navigate to the Contact schedule table-view.
         */
        @FXML
        private Tab byContactTab;

        /**
         * The choice-box for the Customers by Country tab.
         */
        @FXML
        private ChoiceBox<String> byCountryBox;

        /**
         * The Customers by Country tab to navigate to the Customers by Country table-view.
         */
        @FXML
        private Tab byCountryTab;

        /**
         * The table-view for the customers by country reports.
         */
        @FXML
        private TableView<Customer> byCountryTableView;

        /**
         * The choice-box for the Customers by Month tab.
         */
        @FXML
        private ChoiceBox<String> byMonthBox;

        /**
         * The tab to navigate to the Customers by Month table-view.
         */
        @FXML
        private Tab byMonthTab;

        /**
         * The table-view for the Customer by month reports.
         */
        @FXML
        private TableView<Customer> byMonthTableView;

        /**
         * The choice-box for the Customers by Type tab.
         */
        @FXML
        private ChoiceBox<String> byTypeBox;

        /**
         * The tab to navigate to the Customers by Type table-view.
         */
        @FXML
        private Tab byTypeTab;

        /**
         * The table-view for the Customers by Type reports.
         */
        @FXML
        private TableView<Customer> byTypeTableView;

        /**
         * The table-view for the Contact Schedule reports.
         */
        @FXML
        private TableView<Appointments> contactTableView;

        /**
         * The country column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> countryColumn1;

        /**
         * The country column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, String> countryColumn2;

        /**
         * The country column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> countryColumn3;

        /**
         * The customer_id column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, Integer> customerIdColumn;

        /**
         * The customer_id column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, Integer> customerIdColumn1;

        /**
         * The customer_id column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, Integer> customerIdColumn2;

        /**
         * The customer_id column for the Customers by Country table-vew.
         */
        @FXML
        private TableColumn<Customer, Integer> customerIdColumn3;

        /**
         * The description column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, String> descriptionColumn;

        /**
         * The end date column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, LocalDate> endDateColumn;

        /**
         * The end time column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, LocalTime> endTimeColumn;

        /**
         * The name column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> nameColumn1;

        /**
         * The name column for the Customers by Type table-vew.
         */
        @FXML
        private TableColumn<Customer, String> nameColumn2;

        /**
         * The name column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> nameColumn3;

        /**
         * The phone column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> phoneColumn1;

        /**
         * The phone column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, String> phoneColumn2;

        /**
         * The phone column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> phoneColumn3;

        /**
         * The postal-code column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> postalCodeColumn1;

        /**
         * The postal-code column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, String> postalCodeColumn2;

        /**
         * The postal-code column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> postalCodeColumn3;

        /**
         * The start date column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, LocalDate> startDateColumn;

        /**
         * The start time column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, LocalTime> startTimeColumn;

        /**
         * The state column for the Customers by Month table-view.
         */
        @FXML
        private TableColumn<Customer, String> stateColumn1;

        /**
         *  The state column for the Customers by Type table-view.
         */
        @FXML
        private TableColumn<Customer, String> stateColumn2;

        /**
         * The state column for the Customers by Country table-view.
         */
        @FXML
        private TableColumn<Customer, String> stateColumn3;

        /**
         * The title column for the Contact Schedule table-view.
         */
        @FXML
        private TableColumn<Appointments, String> titleColumn;

        /**
         * The type column for the Contact Schedule table-view.
         */
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

        /**
         * Initializes the controller class.
         * This method is automatically called after the FXML file has been loaded.
         * It sets up the tables and choice boxes for different report types.
         *
         * @param url The location used to resolve the relative paths for the root object, or null if unknown.
         * @param resourceBundle The resources used to localize the root object, or null if none.
         */
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

        /**
         * Sets up the columns in the contact table with the correct data properties.
         */
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

        /**
         * Sets up the columns in the customer by month table with the correct data properties.
         */
        private void setUpMonthsTable() {
                customerIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn1.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn1.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn1.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn1.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }

        /**
         * Sets up the columns in the customer by type table with the correct data properties.
         */
        private void setUpTypesTable() {
                customerIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn2.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn2.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn2.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn2.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn2.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }


        /**
         * Sets up the columns in the customer by country table with the correct data properties.
         */
        private void setUpCountryTable() {
                customerIdColumn3.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn3.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneColumn3.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postalCodeColumn3.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                countryColumn3.setCellValueFactory(new PropertyValueFactory<>("country"));
                stateColumn3.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        }

        /**
         * LAMBDA JUSTIFICATION:
         *
         * This lambda expression is used for handling the selection changes in the Choice-box in an efficient manner.
         * It enhances readability by embedding the event handling logic directly within the setup method,
         * thereby avoiding the need for separate methods.
         */
        private void setUpCountryChoiceBox() {
                byCountryBox.setItems(FXCollections.observableArrayList("U.S", "UK", "Canada"));
                byCountryBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                                try {
                                        loadByCountryData(newValue.toString());
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        /**
         * Loads data for the "Total Customer by Country" report based on the selected country.
         *
         * @param countryName The name of the country selected for the report.
         * @throws SQLException If there is an issue accessing the database.
         */
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

        /**
         * Populates the choice box in the "Total Customer by Month" tab.
         */
        private void setUpMonthsChoiceBox() {
                byMonthBox.setItems(FXCollections.observableArrayList("January", "February", "March","April", "May", "June", "July",
                        "August", "September", "October", "November", "December"));
                byMonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                                try {
                                        loadByMonthData(newValue);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        /**
         * Loads data for the "Total Customer by Month" report based on the selected country.
         *
         * @param monthName The name of the month selected for the report.
         * @throws SQLException If there is an issue accessing the database.
         */
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

        /**
         * Populates the choice box in the "Total Customers by Type" tab.
         */
        private void setUpTypeChoiceBox() {
                try {
                        ObservableList<String> types = AppointmentDAO.getAllAppointmentTypes();
                        byTypeBox.setItems(types);
                        byTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue != null) {
                                        try {
                                                loadByTypeData(newValue);
                                        } catch (SQLException e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Loads data for the "Total Customer by Type" report based on the selected country.
         *
         * @param type The name of the type selected for the report.
         * @throws SQLException If there is an issue accessing the database.
         */
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

        /**
         * Populates the choice box in the "Total Customers by Contact" tab.
         */
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

        /**
         * Loads data for the "Contact Schedule" report based on the selected contact name.
         *
         * @param contactName The name of the contact selected for the report.
         * @throws SQLException If there is an issue accesing the database.
         */
        private void loadByContactData(String contactName) throws SQLException {
                int contactId = AppointmentDAO.getContactNumber(contactName);
                ObservableList<Appointments> appointments = AppointmentDAO.getAppointmentsByContact(contactId);

                contactTableView.setItems(appointments);

        }




}


