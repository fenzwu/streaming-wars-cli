
import java.util.ArrayList;

//PPV and movie event class combined into single class
public class Event
{
    //variables
    private EventType eventType = EventType.none;
    private String name = "";
    private int yearProduced = 0;
    private int duration = 0;
    private String nameOfPublisher = "";
    private int licensingFee = 0;

    //accessors. getters. setters
    public EventType getType() { return this.eventType; }
    public void setType(EventType type) { this.eventType = type; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public int getYearProduced() { return this.yearProduced; }
    public void setYearProduced(int num) { this.yearProduced = num; }
    public int getDuration() { return this.duration; }
    public void setDuration(int num) { this.duration = num; }
    public String getNameOfPublisher() { return this.nameOfPublisher; }
    public void setNameOfPublisher(String name) { this.nameOfPublisher = name; }
    public int getLicensingFee() { return this.licensingFee; }
    public void setLicensingFee(int num) { this.licensingFee = num; }

    //constructor
    public Event(){}

    //methods

    //display all events from all studios/publishers
    public void displayAllEvents(ArrayList<Event> eventsList)
    {
        if(eventsList.size() == 0 || eventsList.isEmpty())
        {
            return;
        }
        for(Event event: eventsList)
        {
            System.out.println(event.getType() + ","
                    + event.getName() + ","
                    + event.getYearProduced() + ","
                    + event.getDuration() + ","
                    + event.getNameOfPublisher() + ","
                    + event.getLicensingFee());
        }
    }

    //check if the movie name is unique before adding. combo of name and year
    public boolean checkIfEventNameUnique(String shortName,int yearProduced, ArrayList<Event> eventsList)
    {
        boolean isUnique = true;
        if(eventsList.size() > 0)//list contains at least one record
        {
            for(Event event: eventsList)
            {
                if(event.getName().contentEquals(shortName) &&
                        event.getYearProduced() == yearProduced &&
                        event.getType().toString() == "movie")
                {
                    isUnique = false;
                }
            }
        }
        return  isUnique;
    }

    //check if the movie or ppv exists before adding the offer
    public boolean checkIfMoviePPVExists(String shortName, ArrayList<Event> eventsList)
    {
        boolean exists = false;
        if(eventsList.size() > 0)//list contains at least one record
        {
            for(Event event: eventsList)
            {
                if(event.getName().contentEquals(shortName))
                {
                    exists = true;
                }
            }
        }
        return exists;
    }

    public void watchEvent(ArrayList<Demographic> demographicList,
                           String watchDemoGroup,
                           Demographic demographic,
                           int watchPercentage,
                           ArrayList<StreamingService> streamList,
                           String watchStream,
                           StreamingService stream,
                           ArrayList<Offers> offersList,
                           String watchEventName,
                           int watchEventYear,
                           int watchGroupStreams[][])
    {
        // Identify the demo group & the number of viewers affected
        int watchViewerCount = 0;
        int demoIndex = -1;
        int selectDemoNum = 0;

        //loop through the demo groups to look for matching demo
        for(Demographic demo: demographicList)
        {
            selectDemoNum++;
            if(demo.getShortName().contentEquals(watchDemoGroup))
            {//set the view count percentage for a demo group. Ex: 30% of age_40_50 watched Mulan
                watchViewerCount = demo.getNumOfAccts() * watchPercentage / 100;
                demoIndex = selectDemoNum;
                demographic = demo;//track this demo obj
            }
        }

        // Identify the streaming service & the subscription fee
        int watchSubscriptionFee = 0;
        int streamIndex = -1;
        int selectStreamNum = 0;

        //Identify the streaming service & the subscription fee
        for(StreamingService service: streamList)
        {
            selectStreamNum++;
            if(service.getShortName().contentEquals(watchStream))
            {
                watchSubscriptionFee = service.getSubPrice();
                streamIndex = selectStreamNum;
                stream = service;//track this object
            }
        }

        // Identify the event selected & the Pay-Per-View price
        // For all events: determine event type
        String watchType = "";
        int watchPayPerViewPrice = 0;

        //my code. Identify the event selected & the Pay-Per-View price from offers
        //For all events: determine event type
        for(Offers offer: offersList)
        {
            if(offer.getOfferStream().contentEquals(watchStream) &&
                    offer.getOfferEventName().contentEquals(watchEventName) &&
                    offer.getOfferEventYear() == watchEventYear)
            {
                watchType = offer.getOfferType();//offer type
                watchPayPerViewPrice = offer.getOfferEventPrice();//offer price
            }
        }

        int watchViewingCost = 0;

        if (watchType.equals("movie"))
        {//watchGroupStreams = demographic watches something from a particular stream.

            //For movies: identify the increased percentage of new customers and subscription fee
            if(watchViewerCount > watchGroupStreams[demoIndex][streamIndex])
            {
                watchViewingCost = (watchViewerCount - watchGroupStreams[demoIndex][streamIndex]) * watchSubscriptionFee;
                watchGroupStreams[demoIndex][streamIndex] = watchViewerCount;
            }
        }
        else if (watchType.equals("ppv"))
        {
            // For Pay-Per-Views: identify the demo viewers affected and event price
            watchViewingCost = watchViewerCount * watchPayPerViewPrice;
        }

        //Adjust funds for watching events
        //demo group that spends money to watch
        demographic.setDemoCurrentSpending(demographic.getDemoCurrentSpending() + watchViewingCost);//local obj

        //stream that gets the revenue
        stream.setStreamCurrentRevenue(stream.getStreamCurrentRevenue() + watchViewingCost);//local obj
    }

}
