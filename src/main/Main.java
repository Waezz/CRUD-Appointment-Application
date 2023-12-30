package main;

import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Appointment Management application.
 * It is the entry point for the application.
 *
 * @author William Deutsch
 */
public class Main extends Application {


    /**
     * Starts the application by setting the initial scene.
     *
     * @param stage The primary stage for this application.
     * @throws IOException IF the FXML file for the login view cannot be loaded.
     */
   @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 375, 450);
        stage.setTitle("Appointment Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method that launches the application.
     * It opens the database connection before launching the application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
    }
}
