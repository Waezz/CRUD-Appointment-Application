package main;

import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * Start Method for setting up the initial scene.
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file for the log in screen cannot be loaded.
     */
   @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 375, 450);
        stage.setTitle("Appointment Management System");
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);




    }
}
