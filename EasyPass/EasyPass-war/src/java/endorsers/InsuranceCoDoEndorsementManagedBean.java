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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Insurance;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "insuranceCoDoEndorsementManagedBean")
@ViewScoped
public class InsuranceCoDoEndorsementManagedBean implements Serializable {

    private Insurance insurance;
    private String decision;

    /**
     * Creates a new instance of InsuranceCoDoEndorsementManagedBean
     */
    public InsuranceCoDoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        insurance = (Insurance) ec.getFlash().get("insurance");
        System.out.println("View insurance: " + insurance.getInsuranceId());
    }

    //validate or reject document
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateInsurance transaction
        System.out.println("Insurance " + insurance.getInsuranceId() + ": " + decision);

        if (Constants.localTesting) {
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            insurance.setEndorseBy(id);

            if (decision.equals("Validated")) {
                insurance.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                insurance.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/post_request" + insurance.getOwner() + ".json"), insurance);
        } else {
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            insurance.setEndorseBy(id);

            if (decision.equals("Validated")) {
                insurance.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                insurance.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }

            ObjectMapper mapper = new ObjectMapper();

            try {
                HttpResponse<JsonNode> insuranceResponse = Unirest.put("http://localhost:3000/api/org.acme.easypass.Insurance/" + insurance.getInsuranceId())
                        .header("accept", "application/json")
                        .field("$class", Constants.ASSET_INSURANCE)
                        .field("insuranceId", insurance.getInsuranceId())
                        .field("companyName", insurance.getCompanyName())
                        .field("referenceNumber", insurance.getReferenceNumber())
                        .field("insuranceContractImageURL", insurance.getInsuranceContractImageURL())
                        .field("endorseStatus", insurance.getEndorseStatus())
                        .field("owner", insurance.getOwner())
                        .field("visaApplication", insurance.getVisaApplication())
                        .asJson();
                System.out.println(insuranceResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/insuranceCompanyViewList.xhtml?faces-redirect=true");
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
