public class User {

    private String username;
    private RoleType role;
    private byte[] hash;

    User(String username, byte[] hash, RoleType role) {
        this.username = username;
        this.role = role;
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
