import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class TextCommandProcessor {

    private ActionHandler actionHandler;

    TextCommandProcessor() {
        actionHandler = new ActionHandler();
    }

    /**
     * Infinite loop that processes commands and dispatches appropriate events.
     * Returns when stop command is received.
     */
    public void processCommands(Boolean verboseMode) {
        // References sample code TestCaseReader.java processInstructions()
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (tokens[0].equals("create_demo")) {
                    if (verboseMode) {
                        System.out.println("create_demo_acknowledged");
                    }
                    String shortName = tokens[1];
                    String longName = tokens[2];
                    int numAccounts = Integer.parseInt(tokens[3]);

                    actionHandler.onCreateDemographic(shortName, longName, numAccounts);

                } else if (tokens[0].equals("create_studio")) {
                    if (verboseMode) {
                        System.out.println("create_studio_acknowledged");
                    }
                    String shortName = tokens[1];
                    String longName = tokens[2];

                    actionHandler.onCreateStudio(shortName, longName);

                } else if (tokens[0].equals("create_event")) {
                    if (verboseMode) {
                        System.out.println("create_event_acknowledged");
                    }
                    String type = tokens[1];
                    String name = tokens[2];
                    int year = Integer.parseInt(tokens[3]);
                    int duration = Integer.parseInt(tokens[4]);
                    String studio = tokens[5];
                    int licenseFee = Integer.parseInt(tokens[6]);

                    actionHandler.onCreateEvent(type, name, year, duration, studio, licenseFee);

                } else if (tokens[0].equals("create_stream")) {
                    if (verboseMode) {
                        System.out.println("create_stream_acknowledged");
                    }
                    String shortName = tokens[1];
                    String longName = tokens[2];
                    int subscriptionPrice = Integer.parseInt(tokens[3]);

                    actionHandler.onCreateService(shortName, longName, subscriptionPrice);

                } else if (tokens[0].equals("offer_movie")) {
                    if (verboseMode) {
                        System.out.println("offer_movie_acknowledged");
                    }
                    String serviceName = tokens[1];
                    String movieName = tokens[2];
                    int year = Integer.parseInt(tokens[3]);

                    actionHandler.onOfferMovie(serviceName, movieName, year);

                } else if (tokens[0].equals("offer_ppv")) {
                    if (verboseMode) {
                        System.out.println("offer_ppv_acknowledged");
                    }

                    String serviceName = tokens[1];
                    String movieName = tokens[2];
                    int year = Integer.parseInt(tokens[3]);
                    int price = Integer.parseInt(tokens[4]);

                    actionHandler.onOfferPPV(serviceName, movieName, year, price);

                } else if (tokens[0].equals("watch_event")) {
                    if (verboseMode) {
                        System.out.println("watch_event_acknowledged");
                    }

                    String demoName = tokens[1];
                    int percentage = Integer.parseInt(tokens[2]);
                    String serviceName = tokens[3];
                    String eventName = tokens[4];
                    int year = Integer.parseInt(tokens[5]);

                    actionHandler.onWatchEvent(demoName, percentage, serviceName, eventName, year);

                } else if (tokens[0].equals("next_month")) {
                    if (verboseMode) {
                        System.out.println("next_month_acknowledged");
                    }

                    actionHandler.onNextMonth();

                } else if (tokens[0].equals("display_demo")) {
                    if (verboseMode) {
                        System.out.println("display_demo_acknowledged");
                    }
                    String shortName = tokens[1];

                    HashMap<String, String> dict = actionHandler.onDisplayDemo(shortName);
                    if (dict != null) {
                        System.out.println("demo," + dict.get("shortName") + "," + dict.get("longName"));
                        System.out.println("size," + dict.get("numAccounts"));
                        System.out.println("current_period," + dict.get("currMonthRevenue"));
                        System.out.println("previous_period," + dict.get("prevMonthRevenue"));
                        System.out.println("total," + dict.get("totalRevenue"));
                    }


                } else if (tokens[0].equals("display_stream")) {
                    if (verboseMode) {
                        System.out.println("display_stream_acknowledged");
                    }
                    String shortName = tokens[1];

                    HashMap<String, String> dict = actionHandler.onDisplayStream(shortName);

                    if (dict != null) {
                        System.out.println("stream," + dict.get("shortName") + "," + dict.get("longName"));
                        System.out.println("subscription," + dict.get("subscriptionPrice"));
                        System.out.println("current_period," + dict.get("currMonthRevenue"));
                        System.out.println("previous_period," + dict.get("prevMonthRevenue"));
                        System.out.println("total," + dict.get("totalRevenue"));
                        System.out.println("licensing," + dict.get("licensingCosts"));
                    }

                } else if (tokens[0].equals("display_studio")) {
                    if (verboseMode) {
                        System.out.println("display_studio_acknowledged");
                    }
                    String shortName = tokens[1];

                    HashMap<String, String> dict = actionHandler.onDisplayStudio(shortName);

                    if (dict != null) {
                        System.out.println("studio," + dict.get("shortName") + "," + dict.get("longName"));
                        System.out.println("current_period," + dict.get("currMonthRevenue"));
                        System.out.println("previous_period," + dict.get("prevMonthRevenue"));
                        System.out.println("total," + dict.get("totalRevenue"));
                    }


                } else if (tokens[0].equals("display_events")) {
                    if (verboseMode) {
                        System.out.println("display_studio_acknowledged");
                    }

                    ArrayList<HashMap<String, String>> eventList = actionHandler.onDisplayEvents();
                    for (HashMap<String, String> dict : eventList) {
                        System.out.println(
                                dict.get("type") + "," +
                                        dict.get("name") + "," +
                                        dict.get("year") + "," +
                                        dict.get("duration") + "," +
                                        dict.get("studio") + "," +
                                        dict.get("licenseFee")
                        );
                    }

                } else if (tokens[0].equals("display_offers")) {
                    if (verboseMode) {
                        System.out.println("display_offers_acknowledged");
                    }

                    ArrayList<HashMap<String, String>> offerList = actionHandler.onDisplayOffers();
                    for (HashMap<String, String> dict : offerList) {

                        System.out.print(dict.get("serviceName") + "," +
                                dict.get("type") + "," +
                                dict.get("eventName") + "," +
                                dict.get("year"));
                        if (dict.get("type").equals("ppv")) {
                            System.out.print("," + dict.get("price"));
                        }
                        System.out.println();
                    }

                } else if (tokens[0].equals("display_time")) {
                    if (verboseMode) {
                        System.out.println("display_time_acknowledged");
                    }

                    Calendar calendar = actionHandler.onDisplayTime();
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);

                    System.out.println("time," + month + "," + year);

                } else if (tokens[0].equals("stop")) {
                    break;
                } else {
                    if (verboseMode) {
                        System.out.println("command_" + tokens[0] + "_NOT_acknowledged");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

        }
    }

    public void processCommands() {
        processCommands(false);
    }

}
