import java.util.HashMap;

public class Service extends RevenueTracker {

    private String shortName;
    private String longName;
    private int subscriptionPrice;
    private int licensingCosts;
    private Directory directory;

    // Tracks whether this instance of a streaming service has been used to access or view a movie within the time
    // period, used to determine if the subscription price can be updated per update_stream requirement:
    // The subscription price can be changed only at the beginning of a time period before that specific streaming
    // service has no been used to access and view any movies.
    private Boolean hasBeenUsed = false;

    Service(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
        subscriptionPrice = 0;
        licensingCosts = 0;
        directory = new Directory();
    }

    public boolean offersEvent(Event event) {
        return directory.hasEvent(event);
    }

    public void offerMovie(Event event) {
       directory.addMovie(event);
       licensingCosts += event.getLicenseFee();
    }

    public void offerPPV(Event event, int price) {
        directory.addPPV(event, price);
        licensingCosts += event.getLicenseFee();
    }

    public void markServiceUsed() {
        hasBeenUsed = true;
    }

    public HashMap<String, String> getServiceInfo() {
        HashMap<String, String> dict = new HashMap<String, String>();
        dict.put("shortName", shortName);
        dict.put("longName", longName);
        dict.put("subscriptionPrice", String.valueOf(subscriptionPrice));
        dict.put("currMonthRevenue", String.valueOf(currentMonthRevenue));
        dict.put("prevMonthRevenue", String.valueOf(prevMonthRevenue));
        dict.put("totalRevenue", String.valueOf(totalRevenue));
        dict.put("licensingCosts", String.valueOf(licensingCosts));
        return dict;
    }

    @Override
    public void onNextMonth() {
        super.onNextMonth();
        hasBeenUsed = false;
        directory.clear();
    }

    public String getShortName() { return shortName; }

    public int getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(int subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Boolean getHasBeenUsed() { return hasBeenUsed; }

    public Directory getDirectory() {
        return directory;
    }


}
