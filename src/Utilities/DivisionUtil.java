package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility Class to hold the methods that translate between Division Id's and Country / First-Level-Divisions
 */
public class DivisionUtil {

    /** Method to retrieve country and division names from a Division ID.
     * @param divisionId Takes a division_id.
     * @return Corresponding Country and division data.
     */
    public static CountryDivision getCountryAndDivisionByDivisionId(int divisionId) {
        String  query = "SELECT C.Country, FLD.Division " +
                        "FROM first_level_divisions FLD " +
                        "JOIN countries C ON FLD.Country_ID = C.Country_ID " +
                        "WHERE FLD.Division_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, divisionId);

            try (ResultSet rs =pstmt.executeQuery()) {
                if (rs.next()) {
                    String country =  rs.getString("Country");
                    String division = rs.getString("Division");
                    return new CountryDivision(country,division);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }

        return null;
    }

    public static int getDivisionIdByDivisionName(String divisionName) {
        String query = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";

        try (Connection conn = JDBC.connection;
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, divisionName);

            try (ResultSet rs =pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Division_ID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }

        return -1; //Return invalid ID or change to throw an exception

    }

    /**
     * Method that filters divisions based on the Country name.
     * @param country The country to be filtered
     * @return The associated states/ provinces of said country.
     * @throws SQLException If a database access error occurs/
     */
    public static ObservableList<String> getDivisionsByCountry(String country) throws SQLException {
        String query = "SELECT Division FROM first_level_divisions FLD " +
                       "JOIN countries C ON FLD.Country_ID = C.Country_ID " +
                       "WHERE C.Country = ?";
        ObservableList<String> divisions = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, country);

            try (ResultSet rs =pstmt.executeQuery()) {
                while (rs.next()) {
                    divisions.add(rs.getString("Division"));
                }
            }
        }
        return divisions;
    }
}
