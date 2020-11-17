import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContentProvider {

    private LinkedHashMap<String, Studio> studios;
    private LinkedHashMap<String, Service> services;
    private LinkedHashMap<String, Event> events;
    private ArrayList<String> genres;
    private ArrayList<HashMap<String, String>> offers;

    ContentProvider() {
        studios = new LinkedHashMap<String, Studio>();
        services = new LinkedHashMap<String, Service>();
        events = new LinkedHashMap<String, Event>();
        genres = new ArrayList<String>();
        offers = new ArrayList<HashMap<String, String>>();
    }

    public Studio createStudio(String shortName, String longName) {

        //Ensure studio short name is unique
        if (studios.containsKey(shortName)) {
            return null;
        }

        Studio studio = new Studio(shortName, longName);
        studios.put(shortName, studio);
        return studio;
    }

    public Studio getStudio(String shortName) {
        return studios.get(shortName);
    }

    public Service createService(String shortName, String longName, int subscriptionPrice) {

        //Ensure service short name is unique
        if (services.containsKey(shortName)) {
            return null;
        }

        Service service = new Service(shortName, longName);
        service.setSubscriptionPrice(subscriptionPrice);
        services.put(shortName, service);
        return service;
    }

    public Service getService(String shortName) {
        return services.get(shortName);
    }

    public void updateService(Service service, String longName, int subscriptionPrice) {
        service.setLongName(longName);

        // Only allow the subscription price to be modified if that streaming service has not been used to access and
        // view any movies.
        if (!service.getHasBeenUsed()) {
            service.setSubscriptionPrice(subscriptionPrice);
        }

        services.replace(service.getShortName(), service);
    }

    public Event createEvent(String type, String name, int year, int duration, String studioName, int licenseFee) {

        //Ensure events are unique and studio exists
        String id = name + year;
        if (events.containsKey(id) || !studios.containsKey(studioName)) {
            return null;
        }

        EventType eventType = EventType.fromString(type);
        Event event = new Event(eventType, name, year, duration);
        Studio studio = studios.get(studioName);
        event.setStudio(studio);
        event.setLicenseFee(licenseFee);
        events.put(id, event);
        return event;
    }

    public Event getEvent(String name, int year) {
        String id = name + year;
        return events.get(id);
    }

    public void updateEvent(Event event, int duration, int licenseFee) {
        event.setDuration(duration);

        // Only allow the license fee to be modified if that event has not been accessed
        // by a demo group
        if (!event.getHasBeenViewed()) {
            event.setLicenseFee(licenseFee);
        }

        events.replace(event.getID(), event);
    }

    public ArrayList<HashMap<String, String>> getAllEventsInfo() {
        ArrayList<HashMap<String, String>> eventsDict = new ArrayList<HashMap<String, String>>();

        for(Map.Entry<String,Event> entry : events.entrySet()) {
            eventsDict.add(entry.getValue().getEventInfo());
        }

        return eventsDict;
    }

    public void offerMovie(String serviceName, String movieName, int year) {

        //Ensure service and movie exists
        String movieID = movieName + year;
        if (!events.containsKey(movieID) || !services.containsKey(serviceName)) {
            return;
        }

        Service service = services.get(serviceName);
        Event event = events.get(movieID);

        //Ensure movieName is a movie and not a ppv
        if(event.getType() != EventType.MOVIE) {
            return;
        }

        //Check if service already offers movie
        if(service.offersEvent(event)) {
            return;
        }

        //Offer movie and pay licensing fee
        Studio studio = event.getStudio();
        studio.addRevenue(event.getLicenseFee());
        service.offerMovie(event);

        //Save offer
        HashMap<String, String> offerDict = new HashMap<String, String>();
        offerDict.put("serviceName", serviceName);
        offerDict.put("type", "movie");
        offerDict.put("eventName", movieName);
        offerDict.put("year", String.valueOf(year));
        offers.add(offerDict);

    }

    public void offerPPV(String serviceName, String ppvName, int year, int price) {

        //Ensure service and movie exists
        String ppvID = ppvName + year;
        if (!events.containsKey(ppvID) || !services.containsKey(serviceName)) {
            return;
        }

        Service service = services.get(serviceName);
        Event event = events.get(ppvID);

        //Ensure movieName is a movie and not a ppv
        if(event.getType() != EventType.PPV) {
            return;
        }

        //Check if service already offers movie
        if(service.offersEvent(event)) {
            return;
        }

        //Offer movie and pay licensing fee
        Studio studio = event.getStudio();
        studio.addRevenue(event.getLicenseFee());
        service.offerPPV(event, price);

        //Save offer
        HashMap<String, String> offerDict = new HashMap<String, String>();
        offerDict.put("serviceName", serviceName);
        offerDict.put("type", "ppv");
        offerDict.put("eventName", ppvName);
        offerDict.put("year", String.valueOf(year));
        offerDict.put("price", String.valueOf(price));
        offers.add(offerDict);
    }

    public ArrayList<HashMap<String, String>> getOffers() {
        return offers;
    }

    public void notifyNextMonth() {

        offers.clear();

        for(Map.Entry<String, Studio> entry : studios.entrySet()) {
            entry.getValue().onNextMonth();
        }

        for(Map.Entry<String, Service> entry : services.entrySet()) {
            entry.getValue().onNextMonth();
        }

        for(Map.Entry<String, Event> entry : events.entrySet()) {
            entry.getValue().setToUnviewed();
        }
    }

    //This command must remove the designated movie from the listing for that specific streaming service.
    public void retractMovie(String serviceName, String movieName, int year)
    {
        //Ensure service and movie exists
        String movieID = movieName + year;
        if (!events.containsKey(movieID) || !services.containsKey(serviceName))
        {
            return;
        }

        Service service = services.get(serviceName);
        Event event = events.get(movieID);

        //Ensure movieName is a movie and not a ppv
        if(event.getType() != EventType.MOVIE)
        {
            return;
        }
        //Check if service offers the movie
        //the movie needs to be offered before being retracted
        if(!service.offersEvent(event))
        {
            return;
        }
        //retract the movie offered
        HashMap<String, String> offerDict = new HashMap<String, String>();
        offerDict.put("serviceName", serviceName);
        offerDict.put("type", "movie");
        offerDict.put("eventName", movieName);
        offerDict.put("year", String.valueOf(year));
        offers.remove(offerDict);

    }

}
