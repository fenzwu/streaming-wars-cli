import java.util.ArrayList;
import java.util.HashMap;

public class Event {

    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private int licenseFee;
    private Studio studio;
    private EventType type;

    Event(EventType type, String name, int year, int duration) {
        this.type = type;
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public HashMap<String, String> getEventInfo() {
        HashMap<String, String> dict = new HashMap<String, String>();
        dict.put("type", type.toString());
        dict.put("name", name);
        dict.put("year", String.valueOf(year));
        dict.put("duration", String.valueOf(duration));
        dict.put("studio", studio.getShortName());
        dict.put("licenseFee",String.valueOf(licenseFee));
        return dict;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLicenseFee() {
        return licenseFee;
    }

    public void setLicenseFee(int licenseFee) {
        this.licenseFee = licenseFee;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getID() {
        return this.name + this.year;
    }


}
