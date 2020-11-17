import java.util.HashMap;
public class Studio{

    private String shortName;
    private String longName;
    private int licensingFeeCollectedCurrentMonth;
    private int licensingFeeCollectedPreviousMonth;
    private int licensingFeeCollectedTotal;
    private HashMap<String, Event> nameEventMap;

    public Studio(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
        this.nameEventMap = new HashMap<String, Event>();
    }

    // Getters
    public String getShortName(){
        return this.shortName;
    }

    public String getLongName(){
        return this.longName;
    }

    public int getLicensingFeeCollectedCurrentMonth(){
        return this.licensingFeeCollectedCurrentMonth;
    }

    public int getLicensingFeeCollectedPreviousMonth(){
        return this.licensingFeeCollectedPreviousMonth;
    }

    public int getLicensingFeeCollectedTotal(){
        return this.licensingFeeCollectedTotal;
    }

    public Event getEvent(String nameYear){
        return this.nameEventMap.get(nameYear);
    }

    public HashMap<String, Event> getNameEventMap(){
        return this.nameEventMap;
    }

    // functions
    public void addEvent(String nameYear, Event event){
        this.nameEventMap.put(nameYear, event);
    }

    public void addLicensingFee(int licensingFee){
        this.licensingFeeCollectedCurrentMonth += licensingFee;
    }

    public void setLicensingFeesForNewMonth(){
        this.licensingFeeCollectedPreviousMonth = this.licensingFeeCollectedCurrentMonth;
        this.licensingFeeCollectedTotal += this.licensingFeeCollectedCurrentMonth;
        this.licensingFeeCollectedCurrentMonth = 0;
    }
}