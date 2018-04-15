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
public class Accommodation {
    private String $class; 
    private String accommodationId;
    private String carrierName;
    private String referenceNumber;
    private String accommodationReferenceImageURL;
    private String endorseStatus;
    private String owner; //citizenId
    private String endorseBy; //hotelId
    private String visaApplication; //visaApplicationId
    
    public Accommodation() {
        this.$class = Constants.ASSET_ACCOMMODATION;
        this.accommodationId = UUID.randomUUID().toString();
        this.endorseStatus = Constants.STATUS_PENDING;
    }

    public Accommodation(String carrierName, String referenceNumber, String accommodationImageURL, String visaApplication, String owner) {
        this();
        this.carrierName = carrierName;
        this.referenceNumber = referenceNumber;
        this.accommodationReferenceImageURL = accommodationImageURL;
        this.visaApplication = Constants.ASSET_VISAAPPLICATION + "#" + visaApplication;
        this.owner = Constants.PARTICIPANT_CITIZEN + "#" + owner;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
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

    public String getAccommodationReferenceImageURL() {
        return accommodationReferenceImageURL;
    }

    public void setAccommodationReferenceImageURL(String accommodationReferenceImageURL) {
        this.accommodationReferenceImageURL = accommodationReferenceImageURL;
    }

    public String getEndorseStatus() {
        return endorseStatus;
    }

    public void setEndorseStatus(String endorseStatus) {
        this.endorseStatus = endorseStatus;
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
