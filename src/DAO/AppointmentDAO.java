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

/**
 * Data Access Object class for handling database operations related to appointments.
 *
 * @author William Deutsch
 */
public class AppointmentDAO {

    /**
     * Retrieves all appointments from the database.
     *
     * @return An Observable list of all appointments.
     * @throws SQLException If there is an issue executing the query.
     */
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

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, startLocal, endLocal, customerId, userId, contactId);
                appointmentsObservableList.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Adds a new appointment to the database.
     *
     * @param appointments The appointment object to add.
     * @throws SQLException If there is an issue executing the query.
     */
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

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointments The appointment object to update.
     * @throws SQLException If there is an issue executing the query.
     */
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

    // Method to get appointments by Month - made redundant by use of LAMBDA
    public static ObservableList<Appointments> getAppointmentsByMonth(LocalDate startOfMonth, LocalDate endOfMonth) throws SQLException {
        return getAppointmentsByDateRange(startOfMonth, endOfMonth);
    }

    // Method to get appointments by Week - made redundant by use of LAMBDA
    public static ObservableList<Appointments> getAppointmentsByWeek(LocalDate startOfWeek, LocalDate endOfWeek) throws SQLException {
        return getAppointmentsByDateRange(startOfWeek, endOfWeek);
    }

    /**
     * Retrieves a list of appointments within a specified date range
     *
     * @param start The start of the date range to retrieve appointments.
     * @param end The end of the date range to retrieve appointments
     * @return An Observable list of appointments within a given date range.
     * @throws SQLException If there is an issue executing the query.
     */
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

    /**
     * Deletes an appointment from the database based on its ID.
     *
     * @param appointmentId The ID of the appointment to delete.
     * @throws SQLException If there is an issue executing the query.
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
        }

    }

    /**
     * Method to map month names to numbers.
     */
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

    /**
     * Method to map contact names to contact_id's.
     */
    private static final Map<String, Integer> CONTACTS_MAP = Map.of(
            "1 - Anika Costa", 1,
            "2 - Daniel Garcia", 2,
            "3 - Li Lee", 3
    );


    /**
     * Converts contact name to its corresponding contact ID.
     *
     * @param contactName The name of the contact.
     * @return The ID of the contact.
     */
    public static int getContactNumber(String contactName) {
        return CONTACTS_MAP.getOrDefault(contactName, -1); //Returns -1 if the name is not found
    }

    /**
     * Converts a month name to its corresponding number.
     *
     * @param monthName The name of the month.
     * @return The number of the month.
     */
    public static int getMonthNumber(String monthName) {
        return MONTHS_MAP.getOrDefault(monthName, -1); //Returns -1 if the month name is not found
    }

    /**
     * Retrieves all appointments associated with a contact_id.
     *
     *
     * @param contactId The contact ID.
     * @return An Observable list of appointments associated with the contact ID.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<Appointments> getAppointmentsByContact(int contactId) throws SQLException {
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, " +
                "End, Customer_ID, User_ID, Contact_ID FROM appointments WHERE Contact_ID = ?";
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, contactId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String type = rs.getString("Type");
                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");

                    Timestamp startUTC = rs.getTimestamp("Start");
                    Timestamp endUTC = rs.getTimestamp("End");

                    LocalDateTime startLocal = startUTC.toLocalDateTime();
                    LocalDateTime endLocal = endUTC.toLocalDateTime();

                    Appointments appointments = new Appointments(appointmentId, title, description, null, type, startLocal, endLocal, customerId, userId, contactId);
                    appointmentsList.add(appointments);

                }
            }
        }
        return appointmentsList;
    }

    /**
     * Retrieves a list of customer IDs associated with appointments within a specific month.
     *
     * @param monthNumber The number of the month.
     * @return An Observable list of customer IDS who have appointments in the specified month.
     * @throws SQLException If there is an issue executing the query.
     */
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

    /**
     * Retrieves a list of customer IDs associated with appointments of a specific type.
     *
     * @param type The type of appointment.
     * @return An Observable list of customer IDs who have appointments of a specified type.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<Integer> getCustomerIdsByType(String type) throws SQLException {
        String query = "SELECT DISTINCT Customer_ID FROM appointments WHERE Type = ?";
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                customerIds.add(rs.getInt("Customer_ID"));
            }
        }
        return customerIds;
    }

    /**
     * Retrieves a list of customer IDs associated with appointments in a specific country.
     *
     * @param countryName The name of the country.
     * @return An Observable list of customer IDs who have appointments of the specified type.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<Integer> getCustomerIdsByCountry(String countryName) throws SQLException {
        String query = "SELECT DISTINCT customers.Customer_ID FROM customers " +
                       "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                       "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID " +
                       "WHERE countries.Country = ?";
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, countryName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                customerIds.add(rs.getInt("Customer_ID"));
            }
        }
        return customerIds;
    }

    /**
     * Retrieves customer data for a given list of customer IDs.
     *
     * @param ids An Observable list of customer IDs.
     * @return An Observable list of customer objects corresponding to the given IDs.
     * @throws SQLException If there is an issue executing the query.
     */
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

    /**
     * Retrieves a list of appointment types from the database.
     *
     * @return An Observable list of distinct appointment types.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<String> getAllAppointmentTypes() throws SQLException {
        String query = "SELECT DISTINCT Type FROM appointments";
        ObservableList<String> types = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                types.add(rs.getString("Type"));
            }
        }
        return types;
    }

    /**
     * Retrieves all appointments for a given customer.
     *
     * @param customerId The ID of the customer.
     * @return An Observable list of appointments for the specified customer.
     * @throws SQLException
     */
    public static ObservableList<Appointments> getAppointmentsByCustomer(int customerId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE Customer_ID = ?";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Timestamp startUTC = rs.getTimestamp("Start");
                Timestamp endUTC = rs.getTimestamp("End");

                LocalDateTime startLocal = startUTC.toLocalDateTime();
                LocalDateTime endLocal = endUTC.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, startLocal, endLocal, customerId, userId, contactId);
                appointments.add(appointment);
            }
        }
             return appointments;
    }



}


