package hei.school.agricole.entity;

import java.time.LocalDate;

public class Activity {
    private int id;
    private String title;
    private String type;
    private LocalDate activityDate;
    private boolean mandatory;

    private Integer collectivityId;
    private Integer federationId;

    public Activity(int id, String title, String type, LocalDate activityDate, boolean mandatory, Integer collectivityId, Integer federationId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.activityDate = activityDate;
        this.mandatory = mandatory;
        this.collectivityId = collectivityId;
        this.federationId = federationId;
    }
}
