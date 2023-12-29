package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for handling the database operations related to countries.
 *
 * @author William Deutsch
 */
public class CountryDAO {

    /**
     * Retrieves a list of all countries from the database.
     *
     * @return An Observable list of strings, where each string is the name of a country.
     * @throws SQLException If there is an issue executing the query.
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        String query = "SELECT Country FROM countries";
        ObservableList<String> countries = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                countries.add(rs.getString("Country"));
            }
        }
        return countries;
    }
}

