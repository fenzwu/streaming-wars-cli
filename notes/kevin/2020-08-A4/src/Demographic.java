import java.util.HashMap;

public class Demographic extends RevenueTracker {

    private String shortName;
    private String longName;
    private int numAccounts;
    private HashMap<String, Integer> viewCounts;

    Demographic(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
        numAccounts = 0;
        viewCounts = new HashMap<String, Integer>();
    }

    public int addMovieView(String serviceName, int subscriptionFee, int percentage) {
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
        int watchCount = numAccounts * percentage / 100;
        int revenue = watchCount * ppvPrice;
        addRevenue(revenue);

        return revenue;
    }

    @Override
    public void onNextMonth() {
        super.onNextMonth();
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


}
