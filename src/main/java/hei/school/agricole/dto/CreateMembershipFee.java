package hei.school.agricole.dto;

import hei.school.agricole.enums.Frequency;

public class CreateMembershipFee {

    private String eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;

    public String getEligibleFrom() {
        return eligibleFrom;
    }

    public void setEligibleFrom(String eligibleFrom) {
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
}