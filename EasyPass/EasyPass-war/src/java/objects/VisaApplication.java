/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.UUID;

/**
 *
 * @author hanfengwei
 */
public class VisaApplication {
    private String $class;
    private String visaApplicationId;
    private String startDate;
    private String endDate;
    private String purposeOfVisit;
    private String visaStatus; //visaStatusId
    private String passport; //passportId
    private String basicInfo; //basicInfoId
    private String bankStatement; //bankStatementId
    private String transportationReference; //transportationReferenceId
    private String accomodation; //accommodationId
    private String insurance; //insuranceCompanyId
    private String localContact; //localcontactId
    private String criminalRecord; //criminalRecordId
    private String owner; //citizenId
    
    public VisaApplication() {
        this.$class = "org.acme.easypass.VisaApplication";
        this.visaApplicationId = "org.acme.easypass.VisaApplication#" + UUID.randomUUID().toString();
        this.owner = "CitizenId(TBC)";
    }
    
    public void updateVisaApplicationInfo(String startDate, String endDate, String purposeOfVisit){
        this.startDate = startDate;
        this.endDate = endDate;
        this.purposeOfVisit = purposeOfVisit;
    }
    
    public void updateVisaApplicationReferences(String visaStatus, String passport, String owner, 
            String basicInfo, String bankStatement, String transportationReference, String accomodation, String insurance, String localContact){
        this.visaStatus = visaStatus;
        this.passport = passport;
        this.owner = owner;
        this.basicInfo = basicInfo;
        this.bankStatement = bankStatement;
        this.transportationReference = transportationReference;
        this.accomodation = accomodation;
        this.insurance = insurance;
        this.localContact = localContact;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getVisaApplicationId() {
        return visaApplicationId;
    }

    public void setVisaApplicationId(String visaApplicationId) {
        this.visaApplicationId = visaApplicationId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(String visaStatus) {
        this.visaStatus = "org.acme.easypass.VisaStatus#" + visaStatus;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = "org.acme.easypass.Passport#" + passport;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = "org.acme.easypass.BasicInfo#" + basicInfo;
    }

    public String getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(String bankStatement) {
        this.bankStatement = "org.acme.easypass.BankStatement#" + bankStatement;
    }

    public String getTransportationReference() {
        return transportationReference;
    }

    public void setTransportationReference(String transportationReference) {
        this.transportationReference = "org.acme.easypass.TransportationReference#" + transportationReference;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = "org.acme.easypass.Accomodation#" + accomodation;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = "org.acme.easypass.Insurance#" + insurance;
    }

    public String getLocalContact() {
        return localContact;
    }

    public void setLocalContact(String localContact) {
        this.localContact = "org.acme.easypass.LocalContact#" + localContact;
    }

    public String getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(String criminalRecord) {
        this.criminalRecord = "org.acme.easypass.CriminalRecord#" + criminalRecord;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = "org.acme.easypass.Citizen#" + owner;
    }
}
