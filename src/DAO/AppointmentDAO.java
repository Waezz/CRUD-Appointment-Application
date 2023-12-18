package DAO;

import Utilities.JDBC;
import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;
import com.mysql.cj.result.ZonedDateTimeValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentDAO {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, " +
                "End, Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS";

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = JDBC.connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Timestamp startUTC = rs.getTimestamp("Start");
                Timestamp endUTC = rs.getTimestamp("End");

                LocalDateTime startLocal = startUTC.toLocalDateTime();
                LocalDateTime endLocal = endUTC.toLocalDateTime();

                System.out.println("Fetched appt: " + appointmentId + "  Start UTC- " + startUTC);
                System.out.println("Fetched appt: " + appointmentId + "  End UTC- " + endUTC);
                System.out.println("Fetched appt: " + appointmentId + "  Start Local- " + startLocal);
                System.out.println("Fetched appt: " + appointmentId + "  End Local- " + endLocal);

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, startLocal, endLocal, customerId, userId, contactId);
                appointmentsObservableList.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

    public static void addAppointment(Appointments appointments) throws SQLException {
        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, appointments.getTitle());
            pstmt.setString(2, appointments.getDescription());
            pstmt.setString(3, appointments.getLocation());
            pstmt.setString(4, appointments.getType());
            pstmt.setTimestamp(5, Timestamp.valueOf(appointments.getStart()));
            pstmt.setTimestamp(6, Timestamp.valueOf(appointments.getEnd()));
            pstmt.setInt(7, appointments.getCustomerId());
            pstmt.setInt(8, appointments.getUserId());
            pstmt.setInt(9, appointments.getContactId());

            pstmt.executeUpdate();

        }
    }

    public static void updateAppointment(Appointments appointments) throws SQLException {
        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try (Connection conn = JDBC.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, appointments.getTitle());
            pstmt.setString(2, appointments.getDescription());
            pstmt.setString(3, appointments.getLocation());
            pstmt.setString(4, appointments.getType());
            pstmt.setTimestamp(5, Timestamp.valueOf(appointments.getStart()));
            pstmt.setTimestamp(6, Timestamp.valueOf(appointments.getEnd()));
            pstmt.setInt(7, appointments.getCustomerId());
            pstmt.setInt(8, appointments.getUserId());
            pstmt.setInt(9, appointments.getContactId());
            pstmt.setInt(10, appointments.getAppointmentId());

            pstmt.executeUpdate();

        }
    }

    // Method to get appointments by Month
    public static ObservableList<Appointments> getAppointmentsByMonth(LocalDate startOfMonth, LocalDate endOfMonth) throws SQLException {
        return getAppointmentsByDateRange(startOfMonth, endOfMonth);
    }

    // Method to get appointments by Week
    public static ObservableList<Appointments> getAppointmentsByWeek(LocalDate startOfWeek, LocalDate endOfWeek) throws SQLException {
        return getAppointmentsByDateRange(startOfWeek, endOfWeek);
    }

    // Method to get appointments by date range
    public static ObservableList<Appointments> getAppointmentsByDateRange(LocalDate start, LocalDate end) throws SQLException {
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, " +
                "End, Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS WHERE DATE(Start) BETWEEN ? AND ?";

        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setDate(1, java.sql.Date.valueOf(start));
            pstmt.setDate(2, java.sql.Date.valueOf(end));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Location");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                filteredAppointments.add(appointments);
            }
        }
        return filteredAppointments;
    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt( 1, appointmentId);
            pstmt.executeUpdate();
        }

    }
}
