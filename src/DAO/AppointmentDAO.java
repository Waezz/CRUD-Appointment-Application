package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;
import java.time.LocalDateTime;
/**
public class AppointmentDAO {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        String query ="SELECT Appointment_ID, Title, Description, Location, Type, Start, " +
                      "End, Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS";

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = JDBC.connection.prepareStatement(query);
             ResultSet rs =pstmt.executeQuery()) {

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                LocalDateTime start = startTimeStamp !=null ? startTimeStamp.toLocalDateTime() : null;
                Timestamp endTimeStamp = rs.getTimestamp("End");
                LocalDateTime end = endTimeStamp !=null ? endTimeStamp.toLocalDateTime() : null;
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments( )
            }
        }
    }
}
**/