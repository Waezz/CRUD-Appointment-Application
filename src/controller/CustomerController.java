package controller;

import DAO.CustomerDAO;
import Utilities.CountryDivision;
import Utilities.DivisionUtil;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private TableColumn<Customer, String> customerPostColumn;

    @FXML
    private TableColumn<Customer, String> customerStateColumn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private ToggleGroup tgMainView;

    @FXML
    private RadioButton viewAllRadioBtn;

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

    @FXML
    void onActionDeleteCust(ActionEvent event) {

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
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/updateCustomer-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

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

    @FXML
    void onActionViewCustomers(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpCustomerTable();
        loadCustomerData();
    }

    private void setUpCustomerTable() {
       customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
       customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
       customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
       customerPostColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
       customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
       customerStateColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }

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
