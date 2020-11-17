public class Movie extends Offering {

    public Movie(StreamingService streamingService, Event event) {
        super(streamingService, event);
    }

    @Override
    public String toString() {
        // <stream>,<type>,<uniqueName>,<price>
        return String.format("%s,%s,%s",
                super.streamingService.getShortName(),
                super.event.getEventType(),
                super.event.getUniqueName());
    }
}
