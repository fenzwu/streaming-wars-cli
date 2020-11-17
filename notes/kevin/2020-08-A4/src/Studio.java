import java.util.HashMap;

public class Studio extends RevenueTracker {

    private String shortName;
    private String longName;

    Studio(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public HashMap<String, String> getStudioInfo() {
        HashMap<String, String> dict = new HashMap<String, String>();
        dict.put("shortName", shortName);
        dict.put("longName", longName);
        dict.put("currMonthRevenue", String.valueOf(currentMonthRevenue));
        dict.put("prevMonthRevenue", String.valueOf(prevMonthRevenue));
        dict.put("totalRevenue", String.valueOf(totalRevenue));
        return dict;
    }

    public String getShortName() {
        return shortName;
    }
}
