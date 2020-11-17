import java.util.ArrayList;

public final class State {
    public final ArrayList<DemographicGroup> demographicGroups = new ArrayList<>();
    public final ArrayList<Studio> studios = new ArrayList<>();
    public final ArrayList<StreamingService> streamingServices = new ArrayList<>();
    public final ArrayList<String> offers = new ArrayList<>();
    public final ArrayList<Event> events = new ArrayList<>();

    public int monthTimeStamp = 10;
    public int yearTimeStamp = 2020;
}
