/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.UUID;
import org.codehaus.jackson.annotate.JsonIgnore;
import util.Constants;

/**
 *
 * @author hanfengwei
 */
public class BankStatement {
    private String $class;
    private String bankStatementId;
    private String bankName;
    private String accountNumber;
    private String bankStatementImageURL;
    private String endorsementState;
    private String owner;
    private String endorseBy;
    private String visaApplication;
    
    public BankStatement() {
        this.$class = "org.acme.easypass.BankStatement";
        this.bankStatementId = UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
    }

    public BankStatement(String bankName, String accountNumber, String bankStatementImageURL, String endorseBy, String visaApplication) {
        this();
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.bankStatementImageURL = bankStatementImageURL;
        this.endorseBy = endorseBy;
        this.visaApplication = visaApplication;
    }
        
    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getBankStatementId() {
        return bankStatementId;
    }

    public void setBankStatementId(String bankStatementId) {
        this.bankStatementId = bankStatementId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankStatementImageURL() {
        return bankStatementImageURL;
    }

    public void setBankStatementImageURL(String bankStatementImageURL) {
        this.bankStatementImageURL = bankStatementImageURL;
    }

    public String getEndorsementState() {
        return endorsementState;
    }

    public void setEndorsementState(String endorsementState) {
        this.endorsementState = endorsementState;
    }

    public String getOwner() {
        return owner.substring(owner.indexOf("#")+1);
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
