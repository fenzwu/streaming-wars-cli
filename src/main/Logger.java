import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {

    private static final String TABLE_NAME = "log";

    private Connection connection;

    Logger() {
        this.connection = DBConnection.connect();
        initTable();
    }

    /**
     * Addds a log entry into the log table
     * @param subject Who is the entry about
     * @param message What is the entry about
     */
    public void log(String subject, String message) {
        //Save log to DB
        String sql = "INSERT INTO " + TABLE_NAME +"(subject, message) VALUES(?,?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Clears all log entries from DB
     */
    public void clearLogs() {
        String sql = "DELETE FROM " + TABLE_NAME;
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieve logs from DB
     * @return List of dictionaries containing logs. keys: "subject", "message", "time".
     */
    public ArrayList<HashMap<String, String>> getLogs() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        ArrayList<HashMap<String, String>> logs = new ArrayList<HashMap<String, String>>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                HashMap<String, String> log = new HashMap<String, String>();
                log.put("subject", rs.getString("subject"));
                log.put("message", rs.getString("message"));
                log.put("time", rs.getString("time"));
                logs.add(log);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }

    /**
     * Ensures log table exists
     */
    private void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY,"
                + "subject TEXT NOT NULL,"
                + "message TEXT NOT NULL,"
                + "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        try(Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
