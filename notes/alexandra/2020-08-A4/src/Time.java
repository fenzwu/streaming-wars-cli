public final class Time {

    private int month;
    private int year;
    private String currentDate;

    public Time(int month, int year) {
        this.month = month;
        this.year = year;
        this.currentDate = formatCurrentDate();
    }

    public Time nextMonth() {
        if (this.month == 12) {
            this.year++;
        }
        int nextMonth = (this.month % 12) + 1;
        this.month = nextMonth;
        this.currentDate = formatCurrentDate();
        return this;
    }

    private String formatCurrentDate() {
        return String.format("%d-%d", this.year, this.month);
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String formatPreviousDate() {
        int prevMonth = 0;
        int prevYear = 0;
        if (this.month == 1) {
            prevMonth = 12;
            prevYear = this.year - 1;
        }
        return String.format("%d-%d", prevYear, prevMonth);
    }

    @Override
    public String toString() {
        // time,<month>,<year>
        return String.format("time,%d,%d", month, year);
    }
}
