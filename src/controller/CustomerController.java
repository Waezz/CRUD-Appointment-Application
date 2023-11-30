package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<?, ?> customerAddressColumn;

    @FXML
    private TableColumn<?, ?> customerCountryColumn;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> customerPhoneColumn;

    @FXML
    private TableColumn<?, ?> customerPostColumn;

    @FXML
    private TableColumn<?, ?> customerStateColumn;

    @FXML
    private TableView<?> customerTableView;

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

}
