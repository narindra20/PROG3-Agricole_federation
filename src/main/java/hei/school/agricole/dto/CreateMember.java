package hei.school.agricole.dto;

import hei.school.agricole.enums.Gender;
import hei.school.agricole.enums.MemberOccupation;
import java.time.LocalDate;
import java.util.List;

public class CreateMember {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private String phone;
    private String email;
    private MemberOccupation occupation;
    private LocalDate membershipDate;
    private List<String> referees;
    private String collectivityIdentifier;
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public MemberOccupation getOccupation() { return occupation; }
    public void setOccupation(MemberOccupation occupation) { this.occupation = occupation; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
    public List<String> getReferees() { return referees; }
    public void setReferees(List<String> referees) { this.referees = referees; }
    public String getCollectivityIdentifier() { return collectivityIdentifier; }
    public void setCollectivityIdentifier(String collectivityIdentifier) { this.collectivityIdentifier = collectivityIdentifier; }
    public boolean isRegistrationFeePaid() { return registrationFeePaid; }
    public void setRegistrationFeePaid(boolean registrationFeePaid) { this.registrationFeePaid = registrationFeePaid; }
    public boolean isMembershipDuesPaid() { return membershipDuesPaid; }
    public void setMembershipDuesPaid(boolean membershipDuesPaid) { this.membershipDuesPaid = membershipDuesPaid; }
}