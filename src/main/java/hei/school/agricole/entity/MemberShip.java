package hei.school.agricole.entity;

import java.time.LocalDate;

public class MemberShip {
    private int id;
    private int memberId;
    private int collectivityId;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private String position;

    public MemberShip(int id, int memberId, int collectivityId, LocalDate entryDate, LocalDate exitDate, String position) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.position = position;
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

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
