package controller;

import DAO.CustomerDAO;
import Utilities.UserInterfaceUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ChoiceBox<Integer> apptCustBox;

    @FXML
    private ChoiceBox<Integer> apptContBox;

    @FXML
    private TextField apptDescTxt;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<LocalTime> apptEndTime;

    @FXML
    private TextField apptIdTxt;

    @FXML
    private TextField apptLocTxt;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private ComboBox<LocalTime> apptStartTIme;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ChoiceBox<Integer> apptUserBox;

    @FXML
    void onActionApptSave(ActionEvent event) {

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

    private void populateTimeBoxes() {
        LocalTime startTime = LocalTime.of(8,0); // Start at 8am
        LocalTime endTime = LocalTime.of(21,30); // End at 10pm

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            apptStartTIme.getItems().add(startTime);
            apptEndTime.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }

    private void setUpDatePickerListener() {
        apptStartDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            apptEndDate.setValue(newValue); //Set End Date to match Start Date
        });

        apptEndDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(apptStartDate.getValue())) {
                UserInterfaceUtil.displayAlert("Your Appointment must end on the day that it starts",
                                                        "Invalid End Date", Alert.AlertType.ERROR);
                apptEndDate.setValue(apptStartDate.getValue()); //Reset to start date
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTimeBoxes();
        setUpDatePickerListener();

        // Populate choice boxes
        apptContBox.setItems(FXCollections.observableArrayList(1, 2, 3));
        apptUserBox.setItems(FXCollections.observableArrayList(1, 2));



        // Populate Customer ChoiceBox with current IDS
        try {
            ObservableList<Integer> customerIds = CustomerDAO.getAllCustomerIDs();
            apptCustBox.setItems(customerIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
