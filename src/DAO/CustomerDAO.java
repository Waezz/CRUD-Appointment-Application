package DAO;

import Utilities.CountryDivision;
import Utilities.JDBC;
import Utilities.DivisionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for handling database operations related to customers.
 *
 * @author William Deutsch
 */
public class CustomerDAO {

    /**
     * Retrieves all customers from the database.
     *
     * @return An Observable list of customer objects representing all customers in the database.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String query ="SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = JDBC.connection.prepareStatement(query);
             ResultSet rs =pstmt.executeQuery()) {

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

                //Create a customer object and add it to the list
                Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return customerObservableList;
    }

    /**
     * Retrieves all customer IDs from the database.
     *
     * @return An Observable list of INT objects representing the IDs of all customers.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        String query = "SELECT Customer_ID FROM customers ORDER BY Customer_ID ASC";
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id =rs.getInt("Customer_ID");
                customerIds.add(id);
            }
        } return customerIds;
    }

    /**
     * Adds a new customer to the database.
     *
     * @param customer The customer object to add to the database.
     * @throws SQLException If there is an issue executing the query.
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, customer.getPhone());
            pstmt.setInt(5, customer.getDivisionId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer The customer object with information to modify in the database.
     * @throws SQLException If there is an issue executing the query.
     */
    public static void updateCustomer (Customer customer) throws SQLException {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, customer.getPhone());
            pstmt.setInt(5, customer.getDivisionId());
            pstmt.setInt(6, customer.getId());

            pstmt.executeUpdate();

        }

    }

    /**
     * Deletes a customer from the database based on their ID.
     *
     * @param customerId The ID of the customer to delete
     * @throws SQLException If there is an issue executing the query.
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }
}
