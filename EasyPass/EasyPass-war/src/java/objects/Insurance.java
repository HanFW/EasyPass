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
public class Insurance {

    private String $class;
    private String insuranceId;
    private String companyName;
    private String referenceNumber;
    private String insuranceContractImageURL;
    private String endorsementState;
    private String owner;
    private String endorseBy;
    private String visaApplication;
    
    public Insurance() {
        this.$class = "org.acme.easypass.Insurance";
        this.insuranceId = UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
    }

    public Insurance(String companyName, String referenceNumber, String insuranceContractImageURL, String endorseBy, String visaApplication) {
        this();
        this.companyName = companyName;
        this.referenceNumber = referenceNumber;
        this.insuranceContractImageURL = insuranceContractImageURL;
        this.endorseBy = endorseBy;
        this.visaApplication = visaApplication;
    }
    
    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getInsuranceContractImageURL() {
        return insuranceContractImageURL;
    }

    public void setInsuranceContractImageURL(String insuranceContractImageURL) {
        this.insuranceContractImageURL = insuranceContractImageURL;
    }

    public String getEndorsementState() {
        return endorsementState;
    }

    public void setEndorsementState(String endorsementState) {
        this.endorsementState = endorsementState;
    }

    public String getEndorseBy() {
        return endorseBy;
    }

    public void setEndorseBy(String endorseBy) {
        this.endorseBy = endorseBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = visaApplication;
    }

    public String getOwner() {
        return owner.substring(owner.indexOf("#") + 1);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
