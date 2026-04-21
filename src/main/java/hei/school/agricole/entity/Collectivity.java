package hei.school.agricole.entity;

import java.time.LocalDate;

public class Collectivity {
    private int id;
    private int number;
    private String name;
    private LocalDate creationDate;
    private int cityId;
    private int domainId;
    private Integer federationId;
    private Integer sectorId;
    private boolean isAuthorized;

    public Collectivity(int id, int number, String name, LocalDate creationDate, int domainId, int cityId, Integer federationId, Integer sectorId, boolean isAuthorized) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.creationDate = creationDate;
        this.domainId = domainId;
        this.cityId = cityId;
        this.federationId = federationId;
        this.sectorId = sectorId;
        this.isAuthorized = isAuthorized;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public Integer getFederationId() {
        return federationId;
    }

    public void setFederationId(Integer federationId) {
        this.federationId = federationId;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }
}
