import java.util.*;

public class Studio {
    private final String shortName;
    private final String longName;

    // Map<UniqueName, Event>
    private Map<String, Event> eventsOffered = new LinkedHashMap<>();
    // Map<Time(%d-%d), Revenue>
    private Map<String, Integer> licenseFeeLedger = new HashMap<>();

    private int currentPeriod = 0;
    private int previousPeriod = 0;
    private int totalLicenseRevenue = 0;

    public Studio(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public Event createEvent(String type, String name, String year, int duration, int licenseFee) {
        Event event = new Event(
                /*type=*/type,
                /*name=*/name,
                /*year=*/Integer.parseInt(year),
                /*duration=*/duration,
                /*studio=*/this,
                /*licenseFee=*/licenseFee);

        eventsOffered.putIfAbsent(event.formatUniqueName(name, year), event);
        return event;
    }

    public void licenseEvent(Event event) {
        // Adds licensing revenue for the event.
        currentPeriod += event.getLicenseFee();
    }

    public void closeMonthlyBooks(Time time) {
        totalLicenseRevenue += currentPeriod;
        previousPeriod = currentPeriod;
        licenseFeeLedger.put(time.getCurrentDate(), currentPeriod);
        currentPeriod = 0;
    }

    @Override
    public String toString() {
        // studio,<shortName>,<longName>
        // current_period,<license fees collected so far in the current month>
        // previous_period,<license fees collected in the previous month>
        // total,<licensing fees for all previous months except the current month>

        return String.format(
                "studio,%s,%s\ncurrent_period,%d\nprevious_period,%d\ntotal,%d",
                shortName, longName, currentPeriod, previousPeriod, totalLicenseRevenue
        );
    }
}