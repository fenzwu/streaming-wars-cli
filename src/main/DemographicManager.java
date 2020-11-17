import java.util.LinkedHashMap;
import java.util.Map;

public class DemographicManager {

    private LinkedHashMap<String, Demographic> demographics;

    DemographicManager() {
        this.demographics = new LinkedHashMap<String,Demographic>();
    }

    public Demographic createDemographic(String shortName, String longName, int numAccounts) {

        // Ensure shortName is unique.
        if (demographics.containsKey(shortName)) {
            return null;
        }

        Demographic demo = new Demographic(shortName, longName);
        demo.setNumAccounts(numAccounts);
        demographics.put(shortName, demo);
        return demo;
    }

    public void updateDemographic(Demographic demo, String longName, int numAccounts) {

        // Update longName and numAccounts attributes. There is no validation if the values provided by the user are
        // different than the current values, because replacing values with the same has no harmful side effects.
        demo.setLongName(longName);

        // Only allow the number of accounts in a demographic group to be modified if that demographic group
        // has no accessed or viewed any events within the current time period.
        if (!demo.getHasViewedEvent()){
            demo.setNumAccounts(numAccounts);
        }
        demographics.replace(demo.getShortName(), demo);
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
