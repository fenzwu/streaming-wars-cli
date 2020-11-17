import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class TextCommandProcessor {

    private IActions actionHandler;

    TextCommandProcessor(IActions actionHandler) {
        this.actionHandler = actionHandler;
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

                } else if (tokens[0].equals("create_user")) {
                    if (verboseMode) {
                        System.out.println("create_user_acknowledged");
                    }

                    String username = tokens[1];
                    String password = tokens[2];
                    String role = tokens[3];

                    actionHandler.onCreateUser(username, password, role);

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
                } else if (tokens[0].equals("display_user")) {
                    if (verboseMode) {
                        System.out.println("display_user_acknowledged");
                    }

                    HashMap<String, String> userInfo = actionHandler.onDisplayActiveUser();
                    System.out.println("user: " + userInfo.get("username") + ", role: " + userInfo.get("role"));

                } else if (tokens[0].equals("display_logs")) {
                    if (verboseMode) {
                        System.out.println("display_logs_acknowledged");
                    }

                    ArrayList<HashMap<String, String>> logs = actionHandler.onDisplayLogs();
                    for (HashMap<String, String> dict : logs) {
                        System.out.println(dict.get("subject") + " \t " +
                                dict.get("message") + " \t " +
                                dict.get("time"));
                    }

                } else if (tokens[0].equals("login")) {
                    if (verboseMode) {
                        System.out.println("login_acknowledged");
                    }

                    String username = tokens[1];
                    String password = tokens[2];

                    actionHandler.onLogin(username, password);

                } else if (tokens[0].equals("logout")) {
                    if (verboseMode) {
                        System.out.println("logout_acknowledged");
                    }

                    actionHandler.onLogout();

                } else if (tokens[0].equals("change_password")) {
                    if (verboseMode) {
                        System.out.println("change_password_acknowledged");
                    }
                    String newPassword = tokens[1];

                    actionHandler.onChangePassword(newPassword);

                } else if (tokens[0].equals("change_role")) {
                    if (verboseMode) {
                        System.out.println("change_role_acknowledged");
                    }

                    String username = tokens[1];
                    String newRole = tokens[2];

                    actionHandler.onChangeRole(username, newRole);

                } else if (tokens[0].equals("update_demo")) {
                    if (verboseMode) {
                        System.out.println("update_demo_acknowledged");
                    }

                    String shortName = tokens[1];
                    String longName = tokens[2];
                    int numAccounts = Integer.parseInt(tokens[3]);

                    actionHandler.onUpdateDemo(shortName, longName, numAccounts);

                } else if (tokens[0].equals("update_stream")) {
                    if (verboseMode) {
                        System.out.println("update_stream_acknowledged");
                    }

                    String shortName = tokens[1];
                    String longName = tokens[2];
                    int subscriptionPrice = Integer.parseInt(tokens[3]);

                    actionHandler.onUpdateService(shortName, longName, subscriptionPrice);

                } else if (tokens[0].equals("update_event")) {
                    if (verboseMode) {
                        System.out.println("update_event_acknowledged");
                    }

                    String name = tokens[1];
                    int year = Integer.parseInt(tokens[2]);
                    int duration = Integer.parseInt(tokens[3]);
                    int licenseFee = Integer.parseInt(tokens[4]);

                    actionHandler.onUpdateEvent(name, year, duration, licenseFee);

                } else if (tokens[0].equals("clear_logs")) {
                    if (verboseMode) {
                        System.out.println("clear_logs_acknowledged");
                    }

                    actionHandler.onClearLogs();

                }
                else if (tokens[0].equals("retract_movie"))
                {
                    if (verboseMode)
                    {
                        System.out.println("retract_movie_acknowledged");
                    }
                    if(tokens[1] == "" || tokens[2] == "" || tokens[3] == "")
                    {//missing tokens. do not continue command
                        continue;
                    }
                    //parse tokens
                    String serviceName = tokens[1];
                    String movieName = tokens[2];
                    int year = Integer.parseInt(tokens[3]);

                    //retract the movie
                    actionHandler.onRetractMovie(serviceName, movieName, year);
                }
                else if (tokens[0].equals("stop")) {
                    break;
                } else {
                    if (verboseMode) {
                        System.out.println("command_" + tokens[0] + "_NOT_acknowledged");
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void processCommands() {
        processCommands(false);
    }

}
