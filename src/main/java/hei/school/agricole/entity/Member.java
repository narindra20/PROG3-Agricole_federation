package hei.school.agricole.entity;

import hei.school.agricole.enums.Gender;
import hei.school.agricole.enums.MemberOccupation;
import java.time.LocalDate;

public class Member {
    private String id;
    private String collectivityId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private MemberOccupation occupation;
    private LocalDate membershipDate;



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
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {

        this.birthDate = birthDate;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public MemberOccupation getOccupation() {
        return occupation;
    }
    public void setOccupation(MemberOccupation occupation) {
        this.occupation = occupation;
    }
    public LocalDate getMembershipDate() {
        return membershipDate;
    }
    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }
}