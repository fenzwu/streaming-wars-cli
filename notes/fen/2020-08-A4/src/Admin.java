import java.util.*;

public class Admin {
    private HashMap<String, DemographicGroup> nameDemoMap;
    private HashMap<String, Studio> nameStudioMap;
    private HashMap<String, Service> nameServiceMap;

    private HashMap<String, Studio> eventStudioMap;
    private HashMap<String, Service> eventServiceMap;

    private ArrayList<Event> eventList;
    private ArrayList<Offer> offerList;

    private int monthTimeStamp;
    private int yearTimeStamp;

    public Admin() {
        this.nameDemoMap = new HashMap<String, DemographicGroup>();
        this.nameStudioMap = new HashMap<String, Studio>();
        this.nameServiceMap = new HashMap<String, Service>();

        this.eventStudioMap = new HashMap<String, Studio>();
        this.eventServiceMap = new HashMap<String, Service>();

        this.eventList = new ArrayList<Event>();
        this.offerList = new ArrayList<Offer>();

        this.monthTimeStamp = 10;
        this.yearTimeStamp = 2020;
    }

    public void handleTransactionBetweenStudioAndService(int transactionAmount, Studio studio, Service service){
        // The service pays licensing fee to studio
        service.addLicensingCost(transactionAmount);

        // The studio updates its own licensing fee
        studio.addLicensingFee(transactionAmount);
    }

    public void handleTransactionBetweenDemoAndService(int transactionAmount, DemographicGroup demo, Service service){
        demo.addMoneySpentCurrentMonth(transactionAmount);
        service.addSubscriptionFeeCollectedCurrentMonth(transactionAmount);
    }

