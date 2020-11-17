import java.util.HashMap;
import java.util.LinkedHashMap;

public class Directory {

    private LinkedHashMap<String, Event> movies;
    private LinkedHashMap<String, Event> ppvs;
    private HashMap<String, Integer> ppvPrices;

    Directory() {
        movies = new LinkedHashMap<String, Event>();
        ppvs = new LinkedHashMap<String, Event>();
        ppvPrices = new HashMap<String, Integer>();
    }

    public void addMovie(Event event) {
        movies.put(event.getID(), event);
    }

    public void addPPV(Event event, int price) {
        String eventID = event.getID();
        ppvs.put(eventID, event);
        ppvPrices.put(eventID, price);
    }

    public Integer getPPVPrice(Event event) {
        return ppvPrices.get(event.getID());
    }

    public boolean hasEvent(Event event) {
        if (event.getType() == EventType.MOVIE) {
            return movies.containsKey(event.getID());
        } else if (event.getType() == EventType.PPV) {
            return ppvs.containsKey(event.getID());
        }

        return false;
    }

    public void clear() {
        movies.clear();
        ppvs.clear();
        ppvPrices.clear();
    }


}
