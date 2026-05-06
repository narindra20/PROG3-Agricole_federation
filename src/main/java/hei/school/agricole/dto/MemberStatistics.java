package hei.school.agricole.dto;

public class MemberStatistics {
    private String memberId;
    private Double totalPayments;
    private Double potentialOutstanding;
    private Double attendanceRate;

    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public Double getTotalPayments() {
        return totalPayments;
    }
    public void setTotalPayments(Double totalPayments) {
        this.totalPayments = totalPayments;
    }
    public Double getPotentialOutstanding() {
        return potentialOutstanding;
    }
    public void setPotentialOutstanding(Double potentialOutstanding) {
        this.potentialOutstanding = potentialOutstanding;
    }
    public Double getAttendanceRate() {
        return attendanceRate;
    }
    public void setAttendanceRate(Double attendanceRate) {
        this.attendanceRate = attendanceRate;
    }
}