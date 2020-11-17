import java.util.*;

public class TestCaseReader {
    private final State state = new State();
    private final EventHandler handler = new EventHandler();

    public TestCaseReader() {}

    private void create_demo(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("create_demo_acknowledged"); }
        String short_name = tokens[1];
        String long_name = tokens[2];
        int num_accounts = Integer.parseInt(tokens[3]);

        handler.create_demo(verboseMode, short_name, long_name, num_accounts, state.demographicGroups);
    }

    private void create_studio(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("create_studio_acknowledged"); }
        String short_name = tokens[1];
        String long_name = tokens[2];

        handler.create_studio(verboseMode, short_name, long_name, state.studios);
    }

    private void create_event(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("create_event_acknowledged"); }
        String event_type = tokens[1];
        String name = tokens[2];
        int year_produced = Integer.parseInt(tokens[3]);
        int duration = Integer.parseInt(tokens[4]);
        String studio_name = tokens[5];
        int license_fee = Integer.parseInt(tokens[6]);

        handler.create_event(
                verboseMode,
                event_type,
                name,
                year_produced,
                duration,
                studio_name,
                license_fee,
                state.studios,
                state.events
        );
    }

    private void create_stream(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("create_stream_acknowledged"); }
        String short_name = tokens[1];
        String long_name = tokens[2];
        int subscription_price = Integer.parseInt(tokens[3]);

        handler.create_stream(verboseMode, short_name, long_name, subscription_price, state.streamingServices);
    }

    private void offer_event(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println(tokens[0] + "_acknowledged"); }
        String offerType = tokens[0].substring(6);
        String streamingServiceName = tokens[1];
        String eventName = tokens[2];
        int eventYear = Integer.parseInt(tokens[3]);

        handler.offer_event(
                verboseMode,
                offerType,
                streamingServiceName,
                eventName,
                eventYear,
                tokens,
                state.streamingServices,
                state.events,
                state.offers
        );
    }

    private void watch_event(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("watch_event_acknowledged"); }
        String demoGroupName = tokens[1];
        int watchPercentage = Integer.parseInt(tokens[2]);
        String streamingServiceName = tokens[3];
        String eventName = tokens[4];
        int eventYear = Integer.parseInt(tokens[5]);

        handler.watch_event(
                verboseMode,
                demoGroupName,
                watchPercentage,
                streamingServiceName,
                eventName,
                eventYear,
                state.demographicGroups,
                state.streamingServices,
                state.events
        );
    }

    private void next_month(boolean verboseMode) {
        handler.next_month(verboseMode, state);
    }

    private void display_demo(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("display_demo_acknowledged"); }
        String demoGroupName = tokens[1];

        handler.display_demo(verboseMode, demoGroupName, state.demographicGroups);
    }

    private void display_events(boolean verboseMode) {
        handler.display_events(verboseMode, state.events);
    }

    private void display_stream(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("display_stream_acknowledged"); }
        String streamingServiceName = tokens[1];

        handler.display_stream(verboseMode, streamingServiceName, state.streamingServices);
    }

    private void display_studio(String[] tokens, boolean verboseMode) {
        if (verboseMode) { System.out.println("display_studio_acknowledged"); }
        String studioName = tokens[1];

        handler.display_studio(verboseMode, studioName, state.studios);
    }

    private void display_offers(boolean verboseMode) {
        handler.display_offers(verboseMode, state.offers);
    }
    private void display_time(boolean verboseMode) {
        handler.display_time(verboseMode, state.monthTimeStamp, state.yearTimeStamp);
    }

    public void processInstructions(Boolean verboseMode) {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        label:
        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                switch (tokens[0]) {
                    case "create_demo": create_demo(tokens, verboseMode); break;
                    case "create_studio": create_studio(tokens, verboseMode); break;
                    case "create_event": create_event(tokens, verboseMode); break;
                    case "create_stream": create_stream(tokens, verboseMode); break;
                    case "offer_movie":
                    case "offer_ppv": offer_event(tokens, verboseMode);break;
                    case "watch_event": watch_event(tokens, verboseMode); break;
                    case "next_month": next_month(verboseMode); break;
                    case "display_demo": display_demo(tokens, verboseMode); break;
                    case "display_events": display_events(verboseMode); break;
                    case "display_stream": display_stream(tokens, verboseMode); break;
                    case "display_studio": display_studio(tokens, verboseMode); break;
                    case "display_offers": display_offers(verboseMode); break;
                    case "display_time": display_time(verboseMode); break;
                    case "stop": break label;
                    default:
                        if (verboseMode) {
                            System.out.println("command_" + tokens[0] + "_NOT_acknowledged");
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        if (verboseMode) { System.out.println("stop_acknowledged"); }
        commandLineInput.close();
    }

}
