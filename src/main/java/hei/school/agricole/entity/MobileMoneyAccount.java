package hei.school.agricole.entity;

import java.math.BigDecimal;

public class MobileMoneyAccount extends Account {
    private String holder;
    private String provider;
    private String phoneNumber;

    public MobileMoneyAccount(int id, String type, BigDecimal balance, Integer collectivityId, Integer federationId, String holder, String provider, String phoneNumber) {
        super(id, type, balance, collectivityId, federationId);
        this.holder = holder;
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}