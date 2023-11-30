package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentController {

    Stage stage;
    Parent scene;

    @FXML
    private ChoiceBox<?> apptCustBox;

    @FXML
    private TextField apptDescTxt;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<?> apptEndTime;

    @FXML
    private TextField apptIdTxt;

    @FXML
    private TextField apptLocTxt;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private ComboBox<?> apptStartTIme;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ChoiceBox<?> apptUserBox;

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

}
