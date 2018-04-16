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
import objects.BasicInfo;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "iCADoEndorsementManagedBean")
@ViewScoped
public class ICADoEndorsementManagedBean implements Serializable {

    private BasicInfo basicInfo;
    private String decision;

    /**
     * Creates a new instance of ICADoEndorsementManagedBean
     */
    public ICADoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        basicInfo = (BasicInfo) ec.getFlash().get("basicInfo");
        System.out.println("View basic Info: " + basicInfo.getBasicInfoId());
    }

    //validate or reject document
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateBasicInfo transaction
        System.out.println("BasicInfo " + basicInfo.getBasicInfoId() + ": " + decision);

        if (Constants.localTesting) {
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            basicInfo.setEndorseBy(id);

            if (decision.equals("Validated")) {
                basicInfo.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                basicInfo.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/post_request" + basicInfo.getOwner() + ".json"), basicInfo);
        } else {
            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            basicInfo.setEndorseBy(id);

            if (decision.equals("Validated")) {
                basicInfo.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                basicInfo.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }

            ObjectMapper mapper = new ObjectMapper();

            try {
                HttpResponse<JsonNode> basicInfoResponse = Unirest.put("http://localhost:3000/api/org.acme.easypass.BasicInfo/" + basicInfo.getBasicInfoId())
                        .header("accept", "application/json")
                        .field("$class", Constants.ASSET_BASICINFO)
                        .field("basicInfoId", basicInfo.getBasicInfoId())
                        .field("firstName", basicInfo.getFirstName())
                        .field("lastName", basicInfo.getLastName())
                        .field("birthday", basicInfo.getBirthday())
                        .field("identityNumber", basicInfo.getIdentityNumber())
                        .field("residentialAddress", basicInfo.getResidentialAddress())
                        .field("countryOfResidence", basicInfo.getCountryOfResidence())
                        .field("maritalStatus", basicInfo.getMaritalStatus())
                        .field("passportNumber", basicInfo.getPassportNumber())
                        .field("sex", basicInfo.getSex())
                        .field("nationality", basicInfo.getNationality())
                        .field("endorseStatus", basicInfo.getEndorseStatus())
                        .field("owner", basicInfo.getOwner())
                        .field("visaApplication", basicInfo.getVisaApplication())
                        .asJson();
                System.out.println(basicInfoResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ec.redirect(ec.getRequestContextPath() + "/web/endorser/ICAViewList.xhtml?faces-redirect=true");
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
