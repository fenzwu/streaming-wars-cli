public class RevenueTracker {

    protected int prevMonthRevenue;
    protected int currentMonthRevenue;
    protected int totalRevenue;

    RevenueTracker() {
        prevMonthRevenue = 0;
        currentMonthRevenue = 0;
        totalRevenue = 0;
    }

    public void addRevenue(int revenue) {
        currentMonthRevenue += revenue;
    }

    public void onNextMonth() {
        prevMonthRevenue = currentMonthRevenue;
        totalRevenue += prevMonthRevenue;
        currentMonthRevenue = 0;
    }

    public int getPrevMonthRevenue() {
        return prevMonthRevenue;
    }

    public int getCurrentMonthRevenue() {
        return currentMonthRevenue;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

}
