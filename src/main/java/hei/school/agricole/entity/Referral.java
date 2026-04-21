package hei.school.agricole.entity;

import java.time.LocalDate;

public class Referral {
    private int id;
    private int referrerId;
    private int referredId;
    private LocalDate referralDate;

    public Referral(int id, int referrerId, int referredId, LocalDate referralDate) {
        this.id = id;
        this.referrerId = referrerId;
        this.referredId = referredId;
        this.referralDate = referralDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(int referrerId) {
        this.referrerId = referrerId;
    }

    public int getReferredId() {
        return referredId;
    }

    public void setReferredId(int referredId) {
        this.referredId = referredId;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDate referralDate) {
        this.referralDate = referralDate;
    }
}