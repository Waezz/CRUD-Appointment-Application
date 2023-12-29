package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Utilities.DivisionUtil;
import Utilities.UserInterfaceUtil;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This controller handles the logic for adding new customers to the application.
 * It interacts with the addCustomer fxml view and manages user input, data validation,
 * and communication with the database.
 *
 * @author William Deutsch
 */
public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * The text filed for the customer address.
     */
    @FXML
    private TextField custAddressTxt;

    /**
     * The combo-box for the countries.
     */
    @FXML
    private ComboBox<String> custCountryBox;

    /**
     * The text field for the customer id (disabled).
     */
    @FXML
    private TextField custIdTxt;

    /**
     * The text field for the customer name.
     */
    @FXML
    private TextField custNameTxt;

    /**
     * The text field for the customer phone #.
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
     * Validates and saves the customer information entered by the user.
     * Performs checks for empty fields and attempts to create a new customer record in the database.
     * Displays success or error messages based on the outcome.
     * @param event The event that triggered this method.
     */
    @FXML
    void onActionCustSave(ActionEvent event) {


            String name = custNameTxt.getText();
            String address = custAddressTxt.getText();
            String postalCode = custPostalTxt.getText();
            String phone = custPhoneTxt.getText();
            String divisionName = custStateBox.getValue();

            //Validation: Check if any fields are empty
            if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || divisionName == null) {
                UserInterfaceUtil.displayAlert("Please ensure all fields are filled.", "Missing Information", Alert.AlertType.WARNING );
                return; //Exit without adding a customer
            }


            // Convert division name to division ID
            int divisionId = DivisionUtil.getDivisionIdByDivisionName(divisionName);

            Customer newCustomer = new Customer(0, name, address, postalCode, phone, divisionId);

            try {
                CustomerDAO.addCustomer(newCustomer);
                UserInterfaceUtil.displayAlert("The new Customer has been successfully added.", "Customer Added", Alert.AlertType.INFORMATION);
                returnToMain(event);
            } catch (SQLException  | IOException e) {
                e.printStackTrace();
                UserInterfaceUtil.displayAlert("An error has occurred while adding the customer. " + "Please ensure all fields are filled and valid.", "Error", Alert.AlertType.ERROR);
            }


    }

    /**
     * Navigates back to the main appointment screen.
     * @param event The event that triggered this method.
     * @throws IOException If the FXML file for the main appointment screen cannot be loaded.
     */
    private void returnToMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointment-view.fxml"));
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
     * Initializes the controller class. Populates the country combo box and sets up
     * a listener to update the state/province combo box when a country is selected.
     *
     * @param url The location used to resolve the relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> countryList = CountryDAO.getAllCountries();
            custCountryBox.setItems(countryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        custCountryBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateStateBox(newValue);
        });
    }

    /**
     * Updates the state/Province box with available options based on the selected country.
     * @param country The selected country for which to retrieve the state/provinces.
     */
    private void updateStateBox(String country) {
        try {
            ObservableList<String> divisionList = DivisionUtil.getDivisionsByCountry(country);
            custStateBox.setItems(divisionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
