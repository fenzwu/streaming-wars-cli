
import java.util.ArrayList;

public class StreamingService
{
    //variables
    private String shortName = "";
    private String longName = "";
    private int subPrice = 0;
    private int licensingFee;
    private int streamCurrentRevenue = 0;
    private int streamPreviousRevenue = 0;
    private int streamTotalRevenue = 0;

    //accessors. getters. setters.
    public String getShortName() { return this.shortName; }
    public void setShortName(String name) { this.shortName = name; }
    public String getLongName() { return this.longName; }
    public void setLongName(String name) { this.longName = name; }
    public int getSubPrice() { return this.subPrice; }
    public void setSubPrice(int num) { this.subPrice = num; }
    public int getLicensingMoviePPVFee() { return this.licensingFee; }
    public void setLicensingMoviePPVFee(int num) { this.licensingFee = num; }
    public int getStreamCurrentRevenue() { return this.streamCurrentRevenue; }
    public void setStreamCurrentRevenue(int num) { this.streamCurrentRevenue = num; }
    public int getStreamPreviousRevenue() { return this.streamPreviousRevenue; }
    public void setStreamPreviousRevenue(int num) { this.streamPreviousRevenue = num; }
    public int getStreamTotalRevenue() { return this.streamTotalRevenue; }
    public void setStreamTotalRevenue(int num) { this.streamTotalRevenue = num; }

    //constructor
    public StreamingService(){}

    //methods

    //display the stream service info based on the shortname
    public void displayStreamServiceInfo(String shortName, ArrayList<StreamingService> streamList)
    {
        if(streamList.size() == 0 || streamList.isEmpty())
        {
            return;
        }
        for(StreamingService stream: streamList)
        {
            if(stream.getShortName().contentEquals(shortName))
            {
                System.out.println("stream," + stream.getShortName() + "," + stream.getLongName());
                System.out.println("subscription," + stream.getSubPrice());
                System.out.println("current_period," + stream.getStreamCurrentRevenue());
                System.out.println("previous_period," + stream.getStreamPreviousRevenue());
                System.out.println("total," + stream.getStreamTotalRevenue());
                System.out.println("licensing," + stream.getLicensingMoviePPVFee());
            }
        }
    }

    //check if the stream service exists before adding
    public boolean checkIfStreamExists(String shortName, ArrayList<StreamingService> streamList)
    {
        boolean exists = false;

        if(streamList.size() > 0)//list contains at least one record
        {
            for(StreamingService stream: streamList)
            {
                if(stream.getShortName().contentEquals(shortName))
                {
                    exists = true;
                }
            }
        }
        return  exists;
    }

    public ArrayList<StreamingService> updateStreamRevenue(ArrayList<StreamingService> streamList)
    {
        //Update current, previous and total dollar amounts for each stream
        for(StreamingService stream: streamList)
        {
            stream.setStreamTotalRevenue(stream.getStreamTotalRevenue() + stream.getStreamCurrentRevenue());//total + current
            stream.setStreamPreviousRevenue(stream.getStreamCurrentRevenue());//set current to prev
            stream.setStreamCurrentRevenue(0);//reset
        }
        return streamList;
    }

    public void updateLicensingFee(ArrayList<StreamingService> streamList, String streamName, int payLicenseFee)
    {
        //loop through the streams list to find a matching stream service
        for(StreamingService stream:  streamList)
        {
            //match the stream name
            if(stream.getShortName().contentEquals(streamName))
            {//set the license fee for the stream for offering this event from the studio
                stream.setLicensingMoviePPVFee(stream.getLicensingMoviePPVFee() + payLicenseFee);
            }
        }
    }

}
