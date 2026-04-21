package hei.school.agricole.entity;

public class MandatePosition {
    private int id;
    private int mandateId;
    private int memberId;
    private int collectivityId;
    private String position;

    public MandatePosition(int id, int mandateId, int memberId, String position, int collectivityId) {
        this.id = id;
        this.mandateId = mandateId;
        this.memberId = memberId;
        this.position = position;
        this.collectivityId = collectivityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMandateId() {
        return mandateId;
    }

    public void setMandateId(int mandateId) {
        this.mandateId = mandateId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
