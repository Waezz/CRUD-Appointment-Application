package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCustomerController {

    Stage stage;
    Parent scene;


    @FXML
    private TextField custAddressTxt;

    @FXML
    private ComboBox<?> custCountryBox;

    @FXML
    private TextField custIdTxt;

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhoneTxt;

    @FXML
    private TextField custPostalTxt;

    @FXML
    private ComboBox<?> custStateBox;

    @FXML
    void onActionCustSave(ActionEvent event) {

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

}
