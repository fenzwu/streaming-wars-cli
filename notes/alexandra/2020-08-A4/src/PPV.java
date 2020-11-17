public class PPV extends Offering {

    private int price;

    public PPV(StreamingService streamingService, Event event, int price) {
        super(streamingService, event);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        // <stream>,<type>,<uniqueName>,<price>
        return String.format("%s,%s,%s,%d",
                super.streamingService.getShortName(),
                super.event.getEventType(),
                super.event.getUniqueName(),
                this.price);
    }
}
