package hei.school.agricole.dto;

public class CollectivityLocalStatistics {
    private MemberDescription memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;
    private Double assiduityPercentage;

    public MemberDescription getMemberDescription() { return memberDescription; }
    public void setMemberDescription(MemberDescription memberDescription) { this.memberDescription = memberDescription; }
    public Double getEarnedAmount() { return earnedAmount; }
    public void setEarnedAmount(Double earnedAmount) { this.earnedAmount = earnedAmount; }
    public Double getUnpaidAmount() { return unpaidAmount; }
    public void setUnpaidAmount(Double unpaidAmount) { this.unpaidAmount = unpaidAmount; }
    public Double getAssiduityPercentage() { return assiduityPercentage; }
    public void setAssiduityPercentage(Double assiduityPercentage) { this.assiduityPercentage = assiduityPercentage; }
}