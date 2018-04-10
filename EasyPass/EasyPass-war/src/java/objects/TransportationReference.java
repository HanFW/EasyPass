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
public class TransportationReference {
    private String $class;
    private String transportationReferenceId;
    private String carrierName;
    private String reference;
    private String transportationReferenceImageURL;
    private String endorsementState;
    private String owner; //citizenId
    private String endorseBy; //tranportationproviderId
    private String visaApplication; //visaApplicationId
    
    public TransportationReference() {
        this.$class = Constants.ASSET_TRANSPORTATION;
        this.transportationReferenceId = Constants.ASSET_TRANSPORTATION + "#" + UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
    }

    public TransportationReference(String carrierName, String reference, String transportationReferenceImageURL, String visaApplication, String owner) {
        this();
        this.carrierName = carrierName;
        this.reference = reference;
        this.transportationReferenceImageURL = transportationReferenceImageURL;
        this.visaApplication = visaApplication;
        this.owner = Constants.PARTICIPANT_CITIZEN + "#" + owner;
    }
    
    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getTransportationReferenceId() {
        return transportationReferenceId;
    }

    public void setTransportationReferenceId(String transportationReferenceId) {
        this.transportationReferenceId = transportationReferenceId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getOwner() {
       return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
    
    
    
}
