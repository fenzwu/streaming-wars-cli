
import java.util.ArrayList;

public class Demographic
{
    //variables
    private String shortName = "";
    private String longName = "";
    private int numOfAccountsForADemo = 0;
    private int demoCurrentSpending = 0;
    private int demoPreviousSpending = 0;
    private int demoTotalSpending = 0;

    //accessors. getters. setters
    public String getShortName() { return this.shortName; }
    public void setShortName(String name) { this.shortName = name; }

    public String getLongName() { return this.longName; }
    public void setLongName(String name) { this.longName = name; }

    public int getNumOfAccts() { return this.numOfAccountsForADemo; }
    public void setNumOfAccts(int num) { this.numOfAccountsForADemo = num; }

    public int getDemoCurrentSpending() { return this.demoCurrentSpending; }
    public void setDemoCurrentSpending(int num) { this.demoCurrentSpending = num; }

    public int getDemoPreviousSpending() { return this.demoPreviousSpending; }
    public void setDemoPreviousSpending(int num) { this.demoPreviousSpending = num; }

    public int getDemoTotalSpending() { return this.demoTotalSpending; }
    public void setDemoTotalSpending(int num) { this.demoTotalSpending = num; }

    //default constructor
    public Demographic(){}

    //methods
    //display the demo info based on the shortname
    public void displayDemoInfo(String shortName, ArrayList<Demographic> demoList)
    {
        if(demoList.size() == 0 || demoList.isEmpty())
        {
            return;
        }

        for(Demographic demo: demoList)
        {
            if(demo.getShortName().contentEquals(shortName))
            {
                System.out.println("demo," + demo.getShortName() + "," + demo.getLongName());
                System.out.println("size," + demo.getNumOfAccts());
                System.out.println("current_period," + demo.getDemoCurrentSpending());
                System.out.println("previous_period," + demo.getDemoPreviousSpending());
                System.out.println("total," + demo.getDemoTotalSpending());
            }
        }
    }

    //check if the demo group exists before adding
    public boolean checkIfDemoExists(String shortName, ArrayList<Demographic> demoList)
    {
        boolean exists = false;
        if(demoList.size() > 0)//list contains at least one record
        {
            for(Demographic demo: demoList)
            {
                if(demo.getShortName().contentEquals(shortName))
                {
                    exists = true;
                }
            }
        }
        return  exists;
    }

    public ArrayList<Demographic> updateDemoSpending(ArrayList<Demographic> demoList)
    {
        //Update current, previous and total dollar amounts for each demo
        for(Demographic demo: demoList)
        {
            demo.setDemoTotalSpending(demo.getDemoTotalSpending() + demo.getDemoCurrentSpending());//total + current
            demo.setDemoPreviousSpending(demo.getDemoCurrentSpending());//set current to prev
            demo.setDemoCurrentSpending(0);//reset
        }
        return demoList;
    }

}
