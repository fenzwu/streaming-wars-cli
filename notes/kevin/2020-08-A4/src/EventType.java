public enum EventType {
    //References enum implementation:
    // https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java

    MOVIE("movie"),
    PPV("ppv");

    private String text;

    EventType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static EventType fromString(String text) {
        for (EventType e : EventType.values()) {
            if(e.text.equals(text)) {
                return e;
            }
        }

        throw new IllegalArgumentException("No event type matching " + text);
    }

}
