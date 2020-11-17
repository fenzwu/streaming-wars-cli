import java.util.*;

public class Manager {

    // Map<shortName, DemographicGroup>
    private Map<String, DemographicGroup> demographicGroups = new HashMap<>();
    // Map<shortName, Studio>
    private Map<String, Studio> studios = new HashMap<>();
    // Map<shortName, StreamingService>
    private Map<String, StreamingService> streamingServices = new HashMap<>();
    // Map<uniqueName, Event>
    private Map<String, Event> events = new LinkedHashMap<>();

    private Set<Offering> currentOfferings = new LinkedHashSet<>();

    public Manager() {}

    public void createDemographicGroup(String shortName, String longName, int numOfAccounts) {
        DemographicGroup dm = new DemographicGroup(shortName, longName, numOfAccounts);
        demographicGroups.putIfAbsent(shortName, dm);
    }

    public DemographicGroup lookupDemographicGroup(String shortName) {
        return demographicGroups.get(shortName);
    }

    public void createStudio(String shortName, String longName) {
        Studio studio = new Studio(shortName, longName);
        studios.putIfAbsent(shortName, studio);
    }

    public Studio lookupStudio(String shortName) {
        return studios.get(shortName);
    }

    public void createStreamingService(String shortName, String longName, int subscriptionPrice) {
        StreamingService streamingService = new StreamingService(shortName, longName, subscriptionPrice);
        streamingServices.putIfAbsent(shortName, streamingService);
    }

    public StreamingService lookupStreamingService(String shortName) {
        return streamingServices.get(shortName);
    }

    public void addEvent(String uniqueName, Event event) {
        events.putIfAbsent(uniqueName, event);
    }

    public Event lookupEvent(String titleName, String yearProduced) {
        String uniqueName = Event.formatUniqueName(titleName, yearProduced);
        return events.get(uniqueName);
    }

    public Collection<Event> listEvents() {
        return events.values();
    }

    public void addOffering(Offering offering) {
        currentOfferings.add(offering);
    }

    public Set<Offering> getCurrentOfferings() {
        return currentOfferings;
    }

    public void closeBooks(Time time) {
        currentOfferings = new LinkedHashSet<>();

        for (DemographicGroup demographicGroup : demographicGroups.values()) {
            demographicGroup.closeMonthlyBooks(time);
        }

        for (Studio studio : studios.values()) {
            studio.closeMonthlyBooks(time);
        }

        for (StreamingService streamingService : streamingServices.values()) {
            streamingService.closeMonthlyBooks(time);
        }
    }




}