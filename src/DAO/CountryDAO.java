package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
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

