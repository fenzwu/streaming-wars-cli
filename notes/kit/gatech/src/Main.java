import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
//created by kit sayasy 10/09/2020
public class Main
{
    ///////declare global variables
    //demographics
    static ArrayList<Demographic> demographicList = new ArrayList<>();

    //studio/publisher
    static ArrayList<Publisher> publisherList = new ArrayList<>();

    //events
    static ArrayList<Event> eventsList = new ArrayList<>();

    //Stream service
    static ArrayList<StreamingService> streamList = new ArrayList<>();

    //offers
    static ArrayList<Offers> offersList = new ArrayList<>();

    //demographic
    private static final int LIMIT_DEMOS = 20;

    //stream
    private static final int LIMIT_STREAMS = 20;
    private static int watchGroupStreams[][] = new int[1000][1000];

    private static int monthTimeStamp = 10;
    private static int yearTimeStamp = 2020;
    Timer timer = new Timer();

    //process the instructions passed in
    public static void processInstructions(Boolean verboseMode)
    {
        //parse the input args
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true)
        {
            try
            {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);//split the command
                System.out.println("> " + wholeInputLine);//the whole line

                if (tokens[0].equals("create_demo"))
                {
                    if (verboseMode)
                    {
                        System.out.println("create_demo_acknowledged");
                    }
                    //create the demo. set the properties
                    Demographic demographic = new Demographic();
                    demographic.setShortName(tokens[1].trim());
                    demographic.setLongName(tokens[2].trim());
                    demographic.setNumOfAccts(Integer.parseInt(tokens[3]));

                    //check if name already exists
                    boolean exists = demographic.checkIfDemoExists(demographic.getShortName(), demographicList);

                    if(exists)
                    {//leave command it demo exists
                        continue;
                    }
                    //add the demo to the list of demos to track
                    demographicList.add(demographic);
                }
                else if (tokens[0].equals("create_studio"))
                {
                    if (verboseMode)
                    {
                        System.out.println("create_studio_acknowledged");
                    }
                    //create the publisher. set the properties
                    Publisher publisher = new Publisher();
                    publisher.setShortName(tokens[1].trim());
                    publisher.setLongName(tokens[2].trim());

                    //check if name already exists
                    boolean exists = publisher.checkIfPublisherExists(publisher.getShortName(), publisherList);

                    if(exists)
                    {//cancel the command if studio/publisher exists
                        continue;
                    }
                    //add the publisher to the list of publishers to track
                    publisherList.add(publisher);
                }
                else if (tokens[0].equals("create_event"))
                {
                    if (verboseMode)
                    {
                        System.out.println("create_event_acknowledged");
                    }
                    //create the event. set the properties
                    Event event = new Event();

                    //set the event type and other properties
                    if(tokens[1].contentEquals(EventType.movie.toString()))
                    {
                        event.setType(EventType.movie);
                    }
                    else if(tokens[1].contentEquals(EventType.ppv.toString()))
                    {
                        event.setType(EventType.ppv);
                    }
                    else
                    {
                        //cancel the command if event type is invalid
                        continue;
                    }
                    event.setName(tokens[2].trim());
                    event.setYearProduced(Integer.parseInt(tokens[3]));
                    event.setDuration(Integer.parseInt(tokens[4]));
                    event.setNameOfPublisher(tokens[5]);

                    Publisher publisher = new Publisher();

                    //check if movie was produced by a valid studio/publisher
                    boolean result = publisher.checkIfPublisherExists(event.getNameOfPublisher(), publisherList);

                    if(!result)
                    {
                        //cancel the command if the publisher/studio doesn't exist
                        continue;
                    }
                    //check if the event name is unique
                    result = event.checkIfEventNameUnique(event.getName(),event.getYearProduced(), eventsList);

                    if(!result)
                    {
                        //cancel the command if the event name isn't unique
                        continue;
                    }

                    event.setLicensingFee(Integer.parseInt(tokens[6]));

                    //add the event to the list of events to track
                    eventsList.add(event);
                }
                else if (tokens[0].equals("create_stream"))
                {
                    if (verboseMode)
                    {
                        System.out.println("create_stream_acknowledged");
                    }
                    //create the streamingService and set the properties
                    StreamingService streamingService = new StreamingService();
                    streamingService.setShortName(tokens[1].trim());
                    streamingService.setLongName(tokens[2].trim());
                    streamingService.setSubPrice(Integer.parseInt(tokens[3]));

                    //add the streamingService to the list of streamingService to track
                    streamList.add(streamingService);
                }
                else if (tokens[0].startsWith("offer") && (tokens[0].equals("offer_movie") || tokens[0].equals("offer_ppv")))
                {
                    if (verboseMode)
                    {
                        System.out.println(tokens[0] + "_acknowledged");
                    }
                    //create the offer. parse the args
                    Offers offers = new Offers();
                    offers.setOfferType(tokens[0].substring(6));//movie or ppv
                    offers.setOfferStream(tokens[1]);//stream service
                    offers.setOfferEventName(tokens[2]);//name of event
                    offers.setOfferEventYear(Integer.parseInt(tokens[3]));//year produced

                    //check if the stream service is valid
                    StreamingService streamingService = new StreamingService();
                    boolean result = streamingService.checkIfStreamExists(offers.getOfferStream(), streamList);

                    if(!result)
                    {
                        //cancel the command the stream doesn't exists
                        continue;
                    }
                    //check if the movie/ppv exists before offering it
                    Event eventObj = new Event();
                    result = eventObj.checkIfMoviePPVExists(offers.getOfferEventName(), eventsList);

                    if(!result)
                    {//cancel the command the movie doesn't exists
                        continue;
                    }
                    //check if this is a ppv to offer it for a price
                    if (offers.getOfferType().contentEquals("ppv"))
                    {
                        offers.setOfferEventPrice(Integer.parseInt(tokens[4]));
                    }
                    //add the new offer to the list of offers.
                    offersList.add(offers);

                    //The streaming service must license the event from the studio
                    String payStudio = "";
                    int payLicenseFee = 0;

                    //loop through the events list to look for matching studio to pay
                    for(Event event: eventsList)
                    {
                        //match movie/ppv name that was created from create_event with the offered event AND
                        //match the year the event was produced
                        if(event.getName().contentEquals(tokens[2]) && event.getYearProduced() == Integer.parseInt(tokens[3]))
                        {
                            payStudio = event.getNameOfPublisher();//set the studio name to pay
                            payLicenseFee = event.getLicensingFee();//set the license fee
                        }
                    }
                    //update the fees and revenue for the stream and studio/publisher
                    String streamName = tokens[1];
                    streamingService.updateLicensingFee(streamList, streamName, payLicenseFee);

                    Publisher pub = new Publisher();
                    pub.setPublisherCurrentRevenue(publisherList, payStudio, payLicenseFee);
                }
                else if (tokens[0].equals("watch_event"))
                {
                    if (verboseMode)
                    {
                        System.out.println("watch_event_acknowledged");
                    }
                    //parse args
                    String watchDemoGroup = tokens[1];
                    int watchPercentage = Integer.parseInt(tokens[2]);
                    String watchStream = tokens[3];
                    String watchEventName = tokens[4];
                    int watchEventYear = Integer.parseInt(tokens[5]);

                    //check if the demo is valid.
                    Demographic demographic = new Demographic();
                    boolean exists = demographic.checkIfDemoExists(watchDemoGroup, demographicList);

                    if(!exists)
                    {//cancel the command since demo isn't valid
                        continue;
                    }
                    //check if stream is valid
                    StreamingService stream = new StreamingService();
                    exists = stream.checkIfStreamExists(watchStream, streamList);

                    if(!exists)
                    {//cancel the command since stream isn't valid
                        continue;
                    }
                    //check if the movie/ppv exists is offered. it can't be offered if it doesn't exist
                    Offers offerObj = new Offers();
                    exists = offerObj.checkIfEventOffered(watchEventName, offersList);

                    if(!exists)
                    {
                        //cancel the command since offer doesn't exist
                        continue;
                    }
                    Event event = new Event();
                    event.watchEvent(demographicList,watchDemoGroup, demographic, watchPercentage,
                            streamList, watchStream, stream, offersList, watchEventName, watchEventYear, watchGroupStreams);
                }
                else if (tokens[0].equals("next_month"))
                {
                    if (verboseMode)
                    {
                        System.out.println("next_month_acknowledged");
                    }
                    // Update the current timestamp.
                    if (monthTimeStamp == 12)
                    {
                        yearTimeStamp++;
                    }
                    monthTimeStamp = (monthTimeStamp % 12) + 1;

                    //Update current, previous and total dollar amounts for each demo
                    Demographic demo = new Demographic();
                    demographicList = demo.updateDemoSpending(demographicList);

                    //Update current, previous and total dollar amounts for each stream
                    StreamingService stream = new StreamingService();
                    streamList = stream.updateStreamRevenue(streamList);

                    //Update current, previous and total dollar amounts for each studio/publisher
                    Publisher publisher = new Publisher();
                    publisherList = publisher.updatePublisherRevenue(publisherList);

                    //Remove all movie and Pay-Per-View offerings. reset
                    offersList.clear();

                    // Reset the subscription counts for the month. keep
                    watchGroupStreams = new int[LIMIT_DEMOS][LIMIT_STREAMS];
                }
                else if (tokens[0].equals("display_demo"))
                {
                    if (verboseMode)
                    {
                        System.out.println("display_demo_acknowledged");
                    }
                    //look for the matching demo record
                    Demographic demographic = new Demographic();
                    demographic.displayDemoInfo(tokens[1].trim(), demographicList);
                }
                else if (tokens[0].equals("display_events"))
                {

                    if (verboseMode)
                    {
                        System.out.println("display_events_acknowledged");
                    }
                    //display all events for current month
                    Event event = new Event();
                    event.displayAllEvents(eventsList);
                }
                else if (tokens[0].equals("display_stream"))
                {
                    if (verboseMode)
                    {
                        System.out.println("display_stream_acknowledged");
                    }
                    //look for matching stream
                    StreamingService streamingService  = new StreamingService();
                    streamingService.displayStreamServiceInfo(tokens[1].trim(), streamList);
                }
                else if (tokens[0].equals("display_studio"))
                {
                    if (verboseMode)
                    {
                        System.out.println("display_studio_acknowledged");
                    }
                    //look for the matching publisher record
                    Publisher publisher = new Publisher();
                    publisher.displayPublisherInfo(tokens[1].trim(), publisherList);
                }
                else if (tokens[0].equals("display_offers"))
                {
                    if (verboseMode)
                    {
                        System.out.println("display_offers_acknowledged");
                    }
                    //display offers for the current month
                    Offers offers = new Offers();
                    offers.displayOffers(offersList);
                }
                else if (tokens[0].equals("display_time"))
                {
                    if (verboseMode)
                    {
                        System.out.println("display_time_acknowledged");
                    }
                    System.out.println("time," + monthTimeStamp + "," + yearTimeStamp);
                }
                else if (tokens[0].equals("stop"))
                {
                    break;
                }
                else
                {
                    if (verboseMode)
                    {
                        System.out.println("command_" + tokens[0] + "_NOT_acknowledged");
                    }
                }
            }//end try
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println();
            }
        }//end while

        if (verboseMode)
        {
            System.out.println("stop_acknowledged");
        }
        commandLineInput.close();
    }

    public static void main(String[] args)
    {
        Boolean showState = Boolean.FALSE;

        //determine to use verbose logging
        if (args.length >= 2 && (args[1].equals("-v") || args[1].equals("-verbose")))
        {
            showState = Boolean.TRUE;
        }
        //process the instructions
        processInstructions(showState);
    }
}
