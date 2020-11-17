import java.util.ArrayList;

public class Studio {
    public String short_name;
    public String long_name;
    public ArrayList<Movie> movies = new ArrayList<Movie>();
    public ArrayList<PayPerView> payPerViews = new ArrayList<PayPerView>();

    private int currentMonthRevenue = 0;
    private int previousMonthRevenue = 0;
    private int totalRevenue = 0;

    public Studio(String short_name, String long_name) {
        this.short_name = short_name;
        this.long_name = long_name;
    }

    public void licenseEvent(Event event) {
        currentMonthRevenue += event.license_fee;
    }

    public void advanceMonth() {
        previousMonthRevenue = currentMonthRevenue;
        totalRevenue += currentMonthRevenue;
        currentMonthRevenue = 0;
    }

    public void print() {
        System.out.println("studio," + short_name + "," + long_name);
        System.out.println("current_period," + currentMonthRevenue);
        System.out.println("previous_period," + previousMonthRevenue);
        System.out.println("total," + totalRevenue);
    }
}