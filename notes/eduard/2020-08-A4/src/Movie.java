public final class Movie extends Event {
    public Movie(String name, int year_produced, int duration, Studio studio, int license_fee) {
        super(name, year_produced, duration, studio, license_fee);
    }

    public void print() {
        System.out.println("movie" + "," + name + "," + year_produced + "," + duration + "," + studio.short_name + "," + license_fee);
    }
}
