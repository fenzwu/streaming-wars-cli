import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public interface IActions {

    void onCreateDemographic(String shortName, String longName, int numAccounts);

    void onCreateStudio(String shortName, String longName);

    void onCreateEvent(String type, String name, int year, int duration, String studio, int licenseFee);

    void onCreateService(String shortName, String longName, int subscriptionPrice);

    void onCreateUser(String username, String password, String role);

    void onOfferMovie(String serviceName, String movieName, int year);

    void onOfferPPV(String serviceName, String ppvName, int year, int price);

    void onWatchEvent(String demoName, int percentage, String serviceName, String eventName, int year);

    void onUpdateDemo(String shortName, String longName, int numOfAccounts);

    void onUpdateService(String shortName, String longName, int subscriptionPrice);

    void onUpdateEvent(String name, int year, int duration, int licenseFee);

    void onRetractMovie(String serviceName, String movieName, int year);

    void onNextMonth();

    HashMap<String, String> onDisplayDemo(String shortName);

    HashMap<String, String> onDisplayStream(String shortName);

    HashMap<String, String> onDisplayStudio(String shortName);

    ArrayList<HashMap<String, String>> onDisplayEvents();

    ArrayList<HashMap<String, String>> onDisplayOffers();

    Calendar onDisplayTime();

    ArrayList<HashMap<String, String>> onDisplayLogs();

    void onLogin(String username, String password);

    void onLogout();

    HashMap<String, String> onDisplayActiveUser();

    void onChangePassword(String newPassword);

    void onChangeRole(String username, String newRole);

    void onClearLogs();
}
