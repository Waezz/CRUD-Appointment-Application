package DAO;

import Utilities.JDBC;
import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for handing database operations related to users.
 *
 * @author William Deutsch
 */
public class UserDAO {

    /**
     * Validates a user's log in credentials against the database.
     *
     * @param username The username to be validated.
     * @param password The password to be validated.
     * @return true if the credentials are valid and the user exists, false otherwise.
     */
    public static boolean validateUser (String username, String password) {
        String query = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try ( PreparedStatement pstmt = JDBC.connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
