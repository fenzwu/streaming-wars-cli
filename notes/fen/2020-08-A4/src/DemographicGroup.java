import java.util.HashMap;
public class DemographicGroup{

    private String shortName;
    private String longName;
    private HashMap<String,Integer> serviceNumAccountsMap;
    private int numAccounts;
    private int moneySpentCurrentMonth;
    private int moneySpentPreviousMonth;
    private int moneySpentTotal;


    public DemographicGroup(String shortName, String longName, int numAccounts) {
        this.shortName = shortName;
        this.longName = longName;
        this.serviceNumAccountsMap = new HashMap<String,Integer>();
        this.numAccounts = numAccounts;
        this.moneySpentCurrentMonth = 0;
        this.moneySpentPreviousMonth = 0;
        this.moneySpentTotal= 0;
    }

    // Getters
    public String getShortName(){
        return this.shortName;
    }

    public String getLongName(){
        return this.longName;
    }

    public int getNumAccounts(){
        return this.numAccounts;
    }

    public HashMap<String,Integer> getServiceNumAccountsMap(){
        return this.serviceNumAccountsMap;
    }

    public int getMoneySpentCurrentMonth(){
        return this.moneySpentCurrentMonth;
    }

    public int getMoneySpentPreviousMonth(){
        return this.moneySpentPreviousMonth;
    }

    public int getMoneySpentTotal(){
        return this.moneySpentTotal;
    }
    
    // functions
    public void addMoneySpentCurrentMonth(int newSpending){
        this.moneySpentCurrentMonth += newSpending;
    }

    public void setMoneySpentCurrentMonthToZero(){
        this.moneySpentCurrentMonth = 0;
    }

    public void setMoneySpentPreviousMonth(){
        this.moneySpentPreviousMonth = this.moneySpentCurrentMonth;
    }

    public void addMoneySpentTotal(){
        this.moneySpentTotal += this.moneySpentCurrentMonth;
    }

    public void setNumAccountsWatchedToZero(){
        this.serviceNumAccountsMap = new HashMap<String,Integer>();
    }
}