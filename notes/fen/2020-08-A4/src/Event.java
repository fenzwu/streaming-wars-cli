public class Event{

    private String type;
    private String name;
    private int yearProduced;
    private int duration;
    private String studio;
    private int licenseFee;
    private int oneTimeFee;

    public Event(String type, String name, int yearProduced, int duration, String studio, int licenseFee) {
        this.type = type;
        this.name = name;
        this.yearProduced = yearProduced;
        this.duration = duration;
        this.studio = studio;
        this.licenseFee = licenseFee;
        this.oneTimeFee = 0;
    }
    
    // getters
    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public int getYearProduced(){
        return this.yearProduced;
    }

    public int getDuration(){
        return this.duration;
    }

    public String getStudio(){
        return this.studio;
    }

    public int getLicenseFee(){
        return this.licenseFee;
    }

    public int getOneTimeFee(){
        return this.oneTimeFee;
    }
    
    // setters
    public void setOneTimeFee(int oneTimeFee){
        this.oneTimeFee = oneTimeFee;
    }

}