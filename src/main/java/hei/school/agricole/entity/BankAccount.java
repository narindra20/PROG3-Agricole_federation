package hei.school.agricole.entity;

import hei.school.agricole.enums.Bank;

public class BankAccount extends FinancialAccount {
    private Bank bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private String bankAccountNumber;

    private Integer bankAccountKey;

    public Bank getBankName() {
        return bankName;
    }
    public void setBankName(Bank bankName) {
        this.bankName = bankName;
    }
    public Integer getBankCode() {
        return bankCode;
    }
    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }
    public Integer getBankBranchCode() {
        return bankBranchCode;
    }
    public void setBankBranchCode(Integer bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    public Integer getBankAccountKey() {
        return bankAccountKey;
    }
    public void setBankAccountKey(Integer bankAccountKey) {
        this.bankAccountKey = bankAccountKey;
    }
}