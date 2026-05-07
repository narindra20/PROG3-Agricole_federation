package hei.school.agricole.entity;

import hei.school.agricole.enums.MobileBankingService;

public class MobileBankingAccount extends FinancialAccount {
    private MobileBankingService mobileBankingService;
    private String mobileNumber;

    public MobileBankingService getMobileBankingService() {
        return mobileBankingService;
    }
    public void setMobileBankingService(MobileBankingService mobileBankingService) {
        this.mobileBankingService = mobileBankingService;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}