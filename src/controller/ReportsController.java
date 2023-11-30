package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

        Stage stage;
        Parent scene;

        @FXML
        private TableColumn<?, ?> addressColumn1;

        @FXML
        private TableColumn<?, ?> addressColumn2;

        @FXML
        private TableColumn<?, ?> addressColumn3;

        @FXML
        private TableColumn<?, ?> apptIdColumn;

        @FXML
        private ChoiceBox<?> byContactBox;

        @FXML
        private Tab byContactTab;

        @FXML
        private ChoiceBox<?> byCountryBox;

        @FXML
        private Tab byCountryTab;

        @FXML
        private TableView<?> byCountryTableView;

        @FXML
        private ChoiceBox<?> byMonthBox;

        @FXML
        private Tab byMonthTab;

        @FXML
        private TableView<?> byMonthTableView;

        @FXML
        private ChoiceBox<?> byTypeBox;

        @FXML
        private Tab byTypeTab;

        @FXML
        private TableView<?> byTypeTableView;

        @FXML
        private TableView<?> contactTableView;

        @FXML
        private TableColumn<?, ?> countryColumn1;

        @FXML
        private TableColumn<?, ?> countryColumn2;

        @FXML
        private TableColumn<?, ?> countryColumn3;

        @FXML
        private TableColumn<?, ?> customerIdColumn;

        @FXML
        private TableColumn<?, ?> customerIdColumn1;

        @FXML
        private TableColumn<?, ?> customerIdColumn2;

        @FXML
        private TableColumn<?, ?> customerIdColumn3;

        @FXML
        private TableColumn<?, ?> descriptionColumn;

        @FXML
        private TableColumn<?, ?> endDateColumn;

        @FXML
        private TableColumn<?, ?> endTimeColumn;

        @FXML
        private TableColumn<?, ?> nameColumn1;

        @FXML
        private TableColumn<?, ?> nameColumn2;

        @FXML
        private TableColumn<?, ?> nameColumn3;

        @FXML
        private TableColumn<?, ?> phoneColumn1;

        @FXML
        private TableColumn<?, ?> phoneColumn2;

        @FXML
        private TableColumn<?, ?> phoneColumn3;

        @FXML
        private TableColumn<?, ?> postalCodeColumn1;

        @FXML
        private TableColumn<?, ?> postalCodeColumn2;

        @FXML
        private TableColumn<?, ?> postalCodeColumn3;

        @FXML
        private TableColumn<?, ?> startDateColumn;

        @FXML
        private TableColumn<?, ?> startTimeColumn;

        @FXML
        private TableColumn<?, ?> stateColumn1;

        @FXML
        private TableColumn<?, ?> stateColumn2;

        @FXML
        private TableColumn<?, ?> stateColumn3;

        @FXML
        private TableColumn<?, ?> titleColumn;

        @FXML
        private TableColumn<?, ?> typeColumn;

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


