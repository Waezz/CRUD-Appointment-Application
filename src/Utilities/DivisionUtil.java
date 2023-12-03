package Utilities;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionUtil {

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



}
