import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public class Authenticator {

    private static final String TABLE_NAME = "users";

    private Connection connection;
    private User activeUser;

    Authenticator() {
        this.connection = DBConnection.connect();
        this.activeUser = null;
        initTable();
        initDefaultAdmin();
    }

    /**
     * Creates a new user (if they don't already exist) and inserts them into the DB
     * @param username User's username
     * @param password User's password
     * @param role User's role
     */
    public void createUser(String username, String password, String role) throws IllegalArgumentException {
        RoleType roleType = RoleType.fromString(role);

        // Check if user already exists first
        if(hasUser(username)) {
            throw new IllegalArgumentException("User " + username + " already exists");
        };

        // Hash the password
        byte[] hash = StringToSHA256(password);

        //Save user to the DB
        String sql = "INSERT INTO " + TABLE_NAME +"(username, hash, role) VALUES(?,?,?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setBytes(2, hash);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Sets the active user
     * @param username User's username
     * @param password User's password
     */
    public void login(String username, String password) throws IllegalArgumentException {

        //Retrieve user from DB
        User user = getUser(username);
        if (user == null) {
            throw new IllegalArgumentException("User " + username + " does not exist");
        }

        //Verify password hashes match
        byte[] testHash = StringToSHA256(password);
        if(Arrays.equals(testHash, user.getHash())) {
            activeUser = user;
        } else {
            throw new IllegalArgumentException("Incorrect password");
        }

    }

    /**
     * Logout the active user (sets it to null)
     */
    public void logout() {
        activeUser = null;
    }

    /**
     * Gets the currently logged in user
     * @return active user
     */
    public HashMap<String, String> getActiveUserInfo() {
        HashMap<String, String> userInfo = new HashMap<String, String>();

        if (activeUser == null) {
            userInfo.put("username", "n/a");
            userInfo.put("role", "n/a");
        } else {
            userInfo.put("username", activeUser.getUsername());
            userInfo.put("role", activeUser.getRole().toString());
        }

        return userInfo;
    }

    /**
     * Changes the active user's password
     * @param newPassword New password
     * @throws IllegalStateException
     */
    public void changePassword(String newPassword) throws IllegalStateException {
        if (activeUser == null) {
            throw new IllegalStateException("No active user");
        }

        byte[] newHash = StringToSHA256(newPassword);
        activeUser.setHash(newHash);

        String sql = "UPDATE " + TABLE_NAME + " SET hash = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBytes(1, newHash);
            pstmt.setString(2, activeUser.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Change the specified user's role
     * @param username Name of user to change role
     * @param newRole New role
     * @throws IllegalStateException
     */
    public void changeRole(String username, String newRole) throws IllegalStateException {

        if (activeUser == null) {
            throw new IllegalStateException("No active user");
        }

        // Don't allow admins to change their own role.
        // This is to prevent a situation where we have no admins
        if (username.equals(activeUser.getUsername()) && activeUser.getRole() == RoleType.ADMIN) {
            throw new IllegalStateException("Admins cannot change their own role");
        }

        RoleType role = RoleType.fromString(newRole);

        String sql = "UPDATE " + TABLE_NAME + " SET role = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, role.toString());
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //Update active user if needed
        if(username.equals(activeUser.getUsername())) {
            activeUser.setRole(role);
        }

    }

    /**
     * Ensures users table exists
     */
    private void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + "username TEXT PRIMARY KEY,"
                        + "hash BLOB NOT NULL,"
                        + "role TEXT NOT NULL"
                        + ");";

        try(Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if the given user exists
     * @param username User's username
     * @return True if user exists, false otherwise
     */
    private boolean hasUser(String username) {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME
                        + " WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.getInt(1) > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private User getUser(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Check if result is empty
            if (!rs.isBeforeFirst()) {
                return null;
            }

            byte[] hash = rs.getBytes("hash");
            RoleType role = RoleType.fromString(rs.getString("role"));

            return new User(username, hash, role);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Hashes given string with sha256
     * @param text string to hash
     * @return sha256 result
     */
    private byte[] StringToSHA256(String text) {
        byte[] hash = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return hash;
    }

    /**
     * Creates a default admin user when there are no users
     */
    private void initDefaultAdmin() {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.getInt(1) == 0) {
                // Create initial admin account
                createUser("admin","admin",RoleType.ADMIN.toString());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
