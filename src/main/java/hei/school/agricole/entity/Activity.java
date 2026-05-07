package hei.school.agricole.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String id;
    private String label;
    private String activityType;
    private List<String> memberOccupationConcerned = new ArrayList<>();
    private LocalDate executiveDate;
    private String collectivityId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }
    public List<String> getMemberOccupationConcerned() { return memberOccupationConcerned; }
    public void setMemberOccupationConcerned(List<String> memberOccupationConcerned) {
        this.memberOccupationConcerned = memberOccupationConcerned != null ? memberOccupationConcerned : new ArrayList<>();
    }
    public LocalDate getExecutiveDate() { return executiveDate; }
    public void setExecutiveDate(LocalDate executiveDate) { this.executiveDate = executiveDate; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
}