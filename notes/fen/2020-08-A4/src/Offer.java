public class Offer{

    private String service;
    private String type;
    private String name;
    private int yearProduced;
    private int oneTimeFee;

    public Offer(String service, String type, String name, int yearProduced) {
        this.service = service;
        this.type = type;
        this.name = name;
        this.yearProduced = yearProduced;
        this.oneTimeFee = 0;
    }
    
    // getters
    public String getService(){
        return this.service;
    }

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public int getYearProduced(){
        return this.yearProduced;
    }

    public int getOneTimeFee(){
        return this.oneTimeFee;
    }

    // setters
    public void setOneTimeFee(int oneTimeFee){
        this.oneTimeFee = oneTimeFee;
    }
}