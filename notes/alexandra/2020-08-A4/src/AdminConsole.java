import java.util.*;

public class AdminConsole {

    private Manager manager = new Manager();

    // Initializes the system current month and year as 10 (October) and 2020, respectively.
    private Time time = new Time(10, 2020);

    public AdminConsole() { }

    public void processInstructions() {
        final List<String> eventTypes = Arrays.asList("movie", "ppv");
        Scanner scanner = new Scanner(System.in);
        String userInput;
        String[] args;

        cli:
        while (true) {

            try {
                // Determine the next command and echo it to the monitor for testing purposes.
                userInput = scanner.nextLine();
                args = userInput.split(",");
                System.out.println("> " + userInput);
                String command = args[0];

                switch (command) {
                    case "create_demo": {
                        // create_demo,<1:shortName>,<2:longName>,<3:numOfAccounts>
                        manager.createDemographicGroup(
                                /*shortName=*/args[1],
                                /*longName=*/args[2],
                                /*numOfAccounts=*/Integer.parseInt(args[3]));
                        break;
                    }
                    case "display_demo": {
                        // display_demo,<1:shortName>
                        DemographicGroup demographicGroup = manager.lookupDemographicGroup(/*shortName=*/args[1]);
                        if (demographicGroup != null) {
                            System.out.println(demographicGroup);
                        }
                        break;
                    }
                    case "create_studio": {
                        // create_studio,<1:shortName>,<2:longName>
                        manager.createStudio(/*shortName=*/args[1], /*longName=*/args[2]);
                        break;
                    }
                    case "display_studio": {
                        // display_studio,<1:shortName>
                        Studio studio = manager.lookupStudio(/*shortName=*/args[1]);
                        if (studio != null) {
                            System.out.println(studio);
                        }
                        break;
                    }
                    case "create_stream": {
                        // create_stream,<1:shortName>,<2:longName>,<3:subscriptionPrice>
                        manager.createStreamingService(
                                /*shortName=*/args[1],
                                /*longName=*/args[2],
                                /*subscriptionPrice=*/Integer.parseInt(args[3]));
                        break;
                    }
                    case "display_stream": {
                        // display_stream,<1:shortName>
                        StreamingService streamingService = manager.lookupStreamingService(/*shortName=*/args[1]);
                        if (streamingService != null) {
                            System.out.println(streamingService);
                        }
                        break;
                    }
                    case "create_event": {
                        // create_event,<1:type>,<2:name>,<3:yearProduced>,<4:duration>,<5:studio>,<6:licenseFee>
                        String eventType = args[1];
                        if (!eventTypes.contains(eventType)) {
                            break;
                        }

                        Studio studio = manager.lookupStudio(/*shortName=*/args[5]);
                        if (studio != (null)) {
                            Event event = studio.createEvent(
                                    /*type=*/eventType,
                                    /*name=*/args[2],
                                    /*year=*/args[3],
                                    /*duration=*/Integer.parseInt(args[4]),
                                    /*licenseFee=*/Integer.parseInt(args[6]));
                            manager.addEvent(event.getUniqueName(), event);
                        }
                        break;
                    }
                    case "watch_event": {
                        // watch_event,<demographicGroup>,<percentage>,<streamingService>,<uniqueName>
                        // watch_event,<1:demographicGroup>,<2:percentage>,<3:streamingService>,<4:name>,<5:yearProduced>
                        DemographicGroup demographicGroup = manager.lookupDemographicGroup(/*shortName=*/args[1]);
                        StreamingService streamingService = manager.lookupStreamingService(/*shortName=*/args[3]);

                        // Validates that the demographic group and streaming service provided in the command exist.
                        if (demographicGroup == null || streamingService == null) {
                            break;
                        }

                        String eventName = Event.formatUniqueName(/*titleName=*/args[4], /*yearProduced=*/args[5]);
                        demographicGroup.watchEvent(streamingService, eventName, /*percent=*/Integer.parseInt(args[2]));
                        break;
                    }
                    case "display_events": {
                        // display_events
                        // no additional arguments
                        Collection<Event> events = manager.listEvents();
                        for (Event event : events) {
                            System.out.println(event);
                        }
                        break;
                    }
                    case "offer_movie": {
                        // offer_movie,<streamingService>,<uniqueName>
                        // offer_movie,<1:streamingService>,<2:name>,<3:yearProduced>
                        StreamingService streamingService = manager.lookupStreamingService(/*shortName=*/args[1]);
                        Event event = manager.lookupEvent(/*titleName=*/args[2], /*yearProduced=*/args[3]);

                        // Validates that the streaming service and event name provided in the command exist.
                        if (streamingService == null || event == null) {
                            break;
                        }

                        Offering movie = streamingService.offerMovie(event);
                        if (movie != null) {
                            manager.addOffering(movie);
                        }
                        break;
                    }
                    case "offer_ppv": {
                        // offer_ppv,<streamingService>,<uniqueName>,<price>
                        // offer_ppv,<1:streamingService>,<2:name>,<3:yearProduced>,<4:price>
                        StreamingService streamingService = manager.lookupStreamingService(/*shortName=*/args[1]);
                        Event event = manager.lookupEvent(/*titleName=*/args[2], /*yearProduced=*/args[3]);
                        // Validates that the streaming service and event name provided in the command exist.
                        if (streamingService.equals(null) || event.equals(null)) {
                            break;
                        }
                        Offering ppv = streamingService.offerPPV(event, /*price=*/Integer.parseInt(args[4]));
                        if (ppv != null) {
                            manager.addOffering(ppv);
                        }
                        break;
                    }
                    case "display_offers": {
                        // display_offers
                        // no additional arguments
                        for (Offering offering : manager.getCurrentOfferings()) {
                            System.out.println(offering);
                        }
                        break;
                    }
                    case "next_month": {
                        time.nextMonth();
                        manager.closeBooks(time);
                        break;
                    }
                    case "display_time": {
                        System.out.println(time);
                        break;
                    }
                    case "stop":
                        break cli;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }
        scanner.close();
    }
}
