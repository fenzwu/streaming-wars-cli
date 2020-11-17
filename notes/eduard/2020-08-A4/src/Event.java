public abstract class Event {
    public String name;
    public Studio studio;
    public int license_fee;
    public int year_produced;
    public int duration;

    public Event(String name, int year_produced, int duration, Studio studio, int license_fee) {
        this.name = name;
        this.year_produced = year_produced;
        this.duration = duration;
        this.studio = studio;
        this.license_fee = license_fee;
    }

    public String unique_id() {
        return name + year_produced;
    }

    public void print() {}
}

