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

public class UpdateCustomerController {

    Stage stage;
    Parent scene;

    private Customer selectedCustomer;


    @FXML
    private TextField custAddressTxt;

    @FXML
    private ComboBox<String> custCountryBox;

    @FXML
    private TextField custIdTxt;

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhoneTxt;

    @FXML
    private TextField custPostalTxt;

    @FXML
    private ComboBox<String> custStateBox;

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

    public void initializeCustomerData(Customer customer) {
        this.selectedCustomer = customer;

        custIdTxt.setText(String.valueOf(customer.getId()));
        custNameTxt.setText(customer.getName());
        custAddressTxt.setText(customer.getAddress());
        custPhoneTxt.setText(customer.getPhone());
        custPostalTxt.setText(customer.getPostalCode());

        populateCountryComboBox();
    }

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

    private void setComboBoxValues() {
        CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(selectedCustomer.getDivisionId());
        if (countryDivision != null) {
            String countryName = countryDivision.getCountry();
            String divisionName = countryDivision.getDivision();

            custCountryBox.setValue(countryName);
            updateStateComboBox(countryName, divisionName);
        }

    }

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


