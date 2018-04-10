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
    private String state;
    private String message;
    private String owner; //citizenId
    private String updatedBy; //embassyId
    private String visaApplication; //visaApplicationId
    
    public VisaStatus() {
        this.$class = "org.acme.easypass.VisaStatus";
        this.visaStatusId = UUID.randomUUID().toString();
        this.state = Constants.STATUS_PENDING;
        this.message = "";
        this.owner = "CitizenId(TBC)";
    }
    
    public VisaStatus(String updatedBy, String visaApplication) {
        this();
        this.updatedBy = updatedBy;
        this.visaApplication = visaApplication;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        this.owner = "org.acme.easypass.Citizen#" + owner;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = "org.acme.easypass.Embassy#" + updatedBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = "org.acme.easypass.VisaApplication#" + visaApplication;
    }
    
    
}
