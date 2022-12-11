package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    static Connection conn = null;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/hokimart?" + "user=root&password= ");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        if (conn != null) {
            System.out.println("Connection Established");
        }
        return conn != null ? conn : null;
    }

    public static void close() throws SQLException {
        conn.close();
    }
}
