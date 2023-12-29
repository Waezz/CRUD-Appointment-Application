package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import Utilities.CountryDivision;
import Utilities.DivisionUtil;
import Utilities.UserInterfaceUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for updating customer information in the application.
 * This class handles the functionality of modifying an existing customer's details.
 *
 * @author William Deutsch
 */
public class UpdateCustomerController {

    Stage stage;
    Parent scene;

    private Customer selectedCustomer;


    /**
     * The text field for the customer address.
     */
    @FXML
    private TextField custAddressTxt;

    /**
     * The combo-box for the customer country.
     */
    @FXML
    private ComboBox<String> custCountryBox;

    /**
     * The text field for the customer_id.
     */
    @FXML
    private TextField custIdTxt;

    /**
     * The text field for the customer name.
     */
    @FXML
    private TextField custNameTxt;

    /**
     * The text field for the customer phone.
     */
    @FXML
    private TextField custPhoneTxt;

    /**
     * The text field for the customer postal-code.
     */
    @FXML
    private TextField custPostalTxt;

    /**
     * The combo-box for the customer state/province.
     */
    @FXML
    private ComboBox<String> custStateBox;

    /**
     * Handles the action of saving the updated customer information.
     * Validates the input fields and updates the customer in the database.
     *
     * @param event The event that triggered this method.
     */
    @FXML
    void onActionCustSave(ActionEvent event) {

        try {

            String name = custNameTxt.getText();
            String address = custAddressTxt.getText();
            String postalCode = custPostalTxt.getText();
            String phone = custPhoneTxt.getText();
            String divisionName = custStateBox.getValue();

            //Validation: Check if any fields are empty
            if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || divisionName == null) {
                UserInterfaceUtil.displayAlert("Please ensure all fields are filled.", "Missing Information", Alert.AlertType.WARNING);
                return; //Exit without adding a customer
            }

            // Convert division name to division ID
            int divisionId = DivisionUtil.getDivisionIdByDivisionName(divisionName);

            Customer updatedCustomer = new Customer(selectedCustomer.getId(), name, address, postalCode, phone, divisionId);
            CustomerDAO.updateCustomer(updatedCustomer);

            UserInterfaceUtil.displayAlert("Customer Updated", "The customer's information has been successfully updated.", Alert.AlertType.INFORMATION);
            returnToMain(event);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            UserInterfaceUtil.displayAlert("Update Error", "There was a problem updating the customer.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates back to the Appointment screen.
     *
     * @param event The event that triggered this method.
     * @throws IOException IF the FXML file for the appointments screen cannot be loaded.
     */
    private void returnToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Navigates back to the Customers screen when triggered.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the Customers screen cannot be loaded.
     */
    @FXML
    void onActionMenuReturn(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customer-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the customer data in the form fields.
     *
     * @param customer The customer data to initialize the form with.
     */
    public void initializeCustomerData(Customer customer) {
        this.selectedCustomer = customer;

        custIdTxt.setText(String.valueOf(customer.getId()));
        custNameTxt.setText(customer.getName());
        custAddressTxt.setText(customer.getAddress());
        custPhoneTxt.setText(customer.getPhone());
        custPostalTxt.setText(customer.getPostalCode());

        populateCountryComboBox();
    }

    /**
     * Populates the country selection combo box with available countries.
     * Sets up a listener for country selection and updates the state combo box accordingly.
     *
     */
    private void populateCountryComboBox(){
        try {
            ObservableList<String> countryList = CountryDAO.getAllCountries();
            custCountryBox.setItems(countryList);

            //Add listener to country combo box
            custCountryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSlection) -> {
                if (newSlection != null) {
                    updateStateComboBox(newSlection, null);
                }
            });

            setComboBoxValues();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the values of the country and state combo boxes based on the customer's selected data.
     */
    private void setComboBoxValues() {
        CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(selectedCustomer.getDivisionId());
        if (countryDivision != null) {
            String countryName = countryDivision.getCountry();
            String divisionName = countryDivision.getDivision();

            custCountryBox.setValue(countryName);
            updateStateComboBox(countryName, divisionName);
        }

    }

    /**
     * Updates the state combo box based on the selected country.
     *
     * @param country The selected country name.
     * @param divisionName The division name to set as the selected value in the state combo box.
     */
    private void updateStateComboBox(String country, String divisionName) {
        try {
            ObservableList<String> divisionList = DivisionUtil.getDivisionsByCountry(country);
            custStateBox.setItems(divisionList);

            if (divisionName != null) {
                custStateBox.setValue(divisionName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


