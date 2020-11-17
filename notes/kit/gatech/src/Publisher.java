
import java.util.ArrayList;

public class Publisher
{
    //variables
    private String shortName = "";
    private String longName = "";
    private int currentRevenue = 0;
    private int previousRevenue = 0;
    private int totalLicenseRevenue = 0;

    //accessors. getters. setters
    public String getShortName() { return this.shortName; }
    public void setShortName(String name) { this.shortName = name; }
    public String getLongName() { return this.longName; }
    public void setLongName(String name) { this.longName = name; }
    public int getCurrentRevenue() { return this.currentRevenue; }
    public void setCurrentRevenue(int num) { this.currentRevenue = num; }
    public int getPreviousRevenue() { return this.previousRevenue; }
    public void setPreviousRevenue(int num) { this.previousRevenue = num; }
    public int getTotalLicenseRevenue() { return this.totalLicenseRevenue; }
    public void setTotalLicenseRevenue(int num) { this.totalLicenseRevenue = num; }

    //constructor
    public Publisher(){}

    //display the studio/publisher info from the shortname
    public void displayPublisherInfo(String shortName, ArrayList<Publisher> pubList)
    {
        if(pubList.size() == 0 || pubList.isEmpty())
        {
            return;
        }

        for(Publisher pub: pubList)
        {
            if(pub.getShortName().contentEquals(shortName))
            {
                System.out.println("studio," + pub.getShortName() + "," + pub.getLongName());
                System.out.println("current_period," + pub.getCurrentRevenue());
                System.out.println("previous_period," + pub.getPreviousRevenue());
                System.out.println("total," + pub.getTotalLicenseRevenue());
            }
        }
    }

    //check if the studio or publisher exists before adding
    public boolean checkIfPublisherExists(String shortName, ArrayList<Publisher> pubList)
    {
        boolean exists = false;
        if(pubList.size() > 0)//list contains at least one record
        {
            for(Publisher pub: pubList)
            {
                if(pub.getShortName().contentEquals(shortName))
                {
                    exists = true;
                }
            }
        }
        return  exists;
    }

    public ArrayList<Publisher> updatePublisherRevenue(ArrayList<Publisher> publisherList)
    {
        //Update current, previous and total dollar amounts for each studio/publisher
        for(Publisher pub: publisherList)
        {
            pub.setTotalLicenseRevenue(pub.getTotalLicenseRevenue() + pub.getCurrentRevenue());//total + current
            pub.setPreviousRevenue(pub.getCurrentRevenue());//set current to prev
            pub.setCurrentRevenue(0);//reset
        }
        return publisherList;
    }

    public void setPublisherCurrentRevenue(ArrayList<Publisher> publisherList, String payStudio, int payLicenseFee)
    {
        //loop through the studio/publisher list to find a match
        for(Publisher publisher: publisherList)
        {
            //studio/publisher collects the revenue/license fee for current month
            if(publisher.getShortName().contentEquals(payStudio))
            {
                publisher.setCurrentRevenue(publisher.getCurrentRevenue() + payLicenseFee);
            }
        }
    }

}
