/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Jingyuan
 */
public class LocalContact {
    @JsonIgnore
    private String $class;
    
    private String localContactId;
    private String contactName;
    private String identityNumber;
    private String endorsementState;
    private String owner;
    @JsonIgnore
    private String endorseBy;
    @JsonIgnore
    private String visaApplication;

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
