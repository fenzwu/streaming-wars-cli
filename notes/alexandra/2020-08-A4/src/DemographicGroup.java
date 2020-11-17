import java.util.*;

public class DemographicGroup {

    private final String shortName;
    private final String longName;
    private int numOfAccounts;

    private Map<StreamingService, Integer> activeSubscriptions = new HashMap<>();
    private Map<Offering, Integer> ppvsWatched = new HashMap<>();
    private Map<String, Integer> viewingCostsLedger = new HashMap<>();

    private int currentPeriod = 0;
    private int previousPeriod = 0;
    private int totalViewingCosts = 0;

    public DemographicGroup(String shortName, String longName, int numOfAccounts) {
        this.shortName = shortName;
        this.longName = longName;
        this.numOfAccounts = numOfAccounts;
    }

    public void watchEvent(StreamingService streamingService, String eventName, int percent) {
        Offering offering = streamingService.selectOffering(eventName);
        if (offering == null) {
            return;
        }

        int numOfViewers = calculateNumberOfAccounts(percent);
        if (offering.event.getEventType().equals("movie")) {
            calculateSubscriptionsNeeded(streamingService, numOfViewers);
        } else if (offering.event.getEventType().equals("ppv")) {
            calculatePPVCosts(streamingService, offering, numOfViewers);
        }
    }

    private int calculateNumberOfAccounts(int percent) {
        return (int) (this.numOfAccounts * (percent/100.0));
    }

    private void calculatePPVCosts(StreamingService streamingService, Offering offering, int numOfViewers) {
        int numOfAccountsAlreadyWatched = ppvsWatched.getOrDefault(offering, 0);
        if (numOfAccountsAlreadyWatched == 0) {
            // Purchase PPVs.
            purchasePPVs(streamingService, offering, numOfViewers);

            // Update number of accounts who watched the offering.
            ppvsWatched.put(offering, numOfViewers);
        } else if (numOfViewers > numOfAccountsAlreadyWatched) {
            // Need to pay for the PPV.
            int numOfNewViewers = numOfViewers - numOfAccountsAlreadyWatched;

            // Purchase PPV.
            purchasePPVs(streamingService, offering, numOfNewViewers);

            // Update number of accounts that watched the PPV this month.
            ppvsWatched.replace(offering, numOfAccountsAlreadyWatched + numOfNewViewers);
        }
    }

    private void calculateSubscriptionsNeeded(StreamingService streamingService, int numOfViewers) {
        int numOfSubscriptions = activeSubscriptions.getOrDefault(streamingService, 0);
        if (numOfSubscriptions == 0) {
            // Purchase subscriptions.
            purchaseSubscriptions(streamingService, numOfViewers);

            // Update number of subscriptions to service.
            activeSubscriptions.put(streamingService, numOfViewers);
        } else if (numOfViewers > numOfSubscriptions) {
            // Need to purchase more subscriptions.
            int newSubscriptionsNeeded = numOfViewers - numOfSubscriptions;

            // Purchase subscriptions.
            purchaseSubscriptions(streamingService, newSubscriptionsNeeded);

            // Update number of subscriptions to service for this month.
            activeSubscriptions.replace(streamingService, numOfSubscriptions + newSubscriptionsNeeded);
        }
    }

    private void purchaseSubscriptions(StreamingService streamingService, int numSubscriptionsNeeded) {
        int purchaseTotal = streamingService.getSubscriptionPrice() * numSubscriptionsNeeded;

        // Record revenue for Streaming Service.
        streamingService.recordRevenue(purchaseTotal);

        // Increments the current periods spending to include the subscription(s) purchase.
        currentPeriod += purchaseTotal;
    }

    private void purchasePPVs(StreamingService streamingService, Offering offering, int numNewViewers) {
        PPV ppv = (PPV) offering;
        int purchaseTotal = ppv.getPrice() * numNewViewers;

        // Record revenue for Streaming Service.
        streamingService.recordRevenue(purchaseTotal);

        // Increments the current periods spending to include the ppv(s) purchase.
        currentPeriod += purchaseTotal;
    }

    public void closeMonthlyBooks(Time time) {
        totalViewingCosts += currentPeriod;
        previousPeriod = currentPeriod;
        viewingCostsLedger.put(time.getCurrentDate(), currentPeriod);
        currentPeriod = 0;
        activeSubscriptions = new HashMap<>();
    }

    @Override
    public String toString() {
        // demo,<shortName>,<longName>
        // size,<numOfAccounts>
        // current_period,<spending so far in the current month>
        // previous_period,<spending in the previous month>
        // total,<spending for all previous months except the current month>

        return String.format(
                "demo,%s,%s\nsize,%d\ncurrent_period,%d\nprevious_period,%d\ntotal,%d",
                shortName, longName, numOfAccounts, currentPeriod, previousPeriod, totalViewingCosts);
    }
}
