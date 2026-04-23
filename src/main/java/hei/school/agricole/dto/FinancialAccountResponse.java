package hei.school.agricole.dto;

public class FinancialAccountResponse {
    private String id;
    private String type;
    private Double balance;
    private String holderName;
    private String bankName;
    private String mobileService;
    private String accountNumber;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public String getHolderName() {
        return holderName;
    }
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getMobileService() {
        return mobileService;
    }
    public void setMobileService(String mobileService) {
        this.mobileService = mobileService;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
