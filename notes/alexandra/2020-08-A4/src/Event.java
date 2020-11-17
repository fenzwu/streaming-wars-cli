public class Event {

    private final String uniqueName;
    private final String eventType;
    private final Studio studio;
    private final int duration; // Measured in minutes.
    private final int licenseFee;

    public Event(String type, String name, int year, int duration, Studio studio, int licenseFee) {
        this.uniqueName = String.format("%s,%d", name, year);
        this.eventType = type;
        this.studio = studio;
        this.duration = duration;
        this.licenseFee = licenseFee;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getEventType() {
        return eventType;
    }

    public Studio getStudio() {
        return studio;
    }

    public int getLicenseFee() {
        return licenseFee;
    }

    public static String formatUniqueName(String titleName, String yearProduced) {
        return String.format("%s,%s", titleName, yearProduced);
    }

    @Override
    public String toString() {
        // <type>,<uniqueName>,<duration>,<studio>,<licenseFee>
        return String.format("%s,%s,%d,%s,%d", eventType, uniqueName, duration, studio.getShortName(), licenseFee);
    }
}
