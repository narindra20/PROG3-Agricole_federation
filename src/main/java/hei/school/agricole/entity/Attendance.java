package hei.school.agricole.entity;

public class Attendance {
    private int id;
    private int memberId;
    private int activityId;
    private boolean present;
    private boolean excused;

    public Attendance(int id, int memberId, int activityId, boolean present, boolean excused) {
        this.id = id;
        this.memberId = memberId;
        this.activityId = activityId;
        this.present = present;
        this.excused = excused;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isExcused() {
        return excused;
    }

    public void setExcused(boolean excused) {
        this.excused = excused;
    }
}
