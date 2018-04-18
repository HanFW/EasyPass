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
import objects.TransportationReference;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "transportationProviderDoEndorsementManagedBean")
@ViewScoped
public class TransportationProviderDoEndorsementManagedBean implements Serializable {

    private TransportationReference transportationReference;
    private String decision;

    /**
     * Creates a new instance of TransportationProviderDoEndorsementManagedBean
     */
    public TransportationProviderDoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        transportationReference = (TransportationReference) ec.getFlash().get("transportationReference");
        System.out.println("View transportation Reference: " + transportationReference.getTransportationReferenceId());
    }

    //validate or reject document
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateTransportationReference transaction
        System.out.println("TransportationReference " + transportationReference.getTransportationReferenceId() + ": " + decision);

        if (Constants.localTesting) {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            transportationReference.setEndorseBy(id);

            if (decision.equals("Validated")) {
                transportationReference.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                transportationReference.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/post_request" + transportationReference.getOwner() + ".json"), transportationReference);

        } else {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            transportationReference.setEndorseBy(id);

            if (decision.equals("Validated")) {
                transportationReference.setEndorseStatus(Constants.STATUS_VERIFIED);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            } else {
                transportationReference.setEndorseStatus(Constants.STATUS_INVALIDATE);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
                ec.getFlash().setKeepMessages(true);
            }

            try {
                HttpResponse<JsonNode> transportResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.ValidateTransportationBookingReference ")
                        .field("$class", Constants.TRANSACTION_VALIDATETRANSPORTATION)
                        .field("endorseStatus", transportationReference.getEndorseStatus())
                        .field("transportationReference", Constants.ASSET_TRANSPORTATION + "#" + transportationReference.getTransportationReferenceId())
                        .field("transportationProvider", Constants.ASSET_TRANSPORTATIONPROVIDER + "#" + endorser.getEndorserId())
                        .asJson();
                System.out.println(transportResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ec.redirect(ec.getRequestContextPath() + "/web/endorser/TransportationProviderViewList.xhtml?faces-redirect=true");
    }

    public TransportationReference getTransportationReference() {
        return transportationReference;
    }

    public void setTransportationReference(TransportationReference transportationReference) {
        this.transportationReference = transportationReference;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
