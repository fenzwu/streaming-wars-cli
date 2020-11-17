import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActionHandler implements IActions {

    private DemographicManager demographicManager;
    private ContentProvider contentProvider;
    private ViewMediator viewMediator;
    private Calendar calendar;
    private Authenticator authenticator;
    private Logger logger;

    ActionHandler() {
        demographicManager = new DemographicManager();
        contentProvider = new ContentProvider();
        viewMediator = new ViewMediator(demographicManager, contentProvider);
        calendar = Calendar.getInstance();
        calendar.set(2020, 9, 1);
        authenticator = new Authenticator();
        logger = new Logger();
    }

    public void onCreateDemographic(String shortName, String longName, int numAccounts) {
        demographicManager.createDemographic(shortName, longName, numAccounts);
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

    public void onCreateUser(String username, String password, String role) {
        authenticator.createUser(username, password, role);
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
        demographicManager.notifyNextMonth();
        contentProvider.notifyNextMonth();
    }

    public HashMap<String, String> onDisplayDemo(String shortName) {
        Demographic demo = demographicManager.getDemographic(shortName);

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

    public void onUpdateDemo(String shortName, String longName, int numOfAccounts) {
        Demographic demo = demographicManager.getDemographic(shortName);

        // If the name (shortName) of the Demographic Group doesn't exist, the command is considered invalid.
        if (demo == null) {
            return;
        }

        demographicManager.updateDemographic(demo, longName, numOfAccounts);
    }

    public void onUpdateService(String shortName, String longName, int subscriptionPrice) {
        Service service = contentProvider.getService(shortName);

        // If the name (shortName) of the Streaming Service doesn't exist, the command is considered invalid.
        if (service == null) {
            return;
        }

        contentProvider.updateService(service, longName, subscriptionPrice);
    }

    public void onUpdateEvent(String name, int year, int duration, int licenseFee) {
        Event event = contentProvider.getEvent(name, year);

        // If the name (shortName) of the event doesn't exist, the command is considered invalid.
        if (event == null) {
            return;
        }

        contentProvider.updateEvent(event, duration, licenseFee);
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

    public ArrayList<HashMap<String, String>> onDisplayLogs() { return logger.getLogs(); }

    public void onLogin(String username, String password) {
        authenticator.login(username, password);
    }

    public void onLogout() {
        authenticator.logout();
    }

    public HashMap<String, String> onDisplayActiveUser() {
        return authenticator.getActiveUserInfo();
    }

    public void onChangePassword(String newPassword) {
        authenticator.changePassword(newPassword);
    }

    public void onChangeRole(String username, String newRole) {
        authenticator.changeRole(username, newRole);
    }

    public void onClearLogs() {
        logger.clearLogs();
    }
    
    public void onRetractMovie(String serviceName, String movieName, int year) {
        contentProvider.retractMovie(serviceName, movieName, year);
    }

    public void log(String user, String message) {
        logger.log(user, message);
    }

}
