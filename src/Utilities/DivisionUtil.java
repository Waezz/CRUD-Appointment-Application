package Utilities;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionUtil {

    public static String getCountryNameByDivisionId(int divisionId) {
        String  query = "SELECT C.Country" +
                        "FROM first_level_divisions FLD" +
                        "JOIN countries C on FLD.Country_ID = C.Country_ID" +
                        "WHERE FLD.Division_ID = ?";
        try (Connection conn = JDBC.connection;
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, divisionId);

            try (ResultSet rs =pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Country");
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
