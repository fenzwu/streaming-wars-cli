import java.util.*;

public class StreamingService {

    private final String shortName;
    private final String longName;
    private final int subscriptionPrice;

    // Map<currentDate, monthlyRevenue>
    private Map<String, Integer> revenueLedger = new HashMap<>();
    // Map<UniqueName, Offering>
    private Map<String, Offering> currentOfferings = new HashMap<>();

    private int currentPeriod = 0;
    private int previousPeriod = 0;
    private int totalSubscriptionRevenue = 0;
    private int licensingFees = 0;

    public StreamingService(String shortName, String longName, int subscriptionPrice) {
        this.shortName = shortName;
        this.longName = longName;
        this.subscriptionPrice = subscriptionPrice;
    }

    public Offering selectOffering(String eventName) {
        return currentOfferings.get(eventName);
    }

    public Offering offerMovie(Event event) {
        if (!event.getEventType().equals("movie") || currentOfferings.containsKey(event.getUniqueName())) {
            return null;
        }
        processOffering(event);
        Offering movie = new Movie(/*streamingService=*/this,/*event=*/event);
        currentOfferings.put(event.getUniqueName(), movie);
        return movie;
    }

    public Offering offerPPV(Event event, int price) {
        if (!event.getEventType().equals("ppv") || currentOfferings.containsKey(event.getUniqueName())) {
            return null;
        }
        processOffering(event);
        Offering ppv = new PPV(/*streamingService=*/this,/*event=*/event,/*price=*/price);
        currentOfferings.put(event.getUniqueName(), ppv);
        return ppv;
    }

    private void processOffering(Event event) {
        payLicenseFee(event);
        Studio studio = event.getStudio();
        studio.licenseEvent(event);
    }

    private void payLicenseFee(Event event) {
        licensingFees += event.getLicenseFee();
    }

    public void recordRevenue(int revenue) {
        currentPeriod += revenue;
    }

    public String getShortName() {
        return shortName;
    }

    public int getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void closeMonthlyBooks(Time time) {
        totalSubscriptionRevenue += currentPeriod;
        previousPeriod = currentPeriod;
        revenueLedger.put(time.getCurrentDate(), currentPeriod);
        currentPeriod = 0;
        currentOfferings = new HashMap<>();
    }

    @Override
    public String toString() {
        // stream,<shortName>,<longName>
        // subscription,<subscriptionPrice>
        // current_period,<subscription fees so far in the current month>
        // previous_period,<subscription fees in the previous month>
        // total,<subscription fees for all previous months except the current month>
        // licensing,<costs for all months since the program started>

        return String.format(
                "stream,%s,%s\nsubscription,%d\ncurrent_period,%d\nprevious_period,%d\ntotal,%d\nlicensing,%d",
                shortName, longName, subscriptionPrice, currentPeriod, previousPeriod, totalSubscriptionRevenue, licensingFees);
    }
}

