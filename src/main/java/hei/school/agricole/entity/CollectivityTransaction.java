package hei.school.agricole.entity;

import hei.school.agricole.enums.PaymentMode;
import java.time.LocalDate;

public class CollectivityTransaction {
    private String id;
    private String collectivityId;
    private LocalDate creationDate;
    private Double amount;
    private PaymentMode paymentMode;
    private String accountCreditedId;
    private String memberDebitedId;

    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }
    public String getCollectivityId() {
        return collectivityId;
    }
    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
    public String getAccountCreditedId() {
        return accountCreditedId;
    }
    public void setAccountCreditedId(String accountCreditedId) {
        this.accountCreditedId = accountCreditedId;
    }
    public String getMemberDebitedId() {
        return memberDebitedId;
    }
    public void setMemberDebitedId(String memberDebitedId) {
        this.memberDebitedId = memberDebitedId;
    }
}
