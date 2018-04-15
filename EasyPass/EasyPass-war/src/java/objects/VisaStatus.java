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
public class VisaStatus {
    private String $class;
    private String visaStatusId;
    private String statusState;
    private String message;
    private String owner; //citizenId
    private String updatedBy; //embassyId
    private String visaApplication; //visaApplicationId
    
    public VisaStatus() {
        this.$class = Constants.ASSET_VISASTATUS;
        this.visaStatusId = UUID.randomUUID().toString();
        this.statusState = Constants.STATUS_PENDING;
        this.message = " ";
    }
    
    public VisaStatus(String visaApplication, String owner) {
        this();
        this.visaApplication = Constants.ASSET_VISAAPPLICATION + "#" + visaApplication;
        this.owner = Constants.PARTICIPANT_CITIZEN + "#" + owner;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getVisaStatusId() {
        return visaStatusId;
    }

    public void setVisaStatusId(String visaStatusId) {
        this.visaStatusId = visaStatusId;
    }

    public String getStatusState() {
        return statusState;
    }

    public void setStatusState(String statusState) {
        this.statusState = statusState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = visaApplication;
    }
}
