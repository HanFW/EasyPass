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
public class Accomodation {
    private String $class; 
    private String accomodationId;
    private String carrierName;
    private String referenceNumber;
    private String transportationReferenceImageURL;
    private String endorsementState;
    private String owner; //citizenId
    private String endorseBy; //hotelId
    private String visaApplication; //visaApplicationId
    
    public Accomodation() {
        this.$class = "org.acme.easypass.Accomodation";
        this.accomodationId = "org.acme.easypass.Accomodation#" + UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
    }

    public Accomodation(String carrierName, String referenceNumber, String transportationReferenceImageURL, String endorseBy, String visaApplication) {
        this();
        this.carrierName = carrierName;
        this.referenceNumber = referenceNumber;
        this.transportationReferenceImageURL = transportationReferenceImageURL;
        this.endorseBy = endorseBy;
        this.visaApplication = visaApplication;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getOwner() {
        return owner.substring(owner.indexOf("#")+1);
    }

    public void setOwner(String owner) {
        this.owner = "org.acme.easypass.Citizen#" + owner;
    }

    public String getAccomodationId() {
        return accomodationId;
    }

    public void setAccomodationId(String accomodationId) {
        this.accomodationId = accomodationId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTransportationReferenceImageURL() {
        return transportationReferenceImageURL;
    }

    public void setTransportationReferenceImageURL(String transportationReferenceImageURL) {
        this.transportationReferenceImageURL = transportationReferenceImageURL;
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
        this.endorseBy = "org.acme.easypass.Hotel#" + endorseBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = "org.acme.easypass.VisaApplication#" + visaApplication;
    }
    
    

       
}
