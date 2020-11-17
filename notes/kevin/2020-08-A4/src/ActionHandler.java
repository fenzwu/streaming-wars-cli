import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActionHandler {

    private UserManager userManager;
    private ContentProvider contentProvider;
    private ViewMediator viewMediator;
    private Calendar calendar;

    ActionHandler() {
        userManager = new UserManager();
        contentProvider = new ContentProvider();
        viewMediator = new ViewMediator(userManager, contentProvider);
        calendar = Calendar.getInstance();
        calendar.set(2020, 9, 1);
    }

    public void onCreateDemographic(String shortName, String longName, int numAccounts) {
        userManager.createDemographic(shortName, longName, numAccounts);
    }

    public void onCreateStudio(String shortName, String longName) {
        contentProvider.createStudio(shortName, longName);
    }

    public void onCreateEvent(String type, String name, int year, int duration, String studio, int licenseFee) {
        contentProvider.createEvent(type, name, year, duration, studio, licenseFee);
    }

    public void onCreateService(String shortName, String longName, int subscriptionPrice) {
        contentProvider.createService(shortName, longName, subscriptionPrice);
    }

    public void onOfferMovie(String serviceName, String movieName, int year) {
        contentProvider.offerMovie(serviceName, movieName, year);
    }

    public void onOfferPPV(String serviceName, String ppvName, int year, int price) {
        contentProvider.offerPPV(serviceName, ppvName, year, price);
    }

    public void onWatchEvent(String demoName, int percentage, String serviceName, String eventName, int year) {
        viewMediator.viewEvent(demoName, percentage, serviceName, eventName, year);
    }

    public void onNextMonth() {
        calendar.add(Calendar.MONTH, 1);
        userManager.notifyNextMonth();
        contentProvider.notifyNextMonth();
    }

    public HashMap<String, String> onDisplayDemo(String shortName) {
        Demographic demo = userManager.getDemographic(shortName);

        if (demo == null) {
            return null;
        }

        return demo.getDemoInfo();
    }

    public HashMap<String, String> onDisplayStream(String shortName) {
        Service service = contentProvider.getService(shortName);

        if(service == null) {
            return null;
        }

        return service.getServiceInfo();
    }

    public HashMap<String, String> onDisplayStudio(String shortName) {
        Studio studio = contentProvider.getStudio(shortName);

        if(studio == null) {
            return null;
        }

        return studio.getStudioInfo();
    }

    public ArrayList<HashMap<String, String>> onDisplayEvents() {
        return contentProvider.getAllEventsInfo();
    }

    public ArrayList<HashMap<String, String>> onDisplayOffers() {
        return contentProvider.getOffers();
    }

    public Calendar onDisplayTime() {
        return calendar;
    }

}
