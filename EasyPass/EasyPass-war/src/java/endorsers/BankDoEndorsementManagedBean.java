/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import objects.BankStatement;

/**
 *
 * @author hanfengwei
 */
@Named(value = "bankDoEndorsementManagedBean")
@ViewScoped
public class BankDoEndorsementManagedBean implements Serializable{
    
    private BankStatement bankStatement;
    private String decision; 

    /**
     * Creates a new instance of bankDoEndorsementManagedBean
     */
    public BankDoEndorsementManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        bankStatement = (BankStatement) ec.getFlash().get("bankStatement");
        System.out.println("View bank statement: " + bankStatement.getBankStatementId());
    }
    
    //validate or reject document
    public void validateDocument () throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        //$$$ValidateBankStatement transaction
        System.out.println("Bank Statement " + bankStatement.getBankStatementId() + ": " + decision);
        
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/bankViewList.xhtml?faces-redirect=true");
    }

    public BankStatement getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
