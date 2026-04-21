package hei.school.agricole.entity;

public class Mandate {
    private int id;
    private int year;
    private int duration;

    public Mandate(int id, int year, int duration) {
        this.id = id;
        this.year = year;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}