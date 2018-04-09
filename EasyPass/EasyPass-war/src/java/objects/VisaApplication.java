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
 * @author hanfengwei
 */
public class VisaApplication {
    private String $class;
    private String visaApplicationId;
    private String startDate;
    private String endDate;
    private String purposeOfVisit;
    private String state;
    private String visaStatus;
    private String passport;
    private String basicInfo;
    private String bankStatement;
    private String transportationReference;
    private String accomodation;
    private String insurance;
    private String localContact;
    private String criminalRecord;
    private String owner;
    
    public VisaApplication() {
        this.$class = "org.acme.easypass.VisaApplication";
        this.visaApplicationId = UUID.randomUUID().toString();
        this.state = Constants.STATUS_PENDING;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(String visaStatus) {
        this.visaStatus = visaStatus;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(String bankStatement) {
        this.bankStatement = bankStatement;
    }

    public String getTransportationReference() {
        return transportationReference;
    }

    public void setTransportationReference(String transportationReference) {
        this.transportationReference = transportationReference;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getLocalContact() {
        return localContact;
    }

    public void setLocalContact(String localContact) {
        this.localContact = localContact;
    }

    public String getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(String criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