    public void processInstructions(Boolean verboseMode) {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                // creates a demographic group
                if (tokens[0].equals("create_demo")) {
                    DemographicGroup demo = new DemographicGroup(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    this.nameDemoMap.put(tokens[1], demo);

                // creates a studio
                } else if (tokens[0].equals("create_studio")) {
                    Studio studio = new Studio(tokens[1], tokens[2]);
                    this.nameStudioMap.put(tokens[1], studio);

                // creates an event, add events to eventStudioMap
                } else if (tokens[0].equals("create_event")) {
                    Event event = new Event(tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[5], Integer.parseInt(tokens[6]));
                    Studio studio = nameStudioMap.get(tokens[5]);
                    studio.addEvent(tokens[2] + tokens[3], event);
                    eventStudioMap.put(tokens[2] + tokens[3], studio);
                    this.eventList.add(event);

                } else if (tokens[0].equals("create_stream")) {
                    Service service = new Service(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    this.nameServiceMap.put(tokens[1], service);

                } else if (tokens[0].equals("offer_movie") || tokens[0].equals("offer_ppv")) {
                    

                    // gets event from studio
                    Studio studio = this.eventStudioMap.get(tokens[2] + tokens[3]);
                    Event ev = studio.getEvent(tokens[2] + tokens[3]);
                    Event event = new Event(ev.getType(), ev.getName(), ev.getYearProduced(), ev.getDuration(), ev.getStudio(), ev.getLicenseFee());
                    if (tokens[0].substring(6).equals("ppv")) {
                        event.setOneTimeFee(Integer.parseInt(tokens[4]));
                    }

                    // add event to service
                    Service service = this.nameServiceMap.get(tokens[1]);
                    service.addEvent(tokens[2] + tokens[3], event);

                    // transaction between studio and service
                    handleTransactionBetweenStudioAndService(event.getLicenseFee(), studio, service);

                    // add event and service to eventServiceMap
                    this.eventServiceMap.put(tokens[2] + tokens[3], service);

                    // add offer to offerlist
                    Offer offer = new Offer(tokens[1], tokens[0].substring(6), tokens[2], Integer.parseInt(tokens[3]));
                    if (tokens[0].substring(6).equals("ppv")) {
                        offer.setOneTimeFee(Integer.parseInt(tokens[4]));
                        offerList.add(offer);
                    }else{
                        offerList.add(offer);
                    }
                } else if (tokens[0].equals("watch_event")) {
                    int watchPercentage = Integer.parseInt(tokens[2]);

                    // Identify the demo group & the number of viewers affected
                    DemographicGroup demo = this.nameDemoMap.get(tokens[1]);
                    int watchViewerCount = demo.getNumAccounts() * watchPercentage / 100;
                    
                    // Identify the streaming service & the subscription fee
                    Service service = this.nameServiceMap.get(tokens[3]);
                    int watchSubscriptionFee = service.getSubscriptionFee();

                    // Identify the event selected & the Pay-Per-View price
                    // For all events: determine event type
                    Event event = service.getEvent(tokens[4] + tokens[5]);
                    String watchType = event.getType();
                    int watchPayPerViewPrice = event.getOneTimeFee();
                    
                    // check if service is in demo else create one mapping
                    HashMap<String,Integer> serviceNumAccountsMap = demo.getServiceNumAccountsMap();
                    if (!serviceNumAccountsMap.containsKey(tokens[3]))
                        serviceNumAccountsMap.put(tokens[3], 0);

                    int watchViewingCost = 0;
                    if (watchType.equals("movie")) {
                        // For movies: identify the increased percentage of new customers and subscription fee
                        if (watchViewerCount > serviceNumAccountsMap.get(tokens[3]))
                            watchViewingCost = (watchViewerCount - serviceNumAccountsMap.get(tokens[3])) * service.getSubscriptionFee();
                            // update mapping here
                            serviceNumAccountsMap.put(tokens[3], watchViewerCount);
                    }else{
                        // For Pay-Per-Views: identify the demo viewers affected and event price
                        watchViewingCost = watchPayPerViewPrice * watchViewerCount;
                    }

                    // Adjust funds for watching events
                    handleTransactionBetweenDemoAndService(watchViewingCost, demo, service);

                } else if (tokens[0].equals("next_month")) {
                    // Update the current timestamp
                    if (monthTimeStamp == 12) { yearTimeStamp++; }
                    monthTimeStamp = (monthTimeStamp % 12) + 1;

                    // Update current, previous and total dollar amounts for Demo and reset the subscription counts for the month
                    for (Map.Entry<String, DemographicGroup> set : nameDemoMap.entrySet()) {
                        set.getValue().setMoneySpentPreviousMonth();
                        set.getValue().addMoneySpentTotal();
                        set.getValue().setMoneySpentCurrentMonthToZero();
                        set.getValue().setNumAccountsWatchedToZero();
                    }

                    // Update current, previous and total dollar amounts for service and remove all movie and Pay-Per-View offerings
                    for (Map.Entry<String, Service> set : nameServiceMap.entrySet()) {
                        set.getValue().clearOfferedEvents();
                        set.getValue().setSubscriptionFeesForNewMonth();
                    }

                    for (Map.Entry<String, Studio> set : nameStudioMap.entrySet()) {
                        set.getValue().setLicensingFeesForNewMonth();
                    }

                    this.offerList = new ArrayList<Offer>();
                } else if (tokens[0].equals("display_demo")) {                   
                    DemographicGroup selectDemo = this.nameDemoMap.get(tokens[1]);
                    System.out.println("demo," + selectDemo.getShortName() + "," + selectDemo.getLongName());
                    System.out.println("size," + selectDemo.getNumAccounts());
                    System.out.println("current_period," + selectDemo.getMoneySpentCurrentMonth());
                    System.out.println("previous_period," + selectDemo.getMoneySpentPreviousMonth());
                    System.out.println("total," + selectDemo.getMoneySpentTotal());

                } else if (tokens[0].equals("display_events")) {
                    for (Event event : eventList) {
                        System.out.println(event.getType() + "," + event.getName() + "," + event.getYearProduced() + "," + event.getDuration() + "," + event.getStudio() + "," + event.getLicenseFee());
                    }
                } else if (tokens[0].equals("display_stream")) {
                    Service selectStream = this.nameServiceMap.get(tokens[1]);
                    System.out.println("stream," + selectStream.getShortName() + "," + selectStream.getLongName());
                    System.out.println("subscription," + selectStream.getSubscriptionFee());
                    System.out.println("current_period," + selectStream.getSubscriptionFeeCollectedCurrentMonth());
                    System.out.println("previous_period," + selectStream.getSubscriptionFeeCollectedPreviousMonth());
                    System.out.println("total," + selectStream.getSubscriptionFeeCollectedTotal());
                    System.out.println("licensing," + selectStream.getTotalLicensingCost());

                } else if (tokens[0].equals("display_studio")) {
                    Studio selectStudio = this.nameStudioMap.get(tokens[1]);
                    System.out.println("studio," + selectStudio.getShortName() + "," + selectStudio.getLongName());
                    System.out.println("current_period," + selectStudio.getLicensingFeeCollectedCurrentMonth());
                    System.out.println("previous_period," + selectStudio.getLicensingFeeCollectedPreviousMonth());
                    System.out.println("total," + selectStudio.getLicensingFeeCollectedTotal());

                } else if (tokens[0].equals("display_offers")) {
                    for (Offer offer : offerList) {
                        if (offer.getType().equals("movie")){
                            System.out.println(offer.getService() + "," + offer.getType() + "," + offer.getName() + "," + offer.getYearProduced());
                        }else{
                            System.out.println(offer.getService() + "," + offer.getType() + "," + offer.getName() + "," + offer.getYearProduced() + "," + offer.getOneTimeFee());
                        }
                    }
                } else if (tokens[0].equals("display_time")) {
                    System.out.println("time," + monthTimeStamp + "," + yearTimeStamp);

                } else if (tokens[0].equals("stop")) {
                    break;
                } else {
                    if (verboseMode) { System.out.println("command_" + tokens[0] + "_NOT_acknowledged"); }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }
        commandLineInput.close();
    }
}