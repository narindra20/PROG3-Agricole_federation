package hei.school.agricole.dto;

public class CollectivityStatistics {
    private String collectivityId;
    private Double percentageUpToDate;
    private Integer newMembersCount;
    private Double attendanceRate;

    public String getCollectivityId() {
        return collectivityId;
    }
    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }
    public Double getPercentageUpToDate() {
        return percentageUpToDate;
    }
    public void setPercentageUpToDate(Double percentageUpToDate) {
        this.percentageUpToDate = percentageUpToDate;
    }
    public Integer getNewMembersCount() {
        return newMembersCount;
    }
    public void setNewMembersCount(Integer newMembersCount) {
        this.newMembersCount = newMembersCount;
    }
    public Double getAttendanceRate() {
        return attendanceRate;
    }
    public void setAttendanceRate(Double attendanceRate) {
        this.attendanceRate = attendanceRate;
    }
}