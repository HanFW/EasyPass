/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import entity.EndorserEntity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import objects.BankStatement;
import util.Constants;

/**
 *
 * @author hanfengwei
 */
@Named(value = "bankDoEndorsementManagedBean")
@ViewScoped
public class BankDoEndorsementManagedBean implements Serializable {

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
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        if (Constants.localTesting) {

            //$$$ValidateBankStatement transaction
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            bankStatement.setEndorseBy(id);

            if (decision.equals("Validated")) {
                bankStatement.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                bankStatement.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/post_request" + bankStatement.getOwner() + ".json"), bankStatement);

        } else {

            //$$$ValidateBankStatement transaction
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            bankStatement.setEndorseBy(id);

            if (decision.equals("Validated")) {
                bankStatement.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                bankStatement.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }

            ObjectMapper mapper = new ObjectMapper();

            try {
                HttpResponse<JsonNode> bankStatementResponse = Unirest.put("http://localhost:3000/api/org.acme.easypass.BankStatement/" + bankStatement.getBankStatementId())
                        .field("$class", Constants.ASSET_BANKSTATEMENT)
                        .field("bankStatementId", bankStatement.getBankStatementId())
                        .field("bankName", bankStatement.getBankName())
                        .field("accountNumber", bankStatement.getAccountNumber())
                        .field("bankStatementImageURL", bankStatement.getBankStatementImageURL())
                        .field("endorseStatus", bankStatement.getEndorseStatus())
                        .field("owner", bankStatement.getOwner())
                        .field("visaApplication", bankStatement.getVisaApplication())
                        .asJson();
                System.out.println(bankStatementResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
