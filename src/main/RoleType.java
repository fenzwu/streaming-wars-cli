public enum RoleType {

    //References enum implementation:
    // https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java

    READER("reader"),
    EDITOR("editor"),
    ADMIN("admin");

    private String text;

    RoleType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static RoleType fromString(String text) {
        for (RoleType e : RoleType.values()) {
            if(e.text.equals(text)) {
                return e;
            }
        }

        throw new IllegalArgumentException("No role matching " + text);
    }

}
