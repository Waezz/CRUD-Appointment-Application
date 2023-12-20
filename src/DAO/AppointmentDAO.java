package DAO;

import Utilities.CountryDivision;
import Utilities.DivisionUtil;
import Utilities.JDBC;
import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;
import com.mysql.cj.result.ZonedDateTimeValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customer;

import java.sql.*;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentDAO {

    //Method for fetching all appointments from the DB
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

    //Method for adding a new appointment to the DB
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

    //Method for updating existing appointments in the DB
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
             PreparedStatement pstmt = conn.prepareStatement(query)) {

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

    //Method for removing appointments from the DB
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
        }

    }

    //Method to map month names to numbers
    private static final Map<String, Integer> MONTHS_MAP = new HashMap<>();
    static {
        MONTHS_MAP.put("January", 1);
        MONTHS_MAP.put("February", 2);
        MONTHS_MAP.put("March", 3);
        MONTHS_MAP.put("April", 4);
        MONTHS_MAP.put("May", 5);
        MONTHS_MAP.put("June", 6);
        MONTHS_MAP.put("July", 7);
        MONTHS_MAP.put("August", 8);
        MONTHS_MAP.put("September", 9);
        MONTHS_MAP.put("October", 10);
        MONTHS_MAP.put("November", 11);
        MONTHS_MAP.put("December", 12);
    }

    public static int getMonthNumber(String monthName) {
        return MONTHS_MAP.getOrDefault(monthName, -1); //Returns -1 if the month name is not found
    }

    //Method that finds appointments based on a month chosen by the user
    public static ObservableList<Integer> getCustomerIdsByMonth(int monthNumber) throws SQLException {
        String query = "SELECT DISTINCT Customer_ID FROM appointments WHERE MONTH(Start) = ?";
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, monthNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                customerIds.add(rs.getInt("Customer_ID"));
            }
        }
        return customerIds;
    }

    public static ObservableList<Customer> getCustomersById(ObservableList<Integer> ids) throws SQLException {
        if (ids.isEmpty()) return FXCollections.observableArrayList();

        String idList = ids.stream().map(Object::toString).collect(Collectors.joining(", "));
        String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers WHERE Customer_ID IN (" + idList + ")";

        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");


                //Use helpers function to get the country name and division from divisionID
                CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(divisionId);
                String country = countryDivision != null ? countryDivision.getCountry() : "";
                String divisionName = countryDivision != null ? countryDivision.getDivision() : "";

                customers.add(new Customer(id, name, address, postalCode, phone, divisionId));
            }
        }
        return customers;
    }

    /*

    public static ObservableList<Customer> getCustomerByMonth(String monthName) throws SQLException {

        int monthNumber = getMonthNumber(monthName);
        if (monthNumber == -1) {
            //Handle invalid month name
            return FXCollections.observableArrayList();
        }

        String query = "SELECT DISTINCT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID " +
                "FROM customers c JOIN appointments a ON c.Customer_ID = a.Customer_ID " +
                "WHERE MONTH(a.Start) = ?";
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setInt(1, monthNumber);
             ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {
                 int id = rs.getInt("Customer_ID");
                 String name =rs.getString("Customer_Name");
                 String address = rs.getString("Address");
                 String postalCode = rs.getString("Postal_Code");
                 String phone = rs.getString("Phone");
                 int divisionId = rs.getInt("Division_ID");


                 //Use helpers function to get the country name and division from divisionID
                 CountryDivision countryDivision = DivisionUtil.getCountryAndDivisionByDivisionId(divisionId);
                 String country = countryDivision != null ? countryDivision.getCountry() : "";
                 String divisionName = countryDivision != null ? countryDivision.getDivision() : "";

                 Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
                 customer.add(customer);
             }
        }
        return customers;
     */

    }


