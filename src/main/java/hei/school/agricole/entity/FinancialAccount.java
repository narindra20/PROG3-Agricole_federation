package hei.school.agricole.entity;

public abstract class FinancialAccount {
    protected String id;
    protected String collectivityId;
    protected Double amount;

    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }
    public String getCollectivityId() {

        return collectivityId;
    }
    public void setCollectivityId(String collectivityId) {

        this.collectivityId = collectivityId;
    }
    public Double getAmount() {

        return amount;
    }
    public void setAmount(Double amount) {

        this.amount = amount;
    }
}

