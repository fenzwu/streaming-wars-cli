import java.util.HashMap;

public final class PayPerView extends Event {
    public HashMap<String, Integer> viewingPrices = new HashMap<String,Integer>();

    public PayPerView(String name, int year_produced, int duration, Studio studio, int license_fee) {
        super(name, year_produced, duration, studio, license_fee);
    }

    public void setPriceForStreamingService(int price, String streamingServiceName) {
        viewingPrices.put(streamingServiceName, price);
    }

    public void print() {
        System.out.println("ppv" + "," + name + "," + year_produced + "," + duration + "," + studio.short_name + "," + license_fee);
    }
}
