/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
public class Visa {

    private String $class;
    private String visaId;
    private String visaIssueDate;
    private String visaValidTill;
    private String visaType;
    private String passport;
    private String issuedBy;
    private String visaApplication;
    
    public Visa() {
        this.$class = Constants.ASSET_VISA;
        this.visaId = UUID.randomUUID().toString();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        this.visaIssueDate = df.format(new Date());
    }

    public Visa(String visaValidTill, String visaType, String passport, String issuedBy, String visaApplication) {
        this();
        this.visaValidTill = visaValidTill;
        this.visaType = visaType;
        this.passport = Constants.ASSET_PASSPORT + "#" + passport;
        this.issuedBy = Constants.PARTICIPANT_EMBASSY + "#" + issuedBy;
        this.visaApplication = Constants.ASSET_VISAAPPLICATION + "#" + visaApplication;
    }
    
    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public String getVisaIssueDate() {
        return visaIssueDate;
    }

    public void setVisaIssueDate(String visaIssueDate) {
        this.visaIssueDate = visaIssueDate;
    }

    public String getVisaValidTill() {
        return visaValidTill;
    }

    public void setVisaValidTill(String visaValidTill) {
        this.visaValidTill = visaValidTill;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(String visaApplication) {
        this.visaApplication = visaApplication;
    }
    
    

}
