import java.util.HashMap;

public final class DemographicGroup {
    public String short_name;
    public String long_name;
    public int accounts;
    public HashMap<String, Integer> subscriptions = new HashMap<String, Integer>();

    private int currentMonthSpending = 0;
    private int previousMonthSpending = 0;
    private int totalSpending = 0;

    public DemographicGroup(String short_name, String long_name, int accounts) {
        this.short_name = short_name;
        this.long_name = long_name;
        this.accounts = accounts;
    }

    public void spend(int amount) {
        currentMonthSpending += amount;
    }

    public void print() {
        System.out.println("demo," + short_name + "," + long_name);
        System.out.println("size," + accounts);
        System.out.println("current_period," + currentMonthSpending);
        System.out.println("previous_period," + previousMonthSpending);
        System.out.println("total," + totalSpending);
    }

    public void advanceMonth() {
        previousMonthSpending = currentMonthSpending;
        totalSpending += currentMonthSpending;
        currentMonthSpending = 0;

        subscriptions.clear();
    }
}
