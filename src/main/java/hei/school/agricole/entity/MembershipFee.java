package hei.school.agricole.entity;

import hei.school.agricole.enums.ActivityStatus;
import hei.school.agricole.enums.Frequency;
import java.time.LocalDate;

public class MembershipFee {
    private String id;
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
    private ActivityStatus status;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalDate getEligibleFrom() {
        return eligibleFrom;
    }
    public void setEligibleFrom(LocalDate eligibleFrom) {
        this.eligibleFrom = eligibleFrom;
    }
    public Frequency getFrequency() {
        return frequency;
    }
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public ActivityStatus getStatus() {
        return status;
    }
    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
}
