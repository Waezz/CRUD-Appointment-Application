package DAO;

import Utilities.CountryDivision;
import Utilities.JDBC;
import Utilities.DivisionUtil;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

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

                //Debugging
                System.out.println("Fetched customer: " + id + " - " + name);


                //Create a customer object and add it to the list
                Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return customerObservableList;
    }

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
}
