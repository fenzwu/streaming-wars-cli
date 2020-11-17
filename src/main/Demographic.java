import java.util.HashMap;

public class Demographic extends RevenueTracker {

    private String shortName;
    private String longName;
    private int numAccounts;
    private HashMap<String, Integer> viewCounts;

    // Tracks whether this instance of a demographic group has viewed any event within the time period, used to
    // determine if the number of accounts in a demographic group can be updated per update_demo requirement:
    // The number of accounts can be changed only at the beginning of a time period before that specific demographic
    // group has accessed and viewed any movies or Pay-Per-View events.
    private Boolean hasViewedEvent = false;

    Demographic(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
        numAccounts = 0;
        viewCounts = new HashMap<String, Integer>();
    }

    public int addMovieView(String serviceName, int subscriptionFee, int percentage) {
        hasViewedEvent = true;
        // If new subscription then initialize viewCount to 0
        if (!viewCounts.containsKey(serviceName)) {
            viewCounts.put(serviceName, 0);
        }

        int revenue = 0;
        int watchCount = numAccounts * percentage / 100;
        int currentCount = viewCounts.get(serviceName);

        if(watchCount > currentCount) {
            int newViews = watchCount - currentCount;
            viewCounts.put(serviceName, watchCount);
            revenue = newViews * subscriptionFee;
            addRevenue(revenue);
        }

        return revenue;
    }

    public int addPPVView(int ppvPrice, int percentage) {
        hasViewedEvent = true;
        int watchCount = numAccounts * percentage / 100;
        int revenue = watchCount * ppvPrice;
        addRevenue(revenue);

        return revenue;
    }

    @Override
    public void onNextMonth() {
        super.onNextMonth();
        hasViewedEvent = false;
        viewCounts.clear();
    }

    public HashMap<String, String> getDemoInfo() {
        HashMap<String, String> info = new HashMap<String, String>();
        info.put("shortName", shortName);
        info.put("longName", longName);
        info.put("numAccounts", String.valueOf(numAccounts));
        info.put("currMonthRevenue", String.valueOf(currentMonthRevenue));
        info.put("prevMonthRevenue", String.valueOf(prevMonthRevenue));
        info.put("totalRevenue", String.valueOf(totalRevenue));

        return info;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    public void setNumAccounts(int numAccounts) {
        this.numAccounts = numAccounts;
    }

    public Boolean getHasViewedEvent() { return hasViewedEvent; }


}
