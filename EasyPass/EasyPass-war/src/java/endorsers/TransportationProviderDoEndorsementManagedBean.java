/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.TransportationReference;

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

        ec.redirect(ec.getRequestContextPath() + "/web/TransportationProviderViewList.xhtml?faces-redirect=true");
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
