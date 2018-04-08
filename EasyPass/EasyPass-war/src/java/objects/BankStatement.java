/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author hanfengwei
 */
public class BankStatement {
    @JsonIgnore
    private String $class;
    
    private String bankStatementId;
    private String bankName;
    private String accountNumber;
    private String bankStatementImageURL;
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
