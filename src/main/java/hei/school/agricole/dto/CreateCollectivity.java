package hei.school.agricole.dto;

import hei.school.agricole.entity.CollectivityStructure;

import java.util.List;

public class CreateCollectivity {

    private String location;
    private List<String> members;
    private boolean federationApproval;
    private CollectivityStructure structure;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }

    public boolean isFederationApproval() { return federationApproval; }
    public void setFederationApproval(boolean federationApproval) { this.federationApproval = federationApproval; }

    public CollectivityStructure getStructure() { return structure; }
    public void setStructure(CollectivityStructure structure) { this.structure = structure; }
}