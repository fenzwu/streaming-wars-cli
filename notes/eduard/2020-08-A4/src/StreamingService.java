import java.util.ArrayList;

public final class StreamingService {
    public String short_name;
    public String long_name;
    public int subscription_price;

    private int currentMonthRevenue = 0;
    private int previousMonthRevenue = 0;
    private int totalRevenue = 0;

    private int totalLicensingCost = 0;

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<PayPerView> payPerViews = new ArrayList<PayPerView>();

    public ArrayList<Movie> getMovies() {
        return movies;
    }
    public ArrayList<PayPerView> getPayPerViews() {
        return payPerViews;
    }

    public StreamingService(String short_name, String long_name, int subscription_price) {
        this.short_name = short_name;
        this.long_name = long_name;
        this.subscription_price = subscription_price;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        totalLicensingCost += movie.license_fee;
    }

    public void addPayPerview(PayPerView payPerView) {
        payPerViews.add(payPerView);
        totalLicensingCost += payPerView.license_fee;
    }

    public void addSubscribers(int newSubscribers) {
        currentMonthRevenue += newSubscribers * subscription_price;
    }

    public void addPPVRevenue(int ppvRevenue) {
        currentMonthRevenue += ppvRevenue;
    }

    public void advanceMonth() {
        previousMonthRevenue = currentMonthRevenue;
        totalRevenue += currentMonthRevenue;
        currentMonthRevenue = 0;

        movies.clear();
        payPerViews.clear();
    }

    public void print() {
        System.out.println("stream," + short_name + "," + long_name);
        System.out.println("subscription," + subscription_price);
        System.out.println("current_period," + currentMonthRevenue);
        System.out.println("previous_period," + previousMonthRevenue);
        System.out.println("total," + totalRevenue);
        System.out.println("licensing," + totalLicensingCost);
    }
}
