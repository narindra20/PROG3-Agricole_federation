package hei.school.agricole.entity;

import java.time.LocalDate;
import java.util.List;

public class Collectivity {

    private int id;
    private int number;
    private String name;
    private LocalDate creationDate;
    private Integer cityId;
    private Integer domainId;
    private Integer federationId;
    private Integer sectorId;

    private boolean authorized;

    private String location;

    private List<Integer> memberIds;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }

    public Integer getDomainId() { return domainId; }
    public void setDomainId(Integer domainId) { this.domainId = domainId; }

    public Integer getFederationId() { return federationId; }
    public void setFederationId(Integer federationId) { this.federationId = federationId; }

    public Integer getSectorId() { return sectorId; }
    public void setSectorId(Integer sectorId) { this.sectorId = sectorId; }

    public boolean isAuthorized() { return authorized; }
    public void setAuthorized(boolean authorized) { this.authorized = authorized; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Integer> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds; }
}