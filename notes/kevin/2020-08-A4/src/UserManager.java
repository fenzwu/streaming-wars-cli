import java.util.LinkedHashMap;
import java.util.Map;

public class UserManager {

    private LinkedHashMap<String, Demographic> demographics;

    UserManager() {
        this.demographics = new LinkedHashMap<String,Demographic>();
    }

    public Demographic createDemographic(String shortName, String longName, int numAccounts) {

        //Ensure shortName is unique
        if (demographics.containsKey(shortName)) {
            return null;
        }

        Demographic demo = new Demographic(shortName, longName);
        demo.setNumAccounts(numAccounts);
        demographics.put(shortName, demo);
        return demo;
    }

    public Demographic getDemographic(String shortName) {
        return demographics.get(shortName);
    }

    public void notifyNextMonth() {
        for(Map.Entry<String, Demographic> entry : demographics.entrySet()) {
            entry.getValue().onNextMonth();
        }
    }

}
