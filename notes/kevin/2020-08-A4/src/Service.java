import java.util.HashMap;

public class Service extends RevenueTracker {

    private String shortName;
    private String longName;
    private int subscriptionPrice;
    private int licensingCosts;
    private Directory directory;

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

    public int getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(int subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Directory getDirectory() {
        return directory;
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
        directory.clear();
    }
}
