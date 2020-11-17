import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String DB_URL = "jdbc:sqlite:streaming_wars.db";

    public static Connection connect() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}
