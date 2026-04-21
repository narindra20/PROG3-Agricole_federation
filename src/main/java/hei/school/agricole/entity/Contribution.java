package hei.school.agricole.entity;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Contribution {
    private int id;
    private int memberId;
    private int collectivityId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String type;

    public Contribution(int id, int memberId, int collectivityId, BigDecimal amount, LocalDate paymentDate, String paymentMethod, String type) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(int collectivityId) {
        this.collectivityId = collectivityId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
