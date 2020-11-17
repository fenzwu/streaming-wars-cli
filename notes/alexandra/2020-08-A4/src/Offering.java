public abstract class Offering {

    protected final StreamingService streamingService;
    protected final Event event;

    public Offering(StreamingService streamingService, Event event) {
        this.streamingService = streamingService;
        this.event = event;
    }
}
