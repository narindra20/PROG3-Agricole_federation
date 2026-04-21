package hei.school.agricole.entity;

import java.math.BigDecimal;

public class BankAccount extends Account {
    private String holder;
    private String bank;
    private String accountNumber;

    public BankAccount(int id, String type, BigDecimal balance, Integer collectivityId, Integer federationId, String holder, String bank, String accountNumber) {
        super(id, type, balance, collectivityId, federationId);
        this.holder = holder;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}