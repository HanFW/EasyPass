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
public class LocalContact {
    private String $class;
    
    private String localContactId;
    private String contactName;
    private String identityNumber;
    private String endorsementState;
    private String owner;
    private String endorseBy;
    private String visaApplication;
    
    public LocalContact() {
        this.$class = "org.acme.easypass.LocalContact";
        this.localContactId = UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
    }

    public LocalContact(String contactName, String identityNumber, String endorseBy, String visaApplication) {
        this();
        this.contactName = contactName;
        this.identityNumber = identityNumber;
        this.endorseBy = endorseBy;
        this.visaApplication = visaApplication;
    }    

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getLocalContactId() {
        return localContactId;
    }

    public void setLocalContactId(String localContactId) {
        this.localContactId = localContactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
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
