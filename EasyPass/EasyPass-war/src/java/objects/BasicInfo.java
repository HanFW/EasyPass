/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.UUID;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
public class BasicInfo {

    private String $class;
    private String basicInfoId;
    private String firstName;
    private String lastName;
    private String birthday;
    private String countryOfResidence;
    private String identityNumber;
    private String residentialAddress;
    private String maritalStatus;
    private String passportNumber;
    private String sex;
    private String nationality;
    private String endorsementState;
    private String owner; //citizenId
    private String endorseBy; //icaId
    private String visaApplication; //visaApplicationId
    
    public BasicInfo() {
        this.$class = "org.acme.easypass.BasicInfo";
        this.basicInfoId = "org.acme.easypass.BasicInfo#" + UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
        this.endorseBy = "ICAId(TBC)";
    }

    public BasicInfo(String firstName, String lastName, String birthday, String countryOfResidence, String identityNumber, String residentialAddress, String maritalStatus, String passportNumber, String sex, String nationality, String visaApplication) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.countryOfResidence = countryOfResidence;
        this.identityNumber = identityNumber;
        this.residentialAddress = residentialAddress;
        this.maritalStatus = maritalStatus;
        this.passportNumber = passportNumber;
        this.sex = sex;
        this.nationality = nationality;
        this.visaApplication = visaApplication;
    }
    
    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getBasicInfoId() {
        return basicInfoId;
    }

    public void setBasicInfoId(String basicInfoId) {
        this.basicInfoId = basicInfoId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEndorsementState() {
        return endorsementState;
    }

    public void setEndorsementState(String endorsementState) {
        this.endorsementState = endorsementState;
    }

    public String getOwner() {
        return owner.substring(owner.indexOf("#")+1);
    }

    public void setOwner(String owner) {
        this.owner = "org.acme.easypass.Citizen#" + owner;
    }

    public String getEndorseBy() {
        return endorseBy;
    }

    public void setEndorseBy(String endorseBy) {
        this.endorseBy = "org.acme.easypass.ICA#" + endorseBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = "org.acme.easypass.VisaApplication#" + visaApplication;
    }
    
    

}
