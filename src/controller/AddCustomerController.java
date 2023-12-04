package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import Utilities.DivisionUtil;
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

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;


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

            String name = custNameTxt.getText();
            String address = custAddressTxt.getText();
            String postalCode = custPostalTxt.getText();
            String phone = custPhoneTxt.getText();
            String divisionName = custStateBox.getValue();

            // Convert division name to division ID
            int divisionId = DivisionUtil.getDivisionIdByDivisionName(divisionName);

            Customer newCustomer = new Customer(0, name, address, postalCode, phone, divisionId);

            try {
                CustomerDAO.addCustomer(newCustomer);
                showAlert("The new Customer has been successfully added.", "Customer Added", Alert.AlertType.CONFIRMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("An error has occurred while adding the customer: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
            }


    }

    public static void showAlert(String content, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null); //Remove ugly icon
        alert.setContentText(content);
        alert.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> countryList = CountryDAO.getAllCountries();
            custCountryBox.setItems(countryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * Add Listener to the Country combo box to update the state/ province when a country is selected
         */
        custCountryBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateStateBox(newValue);
        });
    }

    private void updateStateBox(String country) {
        try {
            ObservableList<String> divisionList = DivisionUtil.getDivisionsByCountry(country);
            custStateBox.setItems(divisionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
