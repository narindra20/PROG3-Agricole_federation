package hei.school.agricole.dto;

public class AttendanceRecord {
    private String memberId;
    private Boolean present;
    private Boolean excused;



    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public Boolean getPresent() {
        return present;
    }
    public void setPresent(Boolean present) {
        this.present = present;
    }
    public Boolean getExcused() {
        return excused;
    }
    public void setExcused(Boolean excused) {
        this.excused = excused;
    }
}