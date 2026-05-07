package hei.school.agricole.dto;

public class FederationStatisticsDto {
    private String collectivityId;
    private Double percentageUpToDateMembers;
    private Integer newMembersCount;
    private Double globalAttendanceRate;

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public Double getPercentageUpToDateMembers() {
        return percentageUpToDateMembers;
    }

    public void setPercentageUpToDateMembers(Double percentageUpToDateMembers) {
        this.percentageUpToDateMembers = percentageUpToDateMembers;
    }

    public Integer getNewMembersCount() {
        return newMembersCount;
    }

    public void setNewMembersCount(Integer newMembersCount) {
        this.newMembersCount = newMembersCount;
    }

    public Double getGlobalAttendanceRate() {
        return globalAttendanceRate;
    }

    public void setGlobalAttendanceRate(Double globalAttendanceRate) {
        this.globalAttendanceRate = globalAttendanceRate;
    }
}