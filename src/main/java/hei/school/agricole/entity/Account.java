package hei.school.agricole.entity;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String type; // CASH, BANK, MOBILE_MONEY
    private BigDecimal balance;

    private Integer collectivityId;
    private Integer federationId;

    public Account(int id, String type, BigDecimal balance, Integer collectivityId, Integer federationId) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.collectivityId = collectivityId;
        this.federationId = federationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(Integer collectivityId) {
        this.collectivityId = collectivityId;
    }

    public Integer getFederationId() {
        return federationId;
    }

    public void setFederationId(Integer federationId) {
        this.federationId = federationId;
    }
}