package hei.school.agricole.entity;

import hei.school.agricole.enums.PaymentMode;
import java.time.LocalDate;

public class MemberPayment {
    private String id;
    private Integer amount;
    private PaymentMode paymentMode;
    private String membershipFeeId;
    private LocalDate creationDate;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
    public String getMembershipFeeId() {
        return membershipFeeId;
    }
    public void setMembershipFeeId(String membershipFeeId) {
        this.membershipFeeId = membershipFeeId;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}