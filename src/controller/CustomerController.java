package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Utilities.CountryDivision;
import Utilities.DivisionUtil;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static DAO.CustomerDAO.deleteCustomer;
/**
 * This controller handles the logic for managing and displaying customers within the application.
 * It interacts with the customer fxml view to present a table of customers,
 * enable navigation to other screens, and provide actions for adding, modifying, and deleting customers.
 *
 * @author William Deutsch
 *
 */
public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * The address column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    /**
     * The country column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    /**
     * The customer_id column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    /**
     * The name column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    /**
     * The phone column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    /**
     * The postal-code column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerPostColumn;

    /**
     * The state column in the customer table-view.
     */
    @FXML
    private TableColumn<Customer, String> customerStateColumn;

    /**
     * The customer table-view.
     */
    @FXML
    private TableView<Customer> customerTableView;

    /**
     * The toggle group for the radio buttons.
     */
    @FXML
    private ToggleGroup tgMainView;

    /**
     * The radio button to view all appointments.
     */
    @FXML
    private RadioButton viewAllRadioBtn;

    /**
     * The radio button to view all customers.
     */
    @FXML
    private RadioButton viewCustomersRadioBtn;

    /**
     * Navigates to the Add Customer screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Add Customer page cannot be loaded.
     */
    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action of deleting a customer.
     * This method is triggered when the 'Delete Customer' button is clicked.
     * It deletes the selected customer after confirmation and validation checks.
     * @param event The event that triggered this method.
     */
    @FXML
    void onActionDeleteCust(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            UserInterfaceUtil.displayAlert("No customer selected.", "Please select a customer to delete.", Alert.AlertType.ERROR);
            return;
        }
        checkAndDeleteCustomer(selectedCustomer);

        //Refresh the Tableview
        loadCustomerData();
    }

    /**
     * Checks if the selected customer has any appointments before deletion.
     * If appointments exist, it prevents deletion and alerts the user
     * Otherwise, it proceeds to ask for confirmation for deletion.
     * @param selectedCustomer The customer selected for deletion.
     */
    private void checkAndDeleteCustomer(Customer selectedCustomer) {
        try {
            ObservableList<Appointments> appointments = AppointmentDAO.getAppointmentsByCustomer(selectedCustomer.getId());

            if (!appointments.isEmpty()) {
                UserInterfaceUtil.displayAlert("You cannot delete a customer with existing appointments. " +
                                               "Please cancel all associated appointments first.", "Delete Customer", Alert.AlertType.INFORMATION);
                return;
            }
            confirmAndDeleteCustomer(selectedCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks for confirmation and deletes the customer if confirmed.
     * Displays a confirmation dialog before deleting the customer.
     * @param customer The customer to be deleted.
     * @throws SQLException If there is an issue executing the operation in the database.
     */
    private void confirmAndDeleteCustomer(Customer customer) throws SQLException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this customer: " + customer.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.YES) {
            deleteCustomer(customer.getId());
        }
    }

    /**
     * Handles the action of logging out.
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

    /**
     * Navigates to the Update Customer screen when triggered.
     * @param event The event that triggers this method.
     * @throws IOException If the FXML file for the Update Customer page cannot be loaded.
     */
    @FXML
    void onActionUpdateCust(ActionEvent event) throws IOException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            UserInterfaceUtil.displayAlert("No Customer Selected", "Please select a customer to update.", Alert.AlertType.ERROR);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer-view.fxml"));
            Parent updateCustomerParent = loader.load();

            UpdateCustomerController controller = loader.getController();
            controller.initializeCustomerData(selectedCustomer);

            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(updateCustomerParent));
            stage.show();

        }

    }

    /**
     * Navigates back to the Appointments Screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Appointment screen cannot be loaded.
     */
    @FXML
    void onActionViewAll(ActionEvent event) throws IOException {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Handles the action of viewing customers.
     * @param event The event that triggered this method.
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) {

    }

    /**
     * Initializes the controller class. It sets up the customer table and loads customer data.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpCustomerTable();
        loadCustomerData();
    }

    /**
     * Sets up the columns in the customer table with the correct data properties.
     */
    private void setUpCustomerTable() {
       customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
       customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
       customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
       customerPostColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
       customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
       customerStateColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }

    /**
     * Loads customer data from the database and populates the table view.
     * It fetches customer data, resolves their country and division, and updates the tableview.
     */
    private void loadCustomerData() {
        try {
            ObservableList<Customer> customerData = CustomerDAO.getAllCustomers();
            for (Customer customer : customerData) {
                CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(customer.getDivisionId());
                if (countryDivision != null) {
                    customer.setCountry(countryDivision.getCountry());
                    customer.setDivisionName(countryDivision.getDivision());
                }
            }
            customerTableView.setItems(customerData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
