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
    private String state;//visaApplication status state
    private String visaStatus; //visaStatusId
    private String passport; //passportId
    private String basicInfo; //basicInfoId
    private String bankStatement; //bankStatementId
    private String transportationReference; //transportationReferenceId
    private String accommodation; //accommodationId
    private String insurance; //insuranceCompanyId
    private String localContact; //localcontactId
    private String criminalRecord; //criminalRecordId
    private String owner; //citizenId
    
    public VisaApplication() {
        this.$class = Constants.ASSET_VISAAPPLICATION;
        this.visaApplicationId = Constants.ASSET_VISAAPPLICATION + "#" + UUID.randomUUID().toString();
    }
    
    public void updateVisaApplicationInfo(String startDate, String endDate, String purposeOfVisit){
        this.startDate = startDate;
        this.endDate = endDate;
        this.purposeOfVisit = purposeOfVisit;
    }
    
    public void updateVisaApplicationReferences(String visaStatus, String passport, String owner, 
            String basicInfo, String bankStatement, String transportationReference, String accommodation, String insurance, String localContact, String criminalRecord){
        this.visaStatus = visaStatus;
        this.passport = passport;
        this.basicInfo = basicInfo;
        this.bankStatement = bankStatement;
        this.transportationReference = transportationReference;
        this.accommodation = accommodation;
        this.insurance = insurance;
        this.localContact = localContact;
        this.owner = Constants.PARTICIPANT_CITIZEN + "#" + owner;
        this.criminalRecord = criminalRecord;
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

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }  
    
}
