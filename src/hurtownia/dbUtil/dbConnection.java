package hurtownia.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection { ///połączenie z bazą danych

    private static final String USERNAME = "root";
    private static final String PASSWORD = "hurtownia";
    private static final String CONN = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
