import java.util.HashMap;
public class Service{

    private String shortName;
    private String longName;
    private int subscriptionFee;
    private int subscriptionFeeCollectedCurrentMonth;
    private int subscriptionFeeCollectedPreviousMonth;
    private int subscriptionFeeCollectedTotal;
    private int totalLicensingCost;
    private HashMap<String, Event> nameEventMap;


    public Service(String shortName, String longName, int subscriptionFee) {
        this.shortName = shortName;
        this.longName = longName;
        this.subscriptionFee = subscriptionFee;
        this.subscriptionFeeCollectedCurrentMonth = 0;
        this.subscriptionFeeCollectedPreviousMonth = 0;
        this.subscriptionFeeCollectedTotal = 0;
        this.totalLicensingCost = 0;
        this.nameEventMap = new HashMap<String, Event>();
    }

    // Getters
    public String getShortName(){
        return this.shortName;
    }

    public String getLongName(){
        return this.longName;
    }

    public int getSubscriptionFee(){
        return this.subscriptionFee;
    }

    public int getSubscriptionFeeCollectedCurrentMonth(){
        return this.subscriptionFeeCollectedCurrentMonth;
    }

    public int getSubscriptionFeeCollectedPreviousMonth(){
        return this.subscriptionFeeCollectedPreviousMonth;
    }

    public int getSubscriptionFeeCollectedTotal(){
        return this.subscriptionFeeCollectedTotal;
    }

    public int getTotalLicensingCost(){
        return this.totalLicensingCost;
    }

    public Event getEvent(String nameYear){
        return this.nameEventMap.get(nameYear);
    }

    // functions
    public void addEvent(String nameYear, Event event){
        this.nameEventMap.put(nameYear, event);
    }

    public void addLicensingCost(int licensingCost){
        this.totalLicensingCost += licensingCost;
    }

    public void addSubscriptionFeeCollectedCurrentMonth(int subscriptionFeeCollected){
        this.subscriptionFeeCollectedCurrentMonth += subscriptionFeeCollected;
    }
    
    public void clearOfferedEvents(){
        this.nameEventMap = new HashMap<String, Event>();
    }

    public void setSubscriptionFeesForNewMonth(){
        this.subscriptionFeeCollectedPreviousMonth = this.subscriptionFeeCollectedCurrentMonth;
        this.subscriptionFeeCollectedTotal += this.subscriptionFeeCollectedCurrentMonth;
        this.subscriptionFeeCollectedCurrentMonth = 0;
    }
}